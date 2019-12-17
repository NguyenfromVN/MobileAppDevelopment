package com.example.travelapp.network;

import com.example.travelapp.model.AddStopPointRequest;
import com.example.travelapp.model.AddStopPointResponse;
import com.example.travelapp.model.CreateTourRequest;
import com.example.travelapp.model.CreateTourResponse;
import com.example.travelapp.model.FbLoginRequest;
import com.example.travelapp.model.FbLoginResponse;
import com.example.travelapp.model.ListStopSearch;
import com.example.travelapp.model.ListToursResponse;
import com.example.travelapp.model.LoadListStopPointRequest;
import com.example.travelapp.model.LoadListStopPointResponse;
import com.example.travelapp.model.LoginRequest;
import com.example.travelapp.model.LoginResponse;
import com.example.travelapp.model.OTPRequest;
import com.example.travelapp.model.OTPResponse;
import com.example.travelapp.model.RegisterRequest;
import com.example.travelapp.model.RegisterResponse;
import com.example.travelapp.model.ReviewResponse;
import com.example.travelapp.model.ReviewsRequest;
import com.example.travelapp.model.GetHistoryByStatusResponse;
import com.example.travelapp.model.TourInforResponse;
import com.example.travelapp.model.UpdateInforRequest;
import com.example.travelapp.model.UpdatePasswordRequest;
import com.example.travelapp.model.UserInforResponse;
import com.example.travelapp.model.VerifyOTPRequest;

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