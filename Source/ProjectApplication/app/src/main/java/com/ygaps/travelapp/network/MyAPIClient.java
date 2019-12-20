package com.ygaps.travelapp.network;

import com.ygaps.travelapp.manager.Constants;

import okhttp3.OkHttpClient;
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
