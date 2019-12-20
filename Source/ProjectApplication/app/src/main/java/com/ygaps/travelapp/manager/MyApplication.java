package com.ygaps.travelapp.manager;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;
import com.ygaps.travelapp.R;

public class MyApplication extends Application {

    public void setToken(String token) {
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

    public void setFcmToken(String token) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.saved_fcm_token), token);
        editor.commit();
    }

    public String loadFcmToken(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token=sharedPref.getString(getString(R.string.saved_fcm_token),null);
        return token;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
