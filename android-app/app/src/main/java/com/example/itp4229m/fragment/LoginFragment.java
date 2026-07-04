package com.example.itp4229m.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.itp4229m.LoginRegisterActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvHomeCategoriesAdapter;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Subject;
import com.example.itp4229m.retrofit.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    Button btnLogin;
    EditText etLoginUserID, etLoginPassword;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String userid, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        btnLogin = view.findViewById(R.id.btnLogin);
        etLoginUserID = view.findViewById(R.id.etLoginUserID);
        etLoginPassword = view.findViewById(R.id.etLoginPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                userid = etLoginUserID.getText().toString();
                password = etLoginPassword.getText().toString();
                Log.e("API_ERROR", "id: " + userid);
                Log.e("API_ERROR", "pwd: " + password);
                loginUser(apiService, userid, password);
                //Log.e("isLoggedIn", "LF click "+String.valueOf(prefs.getBoolean("isLoggedIn", false)));
            }
        });
        return view;
    }

    public void loginUser(ApiService apiService, String userid, String password){
        //User userList = null;
        Call<ApiResponse<User>> call = apiService.loginUser(userid, password);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<User> apiResponse = response.body();
                    //Log.e("API_ERROR", "id: " + userid);
                    //Log.e("API_ERROR", "pwd: " + password);
                    if (apiResponse.getStatus().equals("success")) {
                        //User userList = apiResponse.getData();

                        editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true).apply();
                        editor.putString("userid", userid).apply();
                        ((LoginRegisterActivity) requireActivity()).loadFragment(new LoginRegisterFragment());
                    }else {
                        Log.e("API_ERROR", "error Message: " + apiResponse.getMessage());
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
