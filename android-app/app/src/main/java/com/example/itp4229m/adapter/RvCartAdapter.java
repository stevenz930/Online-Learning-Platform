package com.example.itp4229m.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.CourseDetailActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RvCartAdapter extends RecyclerView.Adapter<RvCartAdapter.ViewHolder> {
    private List<Cart> cartList;
    private Context context;
    SharedPreferences prefs;
    String userid;

    public interface OnCartItemDeleteListener {
        void onItemDeleted();
        void onRefreshActivity();
    }

    private OnCartItemDeleteListener deleteListener;

    public void setOnCartItemDeleteListener(OnCartItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    public void updateCartList(List<Cart> newCartList) {
        this.cartList = newCartList;
        notifyDataSetChanged();
    }

    public RvCartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    @NonNull
    public RvCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the instructor item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_cart, parent, false);
        return new RvCartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvCartAdapter.ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Cart cart = cartList.get(position);
        holder.bind(cart);

        View itemView = holder.itemView;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CourseDetailActivity.class);
                i.putExtra("courseId", cart.getCourseId());
                context.startActivity(i);
            }
        });

        prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        holder.btnCartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCartItem(apiService, userid, cart.getCourseId());

            }
        });
    }


    public void deleteCartItem(ApiService apiService, String userid, String courseId) {
        apiService.deleteCartItem(userid, courseId).enqueue(new Callback<ApiResponse<Cart>>() {
            @Override
            public void onResponse(Call<ApiResponse<Cart>> call, Response<ApiResponse<Cart>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Cart> apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        Log.e("API_SUCCESS", "status: " + apiResponse.getStatus());
                        //Toast.makeText(context,"Item removed from cart"+userid+" "+courseId, Toast.LENGTH_SHORT).show();
                        if (deleteListener != null) {
                            deleteListener.onItemDeleted();
                            deleteListener.onRefreshActivity();
                        }
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Cart>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCartTitle, tvCartInstructor, tvCartPrice;
        ImageView ivCartThumbnail, btnCartRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartTitle = itemView.findViewById(R.id.tvCartTitle);
            tvCartInstructor = itemView.findViewById(R.id.tvCartInstructor);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            ivCartThumbnail = itemView.findViewById(R.id.ivCartThumbnail);
            btnCartRemove = itemView.findViewById(R.id.btnCartRemove);
        }


        public void bind(Cart cart) {
            tvCartTitle.setText(cart.getTitle());
            tvCartInstructor.setText("By "+cart.getInstructorName());

            if (cart.getPrice().doubleValue() == 0) {
                tvCartPrice.setText("Free");
            }else {
                tvCartPrice.setText("$"+cart.getPrice());
            }

            Glide.with(itemView.getContext())
                    .load(cart.getThumbnail())
                    .placeholder(R.drawable.death_valley_dunes)
                    .centerCrop()
                    .into(ivCartThumbnail);
        }
    }
}
