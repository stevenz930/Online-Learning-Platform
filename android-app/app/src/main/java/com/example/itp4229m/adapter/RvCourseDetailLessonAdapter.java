package com.example.itp4229m.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.LessonDetailActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.Lesson;

import java.util.List;

public class RvCourseDetailLessonAdapter extends RecyclerView.Adapter<RvCourseDetailLessonAdapter.ViewHolder> {

    private List<Lesson> lessonList;
    private Context context;

    public RvCourseDetailLessonAdapter(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_course_detail_lesson, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Lesson lesson = lessonList.get(position);
        holder.bind(lesson);

        View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LessonDetailActivity.class);
                i.putExtra("courseId", lesson.getCourseId());
                i.putExtra("lessonOrder", lesson.getLessonOrder());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    // ViewHolder for Layout One
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCourseDetailLessonCover;
        TextView tvCourseDetailRvItemLessonTitle, tvCourseDetailRvItemLessonDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCourseDetailLessonCover = itemView.findViewById(R.id.ivCourseDetailLessonCover);
            tvCourseDetailRvItemLessonTitle = itemView.findViewById(R.id.tvCourseDetailRvItemLessonTitle);
            tvCourseDetailRvItemLessonDuration = itemView.findViewById(R.id.tvCourseDetailRvItemLessonDuration);
        }

        public void bind(Lesson lesson) {
            //Log.e("RVCDLRA", lesson.getTitle());
            Glide.with(itemView.getContext())
                    .load(lesson.getCover())
                    .placeholder(R.drawable.death_valley_dunes)
                    .centerCrop()
                    .override(120, 90)
                    .into(ivCourseDetailLessonCover);
            tvCourseDetailRvItemLessonTitle.setText(lesson.getTitle());

            long minutes = ( lesson.getDuration() / 1_000_000 ) / 60;
            long seconds = ( lesson.getDuration() / 1_000_000 ) % 60;
            tvCourseDetailRvItemLessonDuration.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }
}

