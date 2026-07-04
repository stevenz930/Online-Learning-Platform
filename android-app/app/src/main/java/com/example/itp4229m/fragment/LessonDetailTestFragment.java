package com.example.itp4229m.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.itp4229m.R;

public class LessonDetailTestFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_lesson_detail_test, container, false);

        //Retrofit retrofit = new Retrofit.Builder()
        //        .baseUrl(getString(R.string.api_url))
        //        .addConverterFactory(GsonConverterFactory.create())
        //        .build();
        //ApiService apiService = retrofit.create(ApiService.class);

        //rvCourseDetailLesson = view.findViewById(R.id.rvCourseDetailLesson);
        //rvCourseDetailLesson.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        //adapter = new RvCourseDetailLessonAdapter(List.of());
        //rvCourseDetailLesson.setAdapter(adapter);

        //Bundle args = getArguments();
        //if (args != null) {
        //    String id = args.getString("id");
        //    //Log.e("FCV", "arguments:"+id);
        //    getAllLessonByCourse(apiService, rvCourseDetailLesson, id);
        //}

        return view;
    }
}
