package com.ygaps.travelapp.network;

import com.ygaps.travelapp.model.AcceptInvitationRequest;
import com.ygaps.travelapp.model.AddStopPointRequest;
import com.ygaps.travelapp.model.AddStopPointResponse;
import com.ygaps.travelapp.model.CreateTourRequest;
import com.ygaps.travelapp.model.CreateTourResponse;
import com.ygaps.travelapp.model.FbLoginRequest;
import com.ygaps.travelapp.model.FbLoginResponse;
import com.ygaps.travelapp.model.ListStopSearch;
import com.ygaps.travelapp.model.ListToursResponse;
import com.ygaps.travelapp.model.LoadListStopPointRequest;
import com.ygaps.travelapp.model.LoadListStopPointResponse;
import com.ygaps.travelapp.model.LoadNotificationsResponse;
import com.ygaps.travelapp.model.LoginRequest;
import com.ygaps.travelapp.model.LoginResponse;
import com.ygaps.travelapp.model.OTPRequest;
import com.ygaps.travelapp.model.OTPResponse;
import com.ygaps.travelapp.model.RegisterFirebaseRequest;
import com.ygaps.travelapp.model.RegisterRequest;
import com.ygaps.travelapp.model.RegisterResponse;
import com.ygaps.travelapp.model.ReviewResponse;
import com.ygaps.travelapp.model.ReviewTourResponse;
import com.ygaps.travelapp.model.ReviewsRequest;
import com.ygaps.travelapp.model.GetHistoryByStatusResponse;
import com.ygaps.travelapp.model.SendReviewTour;
import com.ygaps.travelapp.model.TourInforResponse;
import com.ygaps.travelapp.model.UpdateInforRequest;
import com.ygaps.travelapp.model.UpdatePasswordRequest;
import com.ygaps.travelapp.model.UserInforResponse;
import com.ygaps.travelapp.model.VerifyOTPRequest;

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

    //@Query("provinceId") int provinceId, @Query("provinceName") String provinceName,
    @GET("/tour/search/service")

    Call<ListStopSearch> loadSearchStopPoint(@Query("searchKey") String searchKey,  @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Header("Authorization") String Authorization);

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

    @POST("/tour/update-tour")
    Call<JSONObject> updateTour(@Body CreateTourRequest request, @Header("Authorization") String token);

    @GET("/tour/remove-stop-point")
    Call<JSONObject>removeStopPoint(@Query("stopPointId") int id, @Header("Authorization") String token);

    @POST("/user/notification/put-token")
    Call<JSONObject> registerFirebase(@Body RegisterFirebaseRequest request, @Header("Authorization") String token);

    @GET("/tour/get/review-list")
    Call<ReviewTourResponse>loadReviewTour(@Header("Authorization") String token, @Query("tourId") int tourId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("/tour/add/review")
    Call<JSONObject>sendReviewTour(@Header("Authorization") String token, @Body SendReviewTour request);

    @GET("/tour/get/invitation")
    Call<LoadNotificationsResponse>loadNotifications(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Header("Authorization") String token);

    @POST("/tour/response/invitation")
    Call<JSONObject>confirmInvitation(@Header("Authorization") String token, @Body AcceptInvitationRequest request);
}