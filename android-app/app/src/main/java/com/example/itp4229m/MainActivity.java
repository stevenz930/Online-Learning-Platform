package com.example.itp4229m;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.itp4229m.fragment.AccountFragment;
import com.example.itp4229m.fragment.CoursesFragment;
import com.example.itp4229m.fragment.HomeFragment;
import com.example.itp4229m.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity{
    LinearLayout btnExplore, btnSearch, btnCourses, btnAccount;
    LinearLayout[] btns;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        btnExplore = findViewById(R.id.btnExplore);
        btnSearch = findViewById(R.id.btnSearch);
        btnCourses = findViewById(R.id.btnCourses);
        btnAccount = findViewById(R.id.btnAccount);
        btns = new LinearLayout[]{btnExplore, btnSearch, btnCourses, btnAccount};

        for (LinearLayout btn : btns){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( btn == btnExplore ){
                        loadFragment(new HomeFragment());
                    }else if( btn == btnSearch ){
                        loadFragment(new SearchFragment());
                    }else if( btn == btnCourses ){
                        loadFragment(new CoursesFragment());
                    }else if( btn == btnAccount ){
                        loadFragment(new AccountFragment());
                    }
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (!isLoggedIn){
            Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }else {
            loadFragment(new HomeFragment());
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    public LinearLayout getNavBarBtn(Fragment fragment){
        if(fragment instanceof HomeFragment){
            return btnExplore;
        }else if (fragment instanceof SearchFragment) {
            return btnSearch;
        }else if (fragment instanceof CoursesFragment) {
            return btnCourses;
        }else if (fragment instanceof AccountFragment) {
            return btnAccount;
        }
        return null;
    }

    public void changeNavbarBtnColor(Fragment fragment){
        // change navbar button color of current button
        CardView navbarBtnCard = (CardView) getNavBarBtn(fragment).getChildAt(0);//cardview of navbar button
        LinearLayout navbarBtn = (LinearLayout) navbarBtnCard.getChildAt(0);//linearlayout of cardview of navbar button

        ImageView navbarBtnImage = (ImageView) navbarBtn.getChildAt(0);//imageview of cardview of navbar button
        navbarBtnImage.setColorFilter(
                ContextCompat.getColor(fragment.getContext(), R.color.btn_selected),
                PorterDuff.Mode.SRC_IN);

        //reset navbar button color of other buttons
        for(LinearLayout btnDirect : btns){
            if(btnDirect != getNavBarBtn(fragment)){
                navbarBtnCard = (CardView) btnDirect.getChildAt(0);
                navbarBtn = (LinearLayout) navbarBtnCard.getChildAt(0);

                navbarBtnImage = (ImageView) navbarBtn.getChildAt(0);
                navbarBtnImage.setColorFilter(
                        ContextCompat.getColor(fragment.getContext(), R.color.btn_unselected),
                        PorterDuff.Mode.SRC_IN);
            }
        }
    }
}