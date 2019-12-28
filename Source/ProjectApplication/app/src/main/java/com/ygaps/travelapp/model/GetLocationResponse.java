package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class GetLocationResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double Long;

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLong() {
        return Long;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }
}
