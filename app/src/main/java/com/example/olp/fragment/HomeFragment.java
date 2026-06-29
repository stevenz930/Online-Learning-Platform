package com.example.olp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.olp.R;
import com.example.olp.UserAdapter;
import com.example.olp.room.model.User;
import com.example.olp.room.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    //private AppDatabase appDatabase;
    public Button btnAdd, btnAdd2, btnDelete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd2 = view.findViewById(R.id.btnAdd2);
        btnDelete = view.findViewById(R.id.btnDelete);

        recyclerView = view.findViewById(R.id.rvUserlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(userAdapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // 观察数据变化
        userViewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter = new UserAdapter(users);
                recyclerView.setAdapter(userAdapter);  // update adapter
            }
        });

        // insert data
        btnAdd.setOnClickListener(v -> {
            userViewModel.insert(new User("John9527", "pwd","John","Lee",
                    "John9527@1.com","/","/","/","/","/",
                    " ", false, false));

        });
        btnAdd2.setOnClickListener(v -> {
            userViewModel.insert(new User("Mary001", "pwd","Mary","Jane",
                    "Mary001@1.com","/","/","/","/","/",
                    " ", false, false));
        });
        btnDelete.setOnClickListener(v -> {
            userViewModel.deleteAll();
        });

        return view;
    }
}
