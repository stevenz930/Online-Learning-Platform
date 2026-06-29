package com.example.olp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olp.R;
import com.example.olp.CourseAdapter;
import com.example.olp.room.model.Course;
import com.example.olp.room.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {
    private CourseViewModel courseViewModel;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    public Button btnAddCourse, btnAddCourse2, btnDeleteCourse;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        btnAddCourse = view.findViewById(R.id.btnAddCourse);
        btnAddCourse2 = view.findViewById(R.id.btnAddCourse2);
        btnDeleteCourse = view.findViewById(R.id.btnDeleteCourse);

        recyclerView = view.findViewById(R.id.rvCourselist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courseAdapter = new CourseAdapter(new ArrayList<>());
        recyclerView.setAdapter(courseAdapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // 观察数据变化
        courseViewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> course) {
                courseAdapter = new CourseAdapter(course);
                recyclerView.setAdapter(courseAdapter);  // update adapter
            }
        });

        // insert data
        btnAddCourse.setOnClickListener(v -> {
            courseViewModel.insert(new Course("Math", " ","AD","/",
                    " "," ",19.99));

        });
        btnAddCourse2.setOnClickListener(v -> {
            courseViewModel.insert(new Course("English", " ","IM","/",
                    " "," ",0));
        });
        btnDeleteCourse.setOnClickListener(v -> {
            courseViewModel.deleteAll();
        });

        return view;
    }
}
