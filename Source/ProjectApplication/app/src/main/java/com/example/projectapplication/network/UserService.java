package com.example.projectapplication.network;

import com.example.projectapplication.model.AddStopPointRequest;
import com.example.projectapplication.model.AddStopPointResponse;
import com.example.projectapplication.model.CreateTourRequest;
import com.example.projectapplication.model.CreateTourResponse;
import com.example.projectapplication.model.FbLoginRequest;
import com.example.projectapplication.model.FbLoginResponse;
import com.example.projectapplication.model.ListStopSearch;
import com.example.projectapplication.model.ListToursResponse;
import com.example.projectapplication.model.LoadListStopPointRequest;
import com.example.projectapplication.model.LoadListStopPointResponse;
import com.example.projectapplication.model.LoginRequest;
import com.example.projectapplication.model.LoginResponse;
import com.example.projectapplication.model.OTPRequest;
import com.example.projectapplication.model.OTPResponse;
import com.example.projectapplication.model.RegisterRequest;
import com.example.projectapplication.model.RegisterResponse;
import com.example.projectapplication.model.ReviewResponse;
import com.example.projectapplication.model.ReviewsRequest;
import com.example.projectapplication.model.StopPoint;
import com.example.projectapplication.model.GetHistoryByStatusResponse;
import com.example.projectapplication.model.TourInforResponse;
import com.example.projectapplication.model.UpdateInforRequest;
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
    Call<JSONObject> updateInfor(@Header("Authorization") String token, @Body UpdateInforRequest request);

    @POST("user/update-password")
    Call<JSONObject> updatePassword(@Header("Authorization") String token, @Body UpdatePasswordRequest request);

    @POST("/user/request-otp-recovery")
    Call<OTPResponse> sendOTP (@Body OTPRequest request);

    @POST("/user/verify-otp-recovery")
    Call<JSONObject> verifyOTP(@Body VerifyOTPRequest request);

    @POST("/tour/set-stop-points")
    Call<AddStopPointResponse> addStopPoint(@Body AddStopPointRequest request, @Header("Authorization") String token);

    @GET("/tour/search/service")
    Call<ListStopSearch> loadSearchStopPoint(@Query("searchKey") String searchKey, @Query("provinceId") int provinceId, @Query("provinceName") String provinceName, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Header("Authorization") String Authorization);

    @GET("/tour/get/feedback-service")
    Call<ReviewResponse> loadReview(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("serviceId") int id, @Header("Authorization") String token);

    @POST("/tour/add/feedback-service")
    Call<JSONObject> addReview(@Body ReviewsRequest request, @Header("Authorization") String token);

    @GET("/tour/history-user")
    Call<ListToursResponse> loadListToursOfUser(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Header("Authorization") String token);

    @GET("/tour/history-user-by-status")
    Call<GetHistoryByStatusResponse> getHistoryUserByStatus(@Header("Authorization") String token);

    @GET("/tour/info")
    Call<TourInforResponse> getTourDetail(@Query("tourId") int tourId, @Header("Authorization") String token);
}