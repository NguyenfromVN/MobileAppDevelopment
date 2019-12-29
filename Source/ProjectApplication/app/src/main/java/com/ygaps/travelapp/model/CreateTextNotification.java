package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class CreateTextNotification {

    @SerializedName("tourId")
    private int tourId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("noti")
    private String noti;

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }
}
