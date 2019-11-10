package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static String TAG  = "LoginActivity";

    //UI
    private EditText emailPhone;
    private EditText password;
    private Button login, register;
    private ProgressBar ProgDialog;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        // set up
        emailPhone =(EditText) findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btnLogin);
        register = (Button)findViewById(R.id.btnReg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });



    }


    private void attemptLogin() {
        emailPhone.setError(null);
        password.setError(null);

        final String user=emailPhone.getText().toString();
        final String password1 = password.getText().toString();

        final LoginRequest request = new LoginRequest();
        request.setEmailPhone(user);
        request.setPassword(password1);

        boolean cancel = false;



        Call<LoginResponse> call = userService.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyAPIClient.getInstance().setAccessToken(response.body().getToken());

                //******TOKEN nè
                String Token = response.body().getToken();
                //****************

                Log.d(TAG,Token);


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                LoginActivity.this.finish();


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());

            }
        });
    }
}
