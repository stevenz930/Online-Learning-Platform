package com.example.itp4229m;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itp4229m.adapter.RvCartAdapter;
import com.example.itp4229m.adapter.RvCourseDetailReviewAdapter;
import com.example.itp4229m.adapter.RvEnrollAdapter;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Cart;
import com.example.itp4229m.retrofit.Enrollment;
import com.example.itp4229m.retrofit.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnrollActivity extends AppCompatActivity {

    RecyclerView rvEnroll;
    TextView tvEnrollTotalPrice;
    Button btnEnrollPayNow;
    SharedPreferences prefs;
    String userid;
    RvEnrollAdapter adapter;
    BottomSheetDialog dialog;
    List<Cart> cartList;
    double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enroll);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        cartList = null;
        totalPrice = 0.0;

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        tvEnrollTotalPrice = findViewById(R.id.tvEnrollTotalPrice);
        btnEnrollPayNow = findViewById(R.id.btnEnrollPayNow);

        btnEnrollPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new BottomSheetDialog(EnrollActivity.this);
                View dialogView = LayoutInflater.from(EnrollActivity.this).inflate(R.layout.bsl_pay, null);
                dialog.setContentView(dialogView);
                dialog.show();

                Button btnEnrollPayNow = dialogView.findViewById(R.id.btnEnrollPayNow);
                TextView tvPayTotalPrice = dialogView.findViewById(R.id.tvPayTotalPrice);
                tvPayTotalPrice.setText("$" + totalPrice);

                getCart(apiService, userid);

                btnEnrollPayNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idTemp = "";
                        for (Cart cart : cartList) {
                            idTemp += cart.getCourseId() + ", ";
                            postEnrollment(apiService, userid, cart.getCourseId());
                        }

                        //Toast.makeText(EnrollActivity.this, "Paid: " + idTemp, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EnrollActivity.this, MainActivity.class));
                    }
                });
            }
        });

        rvEnroll = findViewById(R.id.rvEnroll);
        rvEnroll.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RvEnrollAdapter(List.of());
        rvEnroll.setAdapter(adapter);

        getCart(apiService, userid);
    }

    public void getCart(ApiService apiService, String userid){
        apiService.getCart(userid).enqueue(new Callback<ApiResponse<List<Cart> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Cart> >> call, Response<ApiResponse<List<Cart> >> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Cart> > apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        cartList = apiResponse.getData();
                        if (cartList != null){
                            adapter = new RvEnrollAdapter(cartList);
                            rvEnroll.setAdapter(adapter);

                            for (Cart cart : cartList) {
                                totalPrice += cart.getPrice().doubleValue();
                            }
                            tvEnrollTotalPrice.setText("$" + totalPrice);
                        }else{
                            adapter = new RvEnrollAdapter(List.of());
                            rvEnroll.setAdapter(adapter);
                        }
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Cart> >> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }


    public void postEnrollment(ApiService apiService, String userid, String courseId) {
        apiService.postEnrollment(userid, courseId).enqueue(new Callback<ApiResponse<Enrollment>>() {
            @Override
            public void onResponse(Call<ApiResponse<Enrollment>> call, Response<ApiResponse<Enrollment>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Enrollment> apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        Log.e("API_SUCCESS", "status: " + apiResponse.getStatus());
                        //Toast.makeText(CourseDetailActivity.this,"Add to cart"+userid+" "+courseId, Toast.LENGTH_SHORT).show();
                    }else if (apiResponse.getStatus().equals("error")) {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Enrollment>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }



}