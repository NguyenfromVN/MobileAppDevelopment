package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class OTPResponse {
    @SerializedName("userId")
    private  int userId;
    @SerializedName("type")
    private  String type;
    @SerializedName("expiredOn")
    private long expiredOn;

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
