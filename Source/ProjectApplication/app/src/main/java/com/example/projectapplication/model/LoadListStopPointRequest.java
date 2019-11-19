package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadListStopPointRequest {

    @SerializedName("hasOneCoordinate")
    Boolean hasOneCoordinate;

    @SerializedName("coordList")
    List<CoordinateSet> coordList;

    public void setHasOneCoordinate(Boolean hasOneCoordinate) {
        this.hasOneCoordinate = hasOneCoordinate;
    }

    public void setCoordList(List<CoordinateSet> coordList) {
        this.coordList = coordList;
    }
}
