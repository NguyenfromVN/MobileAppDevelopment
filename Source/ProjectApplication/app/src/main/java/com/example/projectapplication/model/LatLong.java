package com.example.projectapplication.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class LatLong {
    @SerializedName("lat")
    private double Lat;
    @SerializedName("long")
    private double Long;

    public LatLong(LatLng x){
        Lat=x.latitude;
        Long=x.longitude;
    }

    public void setLat(double lat) {
        Lat = lat;
    }
    public void setLong(double aLong) {
        Long = aLong;
    }
}
