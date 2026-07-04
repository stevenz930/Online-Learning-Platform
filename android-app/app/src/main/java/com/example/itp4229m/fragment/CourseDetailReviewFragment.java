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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvCourseDetailReviewAdapter;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Review;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseDetailReviewFragment extends Fragment {
    Button btnCourseDetailPostReview;
    RecyclerView rvCourseDetailReview;
    RvCourseDetailReviewAdapter adapter;
    SharedPreferences prefs;
    String userid;
    String courseid;
    BottomSheetDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_course_detail_review, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        Bundle args = getArguments();
        if (args != null) {
            courseid = args.getString("id");
            Log.e("FCV", "arguments:"+courseid);
        }

        btnCourseDetailPostReview = view.findViewById(R.id.btnCourseDetailPostReview);

        rvCourseDetailReview = view.findViewById(R.id.rvCourseDetailReview);
        rvCourseDetailReview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RvCourseDetailReviewAdapter(List.of());
        rvCourseDetailReview.setAdapter(adapter);


        btnCourseDetailPostReview.setOnClickListener(v -> {
            dialog = new BottomSheetDialog(requireContext());
            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.bsl_post_review, null);
            dialog.setContentView(dialogView);
            dialog.show();

            RatingBar rbPostReview = dialogView.findViewById(R.id.rbPostReview);
            TextView tvPostReviewRating = dialogView.findViewById(R.id.tvPostReviewRating);
            EditText etPostReview = dialogView.findViewById(R.id.etPostReview);
            Button btnPostReviewClear = dialogView.findViewById(R.id.btnPostReviewClear);
            Button btnPostReviewSend = dialogView.findViewById(R.id.btnCartEnrollNow);

            rbPostReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    tvPostReviewRating.setText(String.format("%.1f", rating));
                }
            });
            btnPostReviewClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etPostReview.setText("");
                }
            });
            btnPostReviewSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postReview(apiService, userid, courseid, rbPostReview.getRating(), etPostReview.getText().toString());
                }
            });
        });

        getAllReviewByCourse(apiService, rvCourseDetailReview, courseid);

        return view;
    }

    public void getAllReviewByCourse(ApiService apiService, RecyclerView rv, String courseId){
        List<Review> reviewList = null;
        apiService.getAllReviewByCourse(courseId).enqueue(new Callback<ApiResponse<List<Review> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Review> >> call, Response<ApiResponse<List<Review> >> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Review> > apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        List<Review> reviewList = apiResponse.getData();
                        if (reviewList != null){
                            adapter = new RvCourseDetailReviewAdapter(reviewList);
                            rv.setAdapter(adapter);
                        }else{
                            adapter = new RvCourseDetailReviewAdapter(List.of());
                            rvCourseDetailReview.setAdapter(adapter);
                        }
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Review> >> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }

    public void postReview(ApiService apiService, String id, String course_id, float rating, String comment){
        Call<ApiResponse<Review>> call = apiService.postReview(id, course_id, rating, comment);
        call.enqueue(new Callback<ApiResponse<Review>>() {
            @Override
            public void onResponse(Call<ApiResponse<Review>> call, Response<ApiResponse<Review>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Review> apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        Toast.makeText(requireContext(), "Review posted successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getAllReviewByCourse(apiService, rvCourseDetailReview, courseid);
                    }else {
                        Log.e("API_ERROR", "error Message: " + apiResponse.getMessage());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Review>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }
}
