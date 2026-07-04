package com.example.itp4229m.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.Review;

import java.util.List;

public class RvCourseDetailReviewAdapter extends RecyclerView.Adapter<RvCourseDetailReviewAdapter.ViewHolder> {

    private List<Review> reviewList;
    private Context context;

    public RvCourseDetailReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @NonNull
    @Override
    public RvCourseDetailReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_course_detail_review, parent, false);
        return new RvCourseDetailReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvCourseDetailReviewAdapter.ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Review review = reviewList.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // ViewHolder for Layout One
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCourseDetailReviewAvatar;
        RatingBar rbCourseDetailRvItemReview;
        TextView tvCourseDetailRvItemReviewUsername, tvCourseDetailRvItemReviewContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCourseDetailReviewAvatar = itemView.findViewById(R.id.ivCourseDetailReviewAvatar);
            rbCourseDetailRvItemReview = itemView.findViewById(R.id.rbCourseDetailRvItemReview);
            tvCourseDetailRvItemReviewUsername = itemView.findViewById(R.id.tvCourseDetailRvItemReviewUsername);
            tvCourseDetailRvItemReviewContent = itemView.findViewById(R.id.tvCourseDetailRvItemReviewContent);
        }

        public void bind(Review review) {
            Log.e("RVCDLRA", review.getAvatar());
            Glide.with(itemView.getContext())
                    .load(review.getAvatar())
                    .placeholder(R.drawable.avatar0)
                    .centerCrop()
                    .override(25, 25)
                    .into(ivCourseDetailReviewAvatar);
            rbCourseDetailRvItemReview.setRating(review.getRating());
            tvCourseDetailRvItemReviewUsername.setText(review.getUsername());
            tvCourseDetailRvItemReviewContent.setText(review.getComment());
        }
    }
}
