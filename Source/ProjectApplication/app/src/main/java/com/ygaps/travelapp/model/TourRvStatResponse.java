package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TourRvStatResponse {
    @SerializedName("pointStats")
    List<PointStat> list;

    public List<PointStat> getList() {
        return list;
    }
}
