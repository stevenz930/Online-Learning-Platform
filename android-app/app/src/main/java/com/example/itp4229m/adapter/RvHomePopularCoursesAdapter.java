package com.example.itp4229m.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.CourseDetailActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.Course;

import java.util.List;

public class RvHomePopularCoursesAdapter extends RecyclerView.Adapter<RvHomePopularCoursesAdapter.ViewHolder> {
    private List<Course> coursesList;
    private Context context;
    public RvHomePopularCoursesAdapter(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the instructor item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_courses, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Course course = coursesList.get(position);
        holder.bind(course);

        // set left margin for the first item
        View itemView = holder.itemView;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (position == 0) {
            layoutParams.leftMargin = dpToPx(holder.itemView.getResources(),32);
        }else{
            layoutParams.leftMargin = 0;
        }
        itemView.setLayoutParams(layoutParams);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CourseDetailActivity.class);
                i.putExtra("courseId", course.getId());
                context.startActivity(i);
            }
        });
    }

    // convert dp to pixel
    private int dpToPx(Resources resources, int dp) {
        float density = resources.getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHomeRvItemPopularCourseTitle, tvHomeRvItemPopularCourseInstructor, tvHomeRvItemPopularCourseReviewCount;
        RatingBar rbHomeRvItemPopularCourse;
        ImageView ivHomeRvItemPopularCourseThumnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHomeRvItemPopularCourseTitle = itemView.findViewById(R.id.tvHomeRvItemPopularCourseTitle);
            tvHomeRvItemPopularCourseInstructor = itemView.findViewById(R.id.tvHomeRvItemPopularCourseInstructor);
            tvHomeRvItemPopularCourseReviewCount = itemView.findViewById(R.id.tvHomeRvItemPopularCourseReviewCount);
            rbHomeRvItemPopularCourse = itemView.findViewById(R.id.rbHomeRvItemPopularCourse);
            ivHomeRvItemPopularCourseThumnail = itemView.findViewById(R.id.ivHomeRvItemPopularCourseThumnail);
        }


        public void bind(Course course) {
            tvHomeRvItemPopularCourseTitle.setText(course.getTitle());
            tvHomeRvItemPopularCourseInstructor.setText("By "+course.getInstructorName());
            tvHomeRvItemPopularCourseReviewCount.setText("("+course.getReviewCount()+")");
            rbHomeRvItemPopularCourse.setRating(course.getAvgRating());
            ivHomeRvItemPopularCourseThumnail.setImageResource(R.drawable.ic_launcher_background);
            Glide.with(itemView.getContext())
                    .load(course.getThumbnail())
                    .placeholder(R.drawable.death_valley_dunes)
                    .override(240,120)
                    .into(ivHomeRvItemPopularCourseThumnail);
        }
    }
}
