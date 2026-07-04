package com.example.itp4229m.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.CourseDetailActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.Course;

import java.util.List;

public class RvCoursesNextEnrolledAdapter extends RecyclerView.Adapter<RvCoursesNextEnrolledAdapter.ViewHolder> {
    private List<Course> coursesList;
    private Context context;
    public RvCoursesNextEnrolledAdapter(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    @NonNull
    public RvCoursesNextEnrolledAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the instructor item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_courses_next_enrolled, parent, false);
        return new RvCoursesNextEnrolledAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvCoursesNextEnrolledAdapter.ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Course course = coursesList.get(position);
        holder.bind(course);

        View itemView = holder.itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CourseDetailActivity.class);
                i.putExtra("courseId", course.getId());
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return coursesList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoursesNextEnrolledTitle, tvCoursesNextEnrolledInstructor, tvCoursesNextEnrolledLessonProgress, tvCoursesNextEnrolledPercent;
        ImageView ivCoursesNextEnrolledThumbnail;
        ProgressBar pbCoursesNextEnrolled;

        int progressPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoursesNextEnrolledTitle = itemView.findViewById(R.id.tvEnrollTitle);
            tvCoursesNextEnrolledInstructor = itemView.findViewById(R.id.tvEnrollInstructor);
            tvCoursesNextEnrolledLessonProgress = itemView.findViewById(R.id.tvCoursesNextEnrolledLessonProgress);
            tvCoursesNextEnrolledPercent = itemView.findViewById(R.id.tvCoursesNextEnrolledPercent);
            ivCoursesNextEnrolledThumbnail = itemView.findViewById(R.id.ivEnrollThumbnail);
            pbCoursesNextEnrolled = itemView.findViewById(R.id.pbCoursesNextEnrolled);
        }


        public void bind(Course course) {
            tvCoursesNextEnrolledTitle.setText(course.getTitle());
            tvCoursesNextEnrolledInstructor.setText("By "+course.getInstructorName());
            tvCoursesNextEnrolledLessonProgress.setText(course.getCompletedLessons() + "/" + course.getTotalLessons());
            tvCoursesNextEnrolledPercent.setText(course.getProgressPercent() + "%");

            Glide.with(itemView.getContext())
                    .load(course.getThumbnail())
                    .placeholder(R.drawable.death_valley_dunes)
                    .override(140,90)
                    .centerCrop()
                    .into(ivCoursesNextEnrolledThumbnail);

            progressPercent = Integer.parseInt(course.getProgressPercent());
            pbCoursesNextEnrolled.setProgress(progressPercent, true);

        }
    }
}
