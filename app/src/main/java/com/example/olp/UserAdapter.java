package com.example.olp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.olp.room.model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, usernameTextView, passWordTextView, fullNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            passWordTextView = itemView.findViewById(R.id.passWordTextView);
            fullNameTextView = itemView.findViewById(R.id.fullNameTextView);
        }

        public void bind(User user) {
            idTextView.setText("ID: " + user.id);
            usernameTextView.setText("Username: " + user.username);
            passWordTextView.setText("Password: " + user.passWord);
            fullNameTextView.setText("Full Name: " + user.firstName + " " + user.lastName);
        }
    }
}

