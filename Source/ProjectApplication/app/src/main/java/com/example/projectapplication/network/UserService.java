package com.example.projectapplication.network;

import com.example.projectapplication.model.CreateTourRequest;
import com.example.projectapplication.model.CreateTourResponse;
import com.example.projectapplication.model.FbLoginRequest;
import com.example.projectapplication.model.FbLoginResponse;
import com.example.projectapplication.model.ListToursResponse;
import com.example.projectapplication.model.LoadListStopPointRequest;
import com.example.projectapplication.model.LoadListStopPointResponse;
import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.model.OTPRequest;
import com.example.projectapplication.model.OTPResponse;
import com.example.projectapplication.model.RegisterRequest;
import com.example.projectapplication.model.RegisterResponse;
import com.example.projectapplication.model.UpdateInfroRequest;
import com.example.projectapplication.model.UpdatePasswordRequest;
import com.example.projectapplication.model.UserInforResponse;
import com.example.projectapplication.model.VerifyOTPRequest;

import org.json.JSONObject;

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

    @POST("/tour/suggested-destination-list")
    Call<LoadListStopPointResponse> loadListStopPoint(@Body LoadListStopPointRequest request, @Header("Authorization") String token);

    @GET("/user/info")
    Call<UserInforResponse> userInfor(@Header("Authorization") String token);

    @POST("user/edit-info")
    Call<JSONObject> updateInfor(@Header("Authorization") String token, @Body UpdateInfroRequest request);

    @POST("user/update-password")
    Call<JSONObject> updatePassword(@Header("Authorization") String token, @Body UpdatePasswordRequest request);

    @POST("/user/request-otp-recovery")
    Call<OTPResponse> sendOTP (@Body OTPRequest request);

    @POST("/user/verify-otp-recovery")
    Call<JSONObject> verifyOTP(@Body VerifyOTPRequest request);
}