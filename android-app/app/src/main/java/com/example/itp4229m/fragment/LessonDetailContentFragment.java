package com.example.itp4229m.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvCourseDetailLessonAdapter;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Course;
import com.example.itp4229m.retrofit.Lesson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonDetailContentFragment extends Fragment {

    TextView tvLessonDetailContent, tvLessonDetailContentInstructorName, tvLessonDetailContentLastUpdate, tvLessonDetailContentCreateAt;
    SharedPreferences prefs;
    String userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_lesson_detail_content, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        tvLessonDetailContent = view.findViewById(R.id.tvLessonDetailContent);
        tvLessonDetailContentInstructorName = view.findViewById(R.id.tvLessonDetailContentInstructorName);
        tvLessonDetailContentLastUpdate = view.findViewById(R.id.tvLessonDetailContentLastUpdate);
        tvLessonDetailContentCreateAt = view.findViewById(R.id.tvLessonDetailContentCreateAt);

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        Bundle args = getArguments();
        if (args != null) {
            String courseId = args.getString("id");
            String lessonOrder = args.getString("lessonOrder");
            //Log.e("FCV", "arguments:"+id);
            getLesson(apiService, courseId, lessonOrder);
            getCourse(apiService, userid, courseId);
        }

        return view;
    }


    public void getLesson(ApiService apiService, String courseId, String lessonOrder){
        Log.e("RVCDLRA", "lessonList.get(0).getTitle(0)");
        List<Lesson> lessonList = null;
        apiService.getLesson(courseId, lessonOrder).enqueue(new Callback<ApiResponse<List<Lesson> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Lesson> >> call, Response<ApiResponse<List<Lesson> >> response) {
                Log.e("RVCDLRA", "lessonList.get(0).getTitle(1)");
                if (response.isSuccessful()) {
                    ApiResponse<List<Lesson> > apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        List<Lesson> lessonList = apiResponse.getData();
                        Log.e("RVCDLRA", lessonList.get(0).getTitle());
                        tvLessonDetailContent.setText(lessonList.getFirst().getContent());
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                        Log.e("API_ERROR", "error status: " + courseId);
                        Log.e("API_ERROR", "error status: " + lessonOrder);
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Lesson> >> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }

    public void getCourse(ApiService apiService, String userid, String courseId){
        List<Course> courseList = null;
        apiService.getCourse(userid, courseId).enqueue(new Callback<ApiResponse<List<Course> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course> >> call, Response<ApiResponse<List<Course> >> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course> > apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        List<Course> courseList = apiResponse.getData();
                        tvLessonDetailContentInstructorName.setText(courseList.getFirst().getInstructorName());
                        tvLessonDetailContentLastUpdate.setText("Last Update: " + courseList.getFirst().getUpdatedAt());
                        tvLessonDetailContentCreateAt.setText("Create At: " + courseList.getFirst().getCreatedAt());
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course> >> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }
}
