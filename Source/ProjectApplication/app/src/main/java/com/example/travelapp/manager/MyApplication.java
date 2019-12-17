package com.example.travelapp.manager;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.travelapp.R;

public class MyApplication extends Application {
    private String token;


    public void setToken(String token) {
        this.token=token;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.saved_token), token);
        editor.commit();
    }

    public String loadToken(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token=sharedPref.getString(getString(R.string.saved_token),null);
        return token;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        loadToken();
    }

}


