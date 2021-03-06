package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("userId")
    String userId;
    @SerializedName("emailVerified")
    private Boolean emailVerified;
    @SerializedName("phoneVerified")
    private  Boolean phoneVerified;
    @SerializedName("token")
    private String token;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
