package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class SpeedLimitNotification {

    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double Long;
    @SerializedName("speed")
    private int speed;

    public double getLat() {
        return lat;
    }

    public double getLong() {
        return Long;
    }

    public int getSpeed() {
        return speed;
    }
}
