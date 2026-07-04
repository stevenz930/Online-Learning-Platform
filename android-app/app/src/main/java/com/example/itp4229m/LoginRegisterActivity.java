package com.example.itp4229m;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.itp4229m.fragment.LoginFragment;
import com.example.itp4229m.fragment.LoginRegisterFragment;
import com.example.itp4229m.fragment.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (savedInstanceState == null) {
            loadFragment(new LoginRegisterFragment());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if(isLoggedIn){
            Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container_loginRegister, fragment);
        if (!(fragment instanceof LoginRegisterFragment)) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}