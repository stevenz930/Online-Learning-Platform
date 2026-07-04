package com.example.itp4229m;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.example.itp4229m.adapter.RvCourseDetailLessonReviewAdapter;
import com.example.itp4229m.adapter.RvCartAdapter;
import com.example.itp4229m.adapter.RvEnrollAdapter;
import com.example.itp4229m.fragment.CourseDetailLessonFragment;
import com.example.itp4229m.fragment.CourseDetailReviewFragment;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.Cart;
import com.example.itp4229m.retrofit.Course;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseDetailActivity extends AppCompatActivity {
    ImageView ivCourseThumbnail;
    TextView tvCourseTitle, tvCoursePrice, tvCourseDescription, tvCartEmpty;
    TabLayout tlCourseDetail;
    Button btnCourseDetailAdd2Cart, btnCourseDetailEnrolNow;
    CardView btnCourseDetailBack2Home, btnCourseDetailCart;
    BottomSheetDialog dialog;
    SharedPreferences prefs;
    String userid;
    RecyclerView rvCart;
    RvCartAdapter adapter;
    LinearLayout llCourseDetailBottomButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.e("FUCK", "FUCK");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Intent i = getIntent();
        String courseId = i.getStringExtra("courseId");

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        userid = prefs.getString("userid", null);

        ivCourseThumbnail = findViewById(R.id.ivCourseThumbnail);

        tvCourseTitle = findViewById(R.id.tvCourseTitle);
        tvCoursePrice = findViewById(R.id.tvCoursePrice);
        tvCourseDescription = findViewById(R.id.tvCourseDescription);
        btnCourseDetailCart = findViewById(R.id.btnCourseDetailCart);
        btnCourseDetailAdd2Cart = findViewById(R.id.btnCourseDetailAdd2Cart);
        btnCourseDetailEnrolNow = findViewById(R.id.btnCourseDetailEnrolNow);
        btnCourseDetailBack2Home = findViewById(R.id.btnCourseDetailBack2Home);
        llCourseDetailBottomButtonGroup = findViewById(R.id.llCourseDetailBottomButtonGroup);

        Bundle args = new Bundle();
        args.putString("id", courseId);

        btnCourseDetailCart.setOnClickListener(v -> {
            dialog = new BottomSheetDialog(CourseDetailActivity.this);
            View dialogView = LayoutInflater.from(CourseDetailActivity.this).inflate(R.layout.bsl_cart, null);
            dialog.setContentView(dialogView);
            dialog.show();

            rvCart = dialogView.findViewById(R.id.rvCart);
            rvCart.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            adapter = new RvCartAdapter(List.of());
            adapter.setOnCartItemDeleteListener(new RvCartAdapter.OnCartItemDeleteListener() {
                @Override
                public void onItemDeleted() {
                    getCart(apiService, userid);
                }
                @Override
                public void onRefreshActivity() {
                    recreate();
                }
            });
            rvCart.setAdapter(adapter);

            tvCartEmpty = dialogView.findViewById(R.id.tvCartEmpty);
            Button btnCartEnrollNow = dialogView.findViewById(R.id.btnCartEnrollNow);

            getCart(apiService, userid);

            btnCartEnrollNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CourseDetailActivity.this, EnrollActivity.class));
                }
            });
        });
        btnCourseDetailBack2Home.setOnClickListener(v -> {
            startActivity(new Intent(CourseDetailActivity.this, MainActivity.class));
        });

        btnCourseDetailAdd2Cart.setOnClickListener(v -> {
            postAdd2Cart(apiService, userid, courseId);
            recreate();
        });

        btnCourseDetailEnrolNow.setOnClickListener(v -> {
            startActivity(new Intent(CourseDetailActivity.this, EnrollActivity.class));
        });


        tlCourseDetail = findViewById(R.id.tlCourseDetail);
        tlCourseDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.e("FCV", String.valueOf(tab.getPosition()));
                switch (tab.getPosition()) {
                    case 0:
                        loadFragment(new CourseDetailLessonFragment(), args);
                        break;
                    case 1:
                        loadFragment(new CourseDetailReviewFragment(), args);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        tlCourseDetail.getTabAt(0).select();
        loadFragment(new CourseDetailLessonFragment(), args);

        getCourse(apiService, userid, courseId);

    }

    public void loadFragment(Fragment fragment, Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fcvCourseDetail, fragment);
        fragment.setArguments(args);
        transaction.commit();
    }

    public void getCourse(ApiService apiService, String userid, String courseId){
        List<Course> courseData = null;
        apiService.getCourse(userid, courseId).enqueue(new Callback<ApiResponse<List<Course>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Course>>> call, Response<ApiResponse<List<Course>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Course>> apiResponse = response.body();
                    if (apiResponse != null) {
                        List<Course> courseData = apiResponse.getData();

                        //Toast.makeText(CourseDetailActivity.this, "has enrolled: " + apiResponse.getHasEnrolled(), Toast.LENGTH_SHORT).show();
                        if (apiResponse.getHasEnrolled() == 1) {
                            llCourseDetailBottomButtonGroup.setVisibility(View.GONE);
                        } else {
                            llCourseDetailBottomButtonGroup.setVisibility(View.VISIBLE);
                        }

                        if (apiResponse.getIsInCart() == 1) {
                            btnCourseDetailAdd2Cart.setText("In Cart");
                            btnCourseDetailAdd2Cart.setEnabled(false);
                            btnCourseDetailEnrolNow.setVisibility(View.GONE);
                        } else {
                            btnCourseDetailAdd2Cart.setText("Add to Cart");
                            btnCourseDetailAdd2Cart.setEnabled(true);
                            btnCourseDetailEnrolNow.setVisibility(View.VISIBLE);
                        }

                        Glide.with(CourseDetailActivity.this)
                                .load(courseData.getFirst().getThumbnail())
                                .centerCrop()
                                .placeholder(R.drawable.death_valley_dunes)
                                .into(ivCourseThumbnail);
                        tvCourseTitle.setText(courseData.getFirst().getTitle());
                        if(courseData.getFirst().getPrice().doubleValue() == 0){
                            tvCoursePrice.setText("Free");
                        }else {
                            tvCoursePrice.setText("$" + courseData.getFirst().getPrice());
                        }
                        tvCourseDescription.setText(courseData.getFirst().getDescription());
                    }else {
                        Log.e("API_ERROR", "error status: " + apiResponse.getStatus());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Course>>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }

    public void getCart(ApiService apiService, String userid){
        List<Cart> cartList = null;
        apiService.getCart(userid).enqueue(new Callback<ApiResponse<List<Cart> >>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Cart> >> call, Response<ApiResponse<List<Cart> >> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Cart> > apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        List<Cart> cartList = apiResponse.getData();
                        if (cartList != null){
                            adapter.updateCartList(cartList);
                            tvCartEmpty.setVisibility(View.INVISIBLE);
                        }else{
                            adapter = new RvCartAdapter(List.of());
                            rvCart.setAdapter(adapter);
                            tvCartEmpty.setVisibility(View.VISIBLE);
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

    public void postAdd2Cart(ApiService apiService, String userid, String courseId) {
        apiService.postAdd2Cart(userid, courseId).enqueue(new Callback<ApiResponse<Cart>>() {
            @Override
            public void onResponse(Call<ApiResponse<Cart>> call, Response<ApiResponse<Cart>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Cart> apiResponse = response.body();
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
            public void onFailure(Call<ApiResponse<Cart>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }
}