package com.example.itp4229m.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.CourseDetailActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.Course;

import java.util.List;

public class RvSearchResultAdapter extends RecyclerView.Adapter<RvSearchResultAdapter.ViewHolder> {
    private List<Course> coursesList;
    private Context context;
    public RvSearchResultAdapter(List<Course> coursesList) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_search_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Course course = coursesList.get(position);
        holder.bind(course);

        View itemView = holder.itemView;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (position == 0) {
            layoutParams.topMargin = dpToPx(holder.itemView.getResources(),32);
        }else{
            layoutParams.topMargin = 0;
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
        TextView tvSearchResultTitle, tvSearchResultInstructor, tvSearchResultPrice;
        ImageView ivSearchResultThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchResultTitle = itemView.findViewById(R.id.tvEnrollTitle);
            tvSearchResultInstructor = itemView.findViewById(R.id.tvEnrollInstructor);
            tvSearchResultPrice = itemView.findViewById(R.id.tvEnrollPrice);
            ivSearchResultThumbnail = itemView.findViewById(R.id.ivEnrollThumbnail);
        }


        public void bind(Course course) {
            tvSearchResultTitle.setText(course.getTitle());
            tvSearchResultInstructor.setText("By "+course.getInstructorName());

            if (Double.valueOf(course.getReviewCount()) == 0){
                tvSearchResultPrice.setText("Free");
            }else {
                tvSearchResultPrice.setText("$"+course.getPrice());
            }

            Glide.with(itemView.getContext())
                    .load(course.getThumbnail())
                    .placeholder(R.drawable.death_valley_dunes)
                    .override(120,90)
                    .centerCrop()
                    .into(ivSearchResultThumbnail);
        }
    }
}
