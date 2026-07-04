package com.example.itp4229m.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.itp4229m.LoginRegisterActivity;
import com.example.itp4229m.R;
import com.example.itp4229m.retrofit.ApiResponse;
import com.example.itp4229m.retrofit.ApiService;
import com.example.itp4229m.retrofit.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {

    Button btnRegister;
    EditText etRegisterUsername, etRegisterEmail, etRegisterPassword, etRegisterConfirmPassword;
    TextView tvRegisterPasswordCheck;
    //boolean isPasswordMatch = false;
    String username, email;
    String password, confirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {// non UI relate, called when fragment is created
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// UI related, called after onCreate
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        btnRegister = view.findViewById(R.id.btnRegister);
        etRegisterUsername = view.findViewById(R.id.etRegisterUsername);
        etRegisterEmail = view.findViewById(R.id.etRegisterEmail);
        etRegisterPassword = view.findViewById(R.id.etRegisterPassword);
        etRegisterConfirmPassword = view.findViewById(R.id.etRegisterConfirmPassword);
        tvRegisterPasswordCheck = view.findViewById(R.id.tvRegisterPasswordCheck);


        etRegisterUsername.setError("Username is empty");
        etRegisterEmail.setError("Email is empty");
        etRegisterPassword.setError("Password is empty");
        etRegisterConfirmPassword.setError("Confirm Password is empty");

        etRegisterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etRegisterUsername.getText().isEmpty()){
                    etRegisterUsername.setError("Username is empty");
                }
            }
        });

        etRegisterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etRegisterEmail.getText().isEmpty()){
                    etRegisterEmail.setError("Email is empty");
                }
            }
        });

        etRegisterPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = etRegisterPassword.getText().toString();
                confirmPassword = etRegisterConfirmPassword.getText().toString();

                if (!password.isEmpty()){
                    if(confirmPassword.equals(password)){
                        tvRegisterPasswordCheck.setVisibility(View.INVISIBLE);
                    }else {
                        etRegisterConfirmPassword.setError("Password does not match.");
                        tvRegisterPasswordCheck.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etRegisterConfirmPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = etRegisterPassword.getText().toString();
                confirmPassword = etRegisterConfirmPassword.getText().toString();

                if (!confirmPassword.isEmpty()){
                    if(confirmPassword.equals(password)){
                        tvRegisterPasswordCheck.setVisibility(View.INVISIBLE);
                    }else {
                        etRegisterConfirmPassword.setError("Password does not match.");
                        tvRegisterPasswordCheck.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnRegister.setEnabled(true);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                username = etRegisterUsername.getText().toString();
                email = etRegisterEmail.getText().toString();
                password = etRegisterPassword.getText().toString();
                confirmPassword = etRegisterConfirmPassword.getText().toString();

                if (etRegisterUsername.getText().isEmpty()){
                    etRegisterUsername.setError("Username is empty");
                    return;
                }
                if (etRegisterEmail.getText().isEmpty()){
                    etRegisterEmail.setError("Email is empty");
                    return;
                }
                if (etRegisterPassword.getText().isEmpty()){
                    etRegisterPassword.setError("Password is empty");
                    return;
                }
                if (etRegisterConfirmPassword.getText().isEmpty()){
                    etRegisterConfirmPassword.setError("Confirm Password is empty");
                    return;
                }

                if(confirmPassword.equals(password)){
                    tvRegisterPasswordCheck.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Register Success", Toast.LENGTH_SHORT).show();
                    registerUser(apiService, username, email, password);
                    btnRegister.setEnabled(false);
                }else {
                    etRegisterConfirmPassword.setError("Password does not match.");
                    tvRegisterPasswordCheck.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    public void registerUser(ApiService apiService, String username, String email, String password){
        Call<ApiResponse<User>> call = apiService.registerUser(username, email, password);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<User> apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        ((LoginRegisterActivity) requireActivity()).loadFragment(new LoginRegisterFragment());
                    }else {
                        Log.e("API_ERROR", "error Message: " + apiResponse.getMessage());
                    }
                } else {
                    Log.e("API_ERROR", "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e("API_ERROR", "requset failed: " + t.getMessage());
            }
        });
    }
}
