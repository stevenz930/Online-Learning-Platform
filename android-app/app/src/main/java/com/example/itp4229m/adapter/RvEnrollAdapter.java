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
import com.example.itp4229m.retrofit.Cart;

import java.util.List;

public class RvEnrollAdapter extends RecyclerView.Adapter<RvEnrollAdapter.ViewHolder> {
    private List<Cart> cartList;
    private Context context;
    public RvEnrollAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    @NonNull
    public RvEnrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the instructor item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_enroll, parent, false);
        return new RvEnrollAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvEnrollAdapter.ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Cart cart = cartList.get(position);
        holder.bind(cart);

        View itemView = holder.itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CourseDetailActivity.class);
                i.putExtra("courseId", cart.getId());
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnrollTitle, tvEnrollInstructor, tvEnrollPrice;
        ImageView ivEnrollThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEnrollTitle = itemView.findViewById(R.id.tvEnrollTitle);
            tvEnrollInstructor = itemView.findViewById(R.id.tvEnrollInstructor);
            tvEnrollPrice = itemView.findViewById(R.id.tvEnrollPrice);
            ivEnrollThumbnail = itemView.findViewById(R.id.ivEnrollThumbnail);
        }


        public void bind(Cart cart) {
            tvEnrollTitle.setText(cart.getTitle());
            tvEnrollInstructor.setText("By "+cart.getInstructorName());

            if (cart.getPrice().equals(0)){
                tvEnrollPrice.setText("Free");
            }else {
                tvEnrollPrice.setText("$"+cart.getPrice());
            }

            Glide.with(itemView.getContext())
                    .load(cart.getThumbnail())
                    .placeholder(R.drawable.death_valley_dunes)
                    .centerCrop()
                    .into(ivEnrollThumbnail);
        }
    }
}
