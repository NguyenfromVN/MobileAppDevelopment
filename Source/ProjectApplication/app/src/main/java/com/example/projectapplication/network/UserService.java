package com.example.projectapplication.network;

import com.example.projectapplication.model.CreateTourRequest;
import com.example.projectapplication.model.CreateTourResponse;
import com.example.projectapplication.model.FbLoginRequest;
import com.example.projectapplication.model.FbLoginResponse;
import com.example.projectapplication.model.ListToursResponse;
import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.model.RegisterRequest;
import com.example.projectapplication.model.RegisterResponse;
import com.example.projectapplication.view.CreateTour;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/user/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @GET("/tour/list")
    Call<ListToursResponse> listTours(@Query("rowPerPage") int per_page, @Query("pageNum") int page, @Header("Authorization") String token);

    @POST("/tour/create")
    Call<CreateTourResponse> createTour(@Body CreateTourRequest request, @Header("Authorization") String token);

    @POST("/user/login/by-facebook")
    Call<FbLoginResponse> fbLogin(@Body FbLoginRequest request);
}