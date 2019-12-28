package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class GetLocationRequest {

    @SerializedName("userId")
    private int userId;
    @SerializedName("tourId")
    private int tourId;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double Long;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }
}
