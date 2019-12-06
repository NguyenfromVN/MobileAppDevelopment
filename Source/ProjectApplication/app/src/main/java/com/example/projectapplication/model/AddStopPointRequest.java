package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddStopPointRequest {
    @SerializedName("tourId")
    String tourId;

    @SerializedName("stopPoints")
    List<StopPointForTour> stopPoints;

    public void setTourId(String tourId) { this.tourId = tourId; }

    public void setStopPoints(List<StopPointForTour> stopPoints) { this.stopPoints = stopPoints; }
}
