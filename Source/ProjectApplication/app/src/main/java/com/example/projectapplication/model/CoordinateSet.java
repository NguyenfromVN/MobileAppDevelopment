package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoordinateSet {
    @SerializedName("coordinateSet")
    List<LatLong> listLatLong;

    public void setListLatLong(List<LatLong> listLatLong) {
        this.listLatLong = listLatLong;
    }
}
