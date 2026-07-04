package com.example.itp4229m.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.CourseDetailActivity;
import com.example.itp4229m.EnrollActivity;
import com.example.itp4229m.MainActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvCartAdapter;
import com.example.itp4229m.adapter.RvCoursesNextEnrolledAdapter;
import com.example.itp4229m.adapter.RvHomePopularCoursesAdapter;
import com.example.itp4229m.manager.LoadingManager;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Cart;
import com.example.itp4229m.retrofit.Course;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoursesFragment extends Fragment {
    TextView tvCourseFirstTitle, tvCartEmpty;
    ImageView ivCourseFisrst;
    CardView btnCourseCart;
    SharedPreferences prefs;
    String userid;
    public LoadingManager loadingManager;
    ConstraintLayout rootHome;
    RvCoursesNextEnrolledAdapter adapter;
    RecyclerView rvCourse;
    BottomSheetDialog dialog;
    RecyclerView rvCart;
    RvCartAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        ((MainActivity) requireActivity()).changeNavbarBtnColor(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        rootHome = view.findViewById(R.id.CourseFragmentRoot);
        loadingManager = new LoadingManager(rootHome , getContext());

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        tvCourseFirstTitle = view.findViewById(R.id.tvCourseFirstTitle);
        ivCourseFisrst = view.findViewById(R.id.ivCourseFirst);
        btnCourseCart = view.findViewById(R.id.btnCourseCart);

        btnCourseCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new BottomSheetDialog(requireContext());
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.bsl_cart, null);
                dialog.setContentView(dialogView);
                dialog.show();

                rvCart = dialogView.findViewById(R.id.rvCart);
                rvCart.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                cartAdapter = new RvCartAdapter(List.of());
                cartAdapter.setOnCartItemDeleteListener(new RvCartAdapter.OnCartItemDeleteListener() {
                    @Override
                    public void onItemDeleted() {
                        getCart(apiService, userid);
                    }
                    @Override
                    public void onRefreshActivity() {
                    }
                });
                rvCart.setAdapter(cartAdapter);

                tvCartEmpty = dialogView.findViewById(R.id.tvCartEmpty);
                Button btnCartEnrollNow = dialogView.findViewById(R.id.btnCartEnrollNow);

                getCart(apiService, userid);

                btnCartEnrollNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(requireActivity(), EnrollActivity.class));
                    }
                });
            }
        });

        rvCourse = view.findViewById(R.id.rvCourse);
        rvCourse.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RvCoursesNextEnrolledAdapter(List.of());
        rvCourse.setAdapter(adapter);

        getRecentEnrolledCourses(apiService, userid);

        return view;
    }

    public void getRecentEnrolledCourses(ApiService apiService, String courseId){
        List<Course> courseData = null;
        loadingManager.show();
        apiService.getRecentEnrolledCourses(courseId).enqueue(new Callback<ApiResponse<List<Course>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call, Response<ApiResponse<List<Course>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Course> courseData = apiResponse.getData();

                        tvCourseFirstTitle.setText(courseData.getFirst().getTitle());
                        Glide.with(requireContext())
                                .load(courseData.getFirst().getThumbnail())
                                .centerCrop()
                                .placeholder(R.drawable.death_valley_dunes)
                                .into(ivCourseFisrst);
                        String firstCourseId = courseData.getFirst().getId();
                        ivCourseFisrst.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(requireContext(), CourseDetailActivity.class);
                                i.putExtra("courseId", firstCourseId);
                                requireContext().startActivity(i);
                            }
                        });

                        courseData.removeFirst();

                        adapter = new RvCoursesNextEnrolledAdapter(courseData);
                        rvCourse.setAdapter(adapter);

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

    public void getCart(ApiService apiService, String userid){
        List<Cart> cartList = null;
        apiService.getCart(userid).enqueue(new Callback<ApiResponse<List<Cart> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Cart> >> call, Response<ApiResponse<List<Cart> >> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Cart> > apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        List<Cart> cartList = apiResponse.getData();
                        if (cartList != null){
                            cartAdapter.updateCartList(cartList);
                            tvCartEmpty.setVisibility(View.INVISIBLE);
                        }else{
                            cartAdapter = new RvCartAdapter(List.of());
                            rvCart.setAdapter(cartAdapter);
                            tvCartEmpty.setVisibility(View.VISIBLE);
                        }
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Cart> >> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }
}
