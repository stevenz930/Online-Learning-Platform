package com.example.itp4229m.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.LoginRegisterActivity;
import com.example.itp4229m.MainActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvHomeCategoriesAdapter;
import com.example.itp4229m.adapter.RvHomePopularCoursesAdapter;
import com.example.itp4229m.manager.LoadingManager;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Course;
import com.example.itp4229m.retrofit.Subject;
import com.example.itp4229m.retrofit.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    ImageView topbarHeadBtn;
    Button btnHomeDS, btnHomeWD, btnHomeAI, btnHomeC, btnHomeMD, btnHomeCC;
    Button[] btns;
    TextView tvHomeUsername, tvPopularCourseCategory;
    RecyclerView rvHomeCategories, rvHomePopularCourses, rvHomeRecommend;
    RvHomeCategoriesAdapter rvHomeCategoriesAdapter;
    RvHomePopularCoursesAdapter rvHomePopularCoursesAdapter;
    LinearLayout svHome;
    String[] subjects;
    SharedPreferences prefs;
    String userid;
    ConstraintLayout rootHome;
    public LoadingManager loadingManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) requireActivity()).changeNavbarBtnColor(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        subjects = getResources().getStringArray(R.array.subjects);

        svHome = view.findViewById(R.id.svHome);
        svHome.setFocusable(false);
        svHome.setClickable(true);
        svHome.setFocusableInTouchMode(false);
        svHome.clearFocus();
        svHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).loadFragment(new SearchFragment());
            }
        });

        topbarHeadBtn = view.findViewById(R.id.topbarHeadBtn);
        topbarHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).loadFragment(new AccountFragment());
            }
        });

        rvHomePopularCourses = view.findViewById(R.id.rvHomePopularCourses);
        rvHomePopularCourses.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvHomePopularCoursesAdapter = new RvHomePopularCoursesAdapter(List.of());
        rvHomePopularCourses.setAdapter(rvHomePopularCoursesAdapter);

        rvHomeRecommend = view.findViewById(R.id.rvHomeRecommend);
        rvHomeRecommend.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvHomePopularCoursesAdapter = new RvHomePopularCoursesAdapter(List.of());
        rvHomeRecommend.setAdapter(rvHomePopularCoursesAdapter);


        btnHomeDS = view.findViewById(R.id.btnHomeDS);
        btnHomeWD = view.findViewById(R.id.btnHomeWD);
        btnHomeAI = view.findViewById(R.id.btnHomeAI);
        btnHomeC = view.findViewById(R.id.btnHomeC);
        btnHomeMD = view.findViewById(R.id.btnHomeMD);
        btnHomeCC = view.findViewById(R.id.btnHomeCC);
        btns = new Button[]{btnHomeDS, btnHomeWD, btnHomeAI, btnHomeC, btnHomeMD, btnHomeCC};

        tvHomeUsername = view.findViewById(R.id.tvHomeUsername);
        tvPopularCourseCategory = view.findViewById(R.id.tvPopularCourseCategory);

        btnHomeDS.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_selected));
        btnHomeDS.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        tvPopularCourseCategory.setText(subjects[0]);

        rootHome = view.findViewById(R.id.rootHome);
        loadingManager = new LoadingManager(rootHome , getContext());

        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[0]);
        //getTop10RatingCourses(apiService, rvHomeRecommend);
        getTop10Courses(apiService, rvHomeRecommend);
        getUser(apiService, userid);

        for (Button btn : btns){// set click listener for each button
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Button button : btns) {
                        if (button == btn) {
                            // selected button，set background color and text color
                            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_selected));
                            button.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        } else {
                            // unselected button，set background color and text color
                            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background));
                            button.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));
                        }
                    }
                    if(btn == btnHomeDS){
                        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[0]);
                    } else if (btn == btnHomeWD) {
                        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[1]);
                    } else if (btn == btnHomeAI) {
                        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[2]);
                    } else if (btn == btnHomeC) {
                        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[3]);
                    } else if (btn == btnHomeMD) {
                        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[4]);
                    } else if (btn == btnHomeCC) {
                        getTop10RatingCoursesBySubject(apiService, rvHomePopularCourses, subjects[5]);
                    }else {
                        return;
                    }
                }
            });
        }

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
                                .into(topbarHeadBtn);
                        tvHomeUsername.setText(userList.getFirst().getUsername());
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
    public void getAllCategories(ApiService apiService){
        List<Subject> subjectList = null;
        apiService.getAllSubjects().enqueue(new Callback<ApiResponse<List<Subject>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Subject>>> call, Response<ApiResponse<List<Subject>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Subject>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Subject> subjectList = apiResponse.getData();
                        rvHomeCategoriesAdapter = new RvHomeCategoriesAdapter(subjectList);
                        rvHomeCategories.setAdapter(rvHomeCategoriesAdapter);
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Subject>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }

    public void getTop10Courses(ApiService apiService, RecyclerView rv){
        List<Course> courseList = null;
        loadingManager.show();
        apiService.getTop10Courses().enqueue(new Callback<ApiResponse<List<Course>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call, Response<ApiResponse<List<Course>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Course> courseList = apiResponse.getData();
                        rvHomePopularCoursesAdapter = new RvHomePopularCoursesAdapter(courseList);
                        rv.setAdapter(rvHomePopularCoursesAdapter);
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
                loadingManager.hide();
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
                loadingManager.hide();
            }
        });
    }

    public void getTop10RatingCourses(ApiService apiService, RecyclerView rv){
        List<Course> courseList = null;
        apiService.getTop10RatingCourses().enqueue(new Callback<ApiResponse<List<Course>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call, Response<ApiResponse<List<Course>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Course> courseList = apiResponse.getData();
                        rvHomePopularCoursesAdapter = new RvHomePopularCoursesAdapter(courseList);
                        rv.setAdapter(rvHomePopularCoursesAdapter);
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }

    public void getTop10RatingCoursesBySubject(ApiService apiService, RecyclerView rv, String subject){
        List<Course> courseList = null;
        loadingManager.show();
        apiService.getTop10RatingCoursesBySubject(subject).enqueue(new Callback<ApiResponse<List<Course>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call, Response<ApiResponse<List<Course>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Course> courseList = apiResponse.getData();
                        tvPopularCourseCategory.setText(subject);
                        rvHomePopularCoursesAdapter = new RvHomePopularCoursesAdapter(courseList);
                        rv.setAdapter(rvHomePopularCoursesAdapter);
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
                loadingManager.hide();
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
                loadingManager.hide();
            }
        });
    }
}
