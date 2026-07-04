package com.example.itp4229m.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itp4229m.MainActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.adapter.RvHomeCategoriesAdapter;
import com.example.itp4229m.adapter.RvHomePopularCoursesAdapter;
import com.example.itp4229m.adapter.RvSearchResultAdapter;
import com.example.itp4229m.manager.LoadingManager;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Course;
import com.example.itp4229m.retrofit.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    SearchView svSearch;
    Button btnKeyword1, btnKeyword2, btnKeyword3, btnKeyword4, btnKeyword5, btnKeyword6;
    RecyclerView rvSearchResult;
    RvSearchResultAdapter adapter;
    ConstraintLayout rootSearch;
    public LoadingManager loadingManager;
    Button[] btnKeywords;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ((MainActivity) requireActivity()).changeNavbarBtnColor(this);

        Gson gson = new GsonBuilder()
                .setLenient()// allow lenient parsing of JSON
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        svSearch = view.findViewById(R.id.svSearch);

        rvSearchResult = view.findViewById(R.id.rvSearchResult);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RvSearchResultAdapter(List.of());
        rvSearchResult.setAdapter(adapter);

        btnKeyword1 = view.findViewById(R.id.btnKeyword1);
        btnKeyword2 = view.findViewById(R.id.btnKeyword2);
        btnKeyword3 = view.findViewById(R.id.btnKeyword3);
        btnKeyword4 = view.findViewById(R.id.btnKeyword4);
        btnKeyword5 = view.findViewById(R.id.btnKeyword5);
        btnKeyword6 = view.findViewById(R.id.btnKeyword6);
        btnKeywords = new Button[]{btnKeyword1, btnKeyword2, btnKeyword3, btnKeyword4, btnKeyword5, btnKeyword6};

        rootSearch = view.findViewById(R.id.rootSearch);


        loadingManager = new LoadingManager(rootSearch , getContext());

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(apiService, query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnKeyword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                search(apiService, btnKeyword1.getText().toString());
            }
        });

        for (Button btn: btnKeywords){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( btn == btnKeyword1 ){
                        search(apiService, btnKeyword1.getText().toString());
                    }else if( btn == btnKeyword2 ){
                        search(apiService, btnKeyword2.getText().toString());
                    }else if( btn == btnKeyword3 ){
                        search(apiService, btnKeyword3.getText().toString());
                    }else if( btn == btnKeyword4 ){
                        search(apiService, btnKeyword4.getText().toString());
                    }else if( btn == btnKeyword5 ){
                        search(apiService, btnKeyword5.getText().toString());
                    }else if( btn == btnKeyword6 ){
                        search(apiService, btnKeyword6.getText().toString());
                    }
                }
            });
        }


        //search(apiService, "data");

        return view;
    }

    public void search(ApiService apiService, String keyword){
        Log.e("API_ERROR", "keyword: " + keyword);
        List<Course> courseList = null;
        loadingManager.show();
        apiService.search(keyword).enqueue(new Callback<ApiResponse<List<Course>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call, Response<ApiResponse<List<Course>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course>> apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        List<Course> courseList = apiResponse.getData();
                        //Log.e("API_ERROR", "error: " + courseList.getFirst().getTitle());
                        adapter = new RvSearchResultAdapter(courseList);
                        rvSearchResult.setAdapter(adapter);
                    }else {
                        Toast.makeText(getContext(), "No result", Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                    loadingManager.hide();
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                    loadingManager.hide();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
                loadingManager.hide();
            }
        });
    }
}
