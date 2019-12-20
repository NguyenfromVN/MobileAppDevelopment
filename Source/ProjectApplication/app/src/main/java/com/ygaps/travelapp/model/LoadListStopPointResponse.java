package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadListStopPointResponse {
    @SerializedName("stopPoints")
    private List<StopPoint> stopPoints;

    public List<StopPoint> getStopPoints() {
        return stopPoints;
    }
}
