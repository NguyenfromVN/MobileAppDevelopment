package com.example.projectapplication.network;

import android.text.TextUtils;

import com.example.projectapplication.manager.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAPIClient {
    private static MyAPIClient instance;

    private Retrofit adapter;

    private String accessToken;

    public static MyAPIClient getInstance() {
        if(instance == null)
            instance = new MyAPIClient();
        return instance;
    }

    public Retrofit getAdapter() {
        return adapter;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private MyAPIClient(){
        OkHttpClient client = new OkHttpClient();
        adapter = new Retrofit.Builder()
                .baseUrl(Constants.APIEndpoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
