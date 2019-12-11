package com.example.projectapplication.view;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapplication.R;
import com.example.projectapplication.manager.MyApplication;
import com.example.projectapplication.model.FbLoginRequest;
import com.example.projectapplication.model.FbLoginResponse;
import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.model.UserInforResponse;
import com.example.projectapplication.network.MyAPIClient;
import com.example.projectapplication.network.UserService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView forgotPass;

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
        forgotPass = (TextView)findViewById(R.id.twForgotPass);

        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setPermissions("email");

        //------Login by fb-------------
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken().toString();

                FbLoginRequest request = new FbLoginRequest();
                request.setAccessToken(token);
                Call<FbLoginResponse> call = userService.fbLogin(request);

                call.enqueue(new Callback<FbLoginResponse>() {
                    @Override
                    public void onResponse(Call<FbLoginResponse> call, Response<FbLoginResponse> response) {
                        if (response.isSuccessful()) {
                            MyAPIClient.getInstance().setAccessToken(response.body().getToken());

                            //******TOKEN nè
                            String Token = response.body().getToken();

                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPref.edit();

                            long time = (new Date()).getTime() / 1000;

                            editor.putString(getString(R.string.saved_access_token), Token);
                            editor.putLong(getString(R.string.saved_access_token_time), time);
                            editor.commit();

                            MyApplication app = (MyApplication) LoginActivity.this.getApplication();
                            app.setToken(Token);


                            Log.d(TAG, Token);




                            Intent intent = new Intent(LoginActivity.this, ListTours.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<FbLoginResponse> call, Throwable t) {
                        Log.d(TAG, t.getMessage());

                    }
                });
            }
                    @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        
        
        // Đăng ký
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reg);
            }
        });
        
        // Đăng nhập

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        
        // Quên pass
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();


        UserService userService;
        MyApplication app = (MyApplication)LoginActivity.this.getApplication();
        String token=app.loadToken();
        Log.d("Token", "loadUserInfor: "+token);
        userService = MyAPIClient.getInstance().getAdapter().create(UserService.class);

        Call<UserInforResponse> call = userService.userInfor(token);
        call.enqueue(new Callback<UserInforResponse>() {
            @Override
            public void onResponse(Call<UserInforResponse> call, Response<UserInforResponse> response) {
                if(response.isSuccessful()){
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getString(R.string.saved_id), response.body().getId());
                    editor.putString(getString(R.string.saved_name),response.body().getFull_name());
                    editor.putString(getString(R.string.saved_phone), response.body().getPhone());
                    editor.putString(getString(R.string.saved_email), response.body().getEmail());
                    editor.putString(getString(R.string.saved_dob), response.body().getDob());
                    editor.putInt(getString(R.string.saved_gender), response.body().getGender());
                    editor.putString(getString(R.string.saved_address), response.body().getAddress());
                    editor.putString(getString(R.string.saved_avt), response.body().getAvatar());
                    editor.commit();
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("UserInfor", "onResponse: "+jObjError.getString("message"));
                        //Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("UserInfor", "onResponse: "+e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInforResponse> call, Throwable t) {

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
                if(response.isSuccessful()){
                MyAPIClient.getInstance().setAccessToken(response.body().getToken());

                //******TOKEN nè
                String Token = response.body().getToken();

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                long time = (new Date()).getTime()/1000;

                editor.putString(getString(R.string.saved_access_token),Token);
                editor.putLong(getString(R.string.saved_access_token_time), time);
                editor.commit();

                MyApplication app = (MyApplication) LoginActivity.this.getApplication();
                app.setToken(Token);


                Log.d(TAG,Token);


                Intent intent = new Intent(LoginActivity.this, ListTours.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                LoginActivity.this.finish();}
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());

            }
        });
    }


}
