package com.example.itp4229m.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.itp4229m.LoginRegisterActivity;
import com.example.itp4229m.MainActivity;
import com.example.itp4229m.R;

public class LoginRegisterFragment extends Fragment {
    Button btnToLogin, btnToSignUp;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean isLoggedIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);

        prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        btnToLogin = view.findViewById(R.id.btnToLogin);
        btnToSignUp = view.findViewById(R.id.btnToSignUp);

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                ((LoginRegisterActivity) requireActivity()).loadFragment(new LoginFragment());
            }
        });
        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                ((LoginRegisterActivity) requireActivity()).loadFragment(new RegisterFragment());
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        //Log.e("isLoggedIn", "LRF "+String.valueOf(isLoggedIn));

        if(isLoggedIn){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }
}
