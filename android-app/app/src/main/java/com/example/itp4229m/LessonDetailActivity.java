package com.example.itp4229m;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.itp4229m.fragment.CourseDetailLessonFragment;
import com.example.itp4229m.fragment.CourseDetailReviewFragment;
import com.example.itp4229m.fragment.LessonDetailContentFragment;
import com.example.itp4229m.fragment.LessonDetailTestFragment;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Course;
import com.example.itp4229m.retrofit.Lesson;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonDetailActivity extends AppCompatActivity {

    FrameLayout flLessonVideoContainer;
    VideoView vvLessonDetail;
    TabLayout tlLessonDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Intent i = getIntent();
        String courseId = i.getStringExtra("courseId");
        String lessonOrder = i.getStringExtra("lessonOrder");

        flLessonVideoContainer = findViewById(R.id.flLessonVideoContainer);
        vvLessonDetail = findViewById(R.id.vvLessonDetail);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
        vvLessonDetail.setVideoURI(Uri.parse(videoPath));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(flLessonVideoContainer);
        vvLessonDetail.setMediaController(mediaController);
        vvLessonDetail.start();


        Bundle args = new Bundle();
        args.putString("id", courseId);
        args.putString("lessonOrder", lessonOrder);


        tlLessonDetail = findViewById(R.id.tlLessonDetail);
        tlLessonDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("FCV", String.valueOf(tab.getPosition()));
                switch (tab.getPosition()) {
                    case 0:
                        loadFragment(new LessonDetailContentFragment(), args);
                        break;
                    case 1:
                        loadFragment(new LessonDetailTestFragment(), args);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        tlLessonDetail.getTabAt(0).select();
        loadFragment(new LessonDetailContentFragment(), args);

        getLesson(apiService, courseId, lessonOrder);

    }


    public void loadFragment(Fragment fragment, Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fcvLessonDetail, fragment);
        fragment.setArguments(args);
        transaction.commit();
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


}