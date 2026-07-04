package com.example.itp4229m.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.itp4229m.MainActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.manager.LoadingManager;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Cart;
import com.example.itp4229m.retrofit.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    CardView cvAccountRoleSuperuser, cvAccountRoleInstructor, cvAccountRoleUser;
    ImageView ivAccountAvatar;
    TextView tvAccountUsername, tvAccountID, tvAccountLastLogin, tvAccountDateJoined, tvAccountEmail, tvAccountBio;
    TextView tvAccountFacebook, tvAccountTwitter, tvAccountInstagram, tvAccountLinkedin;
    EditText etProfileUsername, etProfileEmail, etProfileBio, etProfileFacebook,
            etProfileTwitter, etProfileInstagram, etProfileLinkedin, etProfileAvatar;
    Button btnLogout, btnAccountBeInstructor, btnAccountEditProfile, btnProfileReset, btnProfileSubmit;
    String userid;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ConstraintLayout clAccountRoot;
    public LoadingManager loadingManager;
    BottomSheetDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        ((MainActivity) requireActivity()).changeNavbarBtnColor(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        ivAccountAvatar = view.findViewById(R.id.ivAccountAvatar);
        tvAccountUsername = view.findViewById(R.id.tvAccountUsername);
        tvAccountID = view.findViewById(R.id.tvAccountID);
        tvAccountLastLogin = view.findViewById(R.id.tvAccountLastLogin);
        tvAccountDateJoined = view.findViewById(R.id.tvAccountDateJoined);
        tvAccountEmail = view.findViewById(R.id.tvAccountEmail);
        tvAccountBio = view.findViewById(R.id.tvAccountBio);
        cvAccountRoleSuperuser = view.findViewById(R.id.cvAccountRoleSuperuser);
        cvAccountRoleInstructor = view.findViewById(R.id.cvAccountRoleInstructor);
        cvAccountRoleUser = view.findViewById(R.id.cvAccountRoleUser);
        tvAccountFacebook = view.findViewById(R.id.tvAccountFacebook);
        tvAccountTwitter = view.findViewById(R.id.tvAccountTwitter);
        tvAccountInstagram = view.findViewById(R.id.tvAccountInstagram);
        tvAccountLinkedin = view.findViewById(R.id.tvAccountLinkedin);

        tvAccountFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookUrl = tvAccountFacebook.getText().toString();
                if (!facebookUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                    startActivity(intent);
                }
            }
        });
        tvAccountTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String twitterUrl = tvAccountTwitter.getText().toString();
                if (!twitterUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
                    startActivity(intent);
                }
            }
        });
        tvAccountInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instagramUrl = tvAccountInstagram.getText().toString();
                if (!instagramUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
                    startActivity(intent);
                }
            }
        });
        tvAccountLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkedinUrl = tvAccountLinkedin.getText().toString();
                if (!linkedinUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl));
                    startActivity(intent);
                }
            }
        });

        clAccountRoot = view.findViewById(R.id.clAccountRoot);
        loadingManager = new LoadingManager(clAccountRoot , getContext());

        btnAccountBeInstructor = view.findViewById(R.id.btnAccountBeInstructor);
        btnAccountBeInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBeInstructor(apiService, userid);
                btnAccountBeInstructor.setVisibility(View.GONE);

                getUser(apiService, userid);
            }
        });

        btnAccountEditProfile = view.findViewById(R.id.btnAccountEditProfile);
        btnAccountEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new BottomSheetDialog(requireContext());
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.bsl_edit_profile, null);
                dialog.setContentView(dialogView);
                dialog.show();

                btnProfileReset = dialogView.findViewById(R.id.btnProfileReset);
                btnProfileSubmit = dialogView.findViewById(R.id.btnProfileSubmit);
                etProfileUsername = dialogView.findViewById(R.id.etProfileUsername);
                etProfileEmail = dialogView.findViewById(R.id.etProfileEmail);
                etProfileBio = dialogView.findViewById(R.id.etProfileBio);
                etProfileFacebook = dialogView.findViewById(R.id.etProfileFacebook);
                etProfileTwitter = dialogView.findViewById(R.id.etProfileTwitter);
                etProfileInstagram = dialogView.findViewById(R.id.etProfileInstagram);
                etProfileLinkedin = dialogView.findViewById(R.id.etProfileLinkedin);
                etProfileAvatar = dialogView.findViewById(R.id.etProfileAvatar);

                getUserInDialog(apiService, userid);

                btnProfileReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getUserInDialog(apiService, userid);
                    }
                });
                btnProfileSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postProfile(apiService, userid,
                                etProfileUsername.getText().toString(),
                                etProfileEmail.getText().toString(),
                                etProfileAvatar.getText().toString(),
                                etProfileBio.getText().toString(),
                                etProfileFacebook.getText().toString(),
                                etProfileInstagram.getText().toString(),
                                etProfileLinkedin.getText().toString(),
                                etProfileTwitter.getText().toString());
                    }
                });
            }
        });

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                editor = prefs.edit();
                editor.putBoolean("isLoggedIn", false).apply();
                editor.putString("userid", null).apply();
                Intent intent = requireActivity().getIntent();
                requireActivity().finish();
                startActivity(intent);
            }
        });


        getUser(apiService, userid);

        return view;
    }


    public void getUser(ApiService apiService, String userId){
        List<User> userList = null;
        loadingManager.show();
        apiService.getUser(userId).enqueue(new Callback<ApiResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<User>>> call, Response<ApiResponse<List<User>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<User>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<User> userList = apiResponse.getData();
                        Glide.with(getContext())
                                .load(userList.getFirst().getAvatar())
                                .placeholder(R.drawable.avatar0)
                                .into(ivAccountAvatar);
                        tvAccountUsername.setText(userList.getFirst().getUsername());
                        tvAccountID.setText("ID: "+userList.getFirst().getId());
                        tvAccountLastLogin.setText("Last Login: "+userList.getFirst().getLastLogin());
                        tvAccountDateJoined.setText("Date Joined: "+userList.getFirst().getDateJoined());
                        tvAccountEmail.setText("Email: "+userList.getFirst().getEmail());
                        tvAccountBio.setText(userList.getFirst().getBio());
                        tvAccountFacebook.setText(userList.getFirst().getFacebookUrl());
                        tvAccountTwitter.setText(userList.getFirst().getTwitterUrl());
                        tvAccountInstagram.setText(userList.getFirst().getInstagramUrl());
                        tvAccountLinkedin.setText(userList.getFirst().getLinkedinUrl());

                        if (userList.getFirst().getIsSuperuser() == 1) {
                            cvAccountRoleSuperuser.setVisibility(View.VISIBLE);
                        } else {
                            cvAccountRoleSuperuser.setVisibility(View.GONE);
                        }
                        if (userList.getFirst().getIsInstructor() == 1) {
                            cvAccountRoleInstructor.setVisibility(View.VISIBLE);
                            btnAccountBeInstructor.setVisibility(View.GONE);
                        } else {
                            cvAccountRoleInstructor.setVisibility(View.GONE);
                            btnAccountBeInstructor.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
                loadingManager.hide();
            }

            @Override
            public void onFailure(Call<ApiResponse<List<User>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
                loadingManager.hide();
            }
        });
    }

    public void getUserInDialog(ApiService apiService, String userId){
        List<User> userList = null;
        loadingManager.show();
        apiService.getUser(userId).enqueue(new Callback<ApiResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<User>>> call, Response<ApiResponse<List<User>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<User>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<User> userList = apiResponse.getData();

                        etProfileUsername.setText(userList.getFirst().getUsername());
                        etProfileEmail.setText(userList.getFirst().getEmail());
                        etProfileBio.setText(userList.getFirst().getBio());
                        etProfileFacebook.setText(userList.getFirst().getFacebookUrl());
                        etProfileTwitter.setText(userList.getFirst().getTwitterUrl());
                        etProfileInstagram.setText(userList.getFirst().getInstagramUrl());
                        etProfileLinkedin.setText(userList.getFirst().getLinkedinUrl());
                        etProfileAvatar.setText(userList.getFirst().getAvatar());
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
                loadingManager.hide();
            }

            @Override
            public void onFailure(Call<ApiResponse<List<User>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
                loadingManager.hide();
            }
        });
    }

    public void postBeInstructor(ApiService apiService, String userid) {
        apiService.postBeInstructor(userid).enqueue(new Callback<ApiResponse<Cart>>() {
            @Override
            public void onResponse(Call<ApiResponse<Cart>> call, Response<ApiResponse<Cart>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Cart> apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        Log.e("API_SUCCESS", "status: " + apiResponse.getStatus());
                        //Toast.makeText(CourseDetailActivity.this,"Add to cart"+userid+" "+courseId, Toast.LENGTH_SHORT).show();
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Cart>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }

    public void postProfile(ApiService apiService, String userid, String username, String email, String avatar, String bio,
                            String facebookUrl, String instagramUrl, String linkedinUrl, String twitterUrl) {
        apiService.postProfile(userid, username, email, avatar, bio, facebookUrl, instagramUrl, linkedinUrl, twitterUrl)
                .enqueue(new Callback<ApiResponse<User>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                        if (response.isSuccessful()) {
                            ApiResponse<User> apiResponse = response.body();
                            if (apiResponse.getStatus().equals("success")) {
                                Log.e("API_SUCCESS", "status: " + apiResponse.getStatus());
                                Toast.makeText(requireContext(),"Updated", Toast.LENGTH_SHORT).show();
                                getUser(apiService, userid);
                                dialog.dismiss();
                            }else if (apiResponse.getStatus().equals("error")) {
                                Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                            }
                        } else {
                            Log.e("API_ERROR", "error code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                        Log.e("API_ERROR", "requset failed: " + t.getMessage());
                    }
                });
    }
}
