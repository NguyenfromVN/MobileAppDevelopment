package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class OTPResponse {
    @SerializedName("userId")
    int userId;
    @SerializedName("type")
    String type;
    @SerializedName("expiredOn")
    long expiredOn;

    public int getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public long getExpiredOn() {
        return expiredOn;
    }
}
