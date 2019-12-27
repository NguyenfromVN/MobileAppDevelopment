package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class PointStat {
    @SerializedName("point")
    String point;
    @SerializedName("total")
    String total;

    public String getPoint() {
        return point;
    }

    public String getTotal() {
        return total;
    }
}
