package com.example.projectapplication.network;

import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/user/register")
    Call<LoginResponse> register(@Body RegisterRequest request);
}