package com.example.itp4229m.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvCourseDetailLessonAdapter;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Lesson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseDetailLessonFragment extends Fragment {
    
    RecyclerView rvCourseDetailLesson;
    RvCourseDetailLessonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_course_detail_lesson, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        rvCourseDetailLesson = view.findViewById(R.id.rvCourseDetailLesson);
        rvCourseDetailLesson.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RvCourseDetailLessonAdapter(List.of());
        rvCourseDetailLesson.setAdapter(adapter);

        Bundle args = getArguments();
        if (args != null) {
            String id = args.getString("id");
            //Log.e("FCV", "arguments:"+id);
            getAllLessonByCourse(apiService, rvCourseDetailLesson, id);
        }

        return view;
    }

    public void getAllLessonByCourse(ApiService apiService, RecyclerView rv, String courseId){
        List<Lesson> lessonList = null;
        apiService.getAllLessonByCourse(courseId).enqueue(new Callback<ApiResponse<List<Lesson> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Lesson> >> call, Response<ApiResponse<List<Lesson> >> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Lesson> > apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Lesson> lessonList = apiResponse.getData();
                        //Log.e("RVCDLRA", lessonList.get(0).getTitle());
                        //Log.e("RVCDLRA", lessonList.get(1).getTitle());
                        adapter = new RvCourseDetailLessonAdapter(lessonList);
                        rv.setAdapter(adapter);
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
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
}
