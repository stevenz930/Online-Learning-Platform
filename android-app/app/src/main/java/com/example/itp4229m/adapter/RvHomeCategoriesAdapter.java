package com.example.itp4229m.adapter;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.MainActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.fragment.HomeFragment;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Subject;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RvHomeCategoriesAdapter extends RecyclerView.Adapter<RvHomeCategoriesAdapter.ViewHolder> {
    private List<Subject> subjectList;
    private int selectedPosition = 0;

    public RvHomeCategoriesAdapter(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the instructor item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_home_categories, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data to the instructor item
        Subject categorySubject = subjectList.get(position);
        holder.bind(categorySubject);

        if(position == selectedPosition){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.btn_selected));
            holder.btnHomeRvItemCategory.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.btn_unselected));
            holder.btnHomeRvItemCategory.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_color));
        }

        // set left margin for the first item
        if (position == 0) {
            View itemView = holder.itemView;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = dpToPx(holder.itemView.getResources(),32);
            itemView.setLayoutParams(layoutParams);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedPosition(holder.getAdapterPosition());
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
        return subjectList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnHomeRvItemCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnHomeRvItemCategory = itemView.findViewById(R.id.btnHomeRvItemCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        public void bind(Subject categorySubject) {
            btnHomeRvItemCategory.setText(categorySubject.getSubjectName());
        }
    }
}
