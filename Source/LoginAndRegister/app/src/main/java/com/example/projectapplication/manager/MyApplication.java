package com.example.projectapplication.manager;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.projectapplication.R;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.view.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyApplication extends Application {
    private String token;


    public void setToken(String token) {
        this.token=token;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.saved_token), token);
        editor.commit();
    }

    public void loadToken(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPref.getString(getString(R.string.saved_token),null);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        loadToken();
    }

}


