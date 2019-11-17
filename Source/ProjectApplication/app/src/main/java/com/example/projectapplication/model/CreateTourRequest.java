package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class CreateTourRequest {
    @SerializedName("name")
    private String name;
    @SerializedName("startDate")
    private long startDate;
    @SerializedName("endDate")
    private long endDate;
    @SerializedName("adults")
    private int adults;
    @SerializedName("childs")
    private int childs;
    @SerializedName("minCost")
    private int minCost;
    @SerializedName("maxCost")
    private int maxCost;
    @SerializedName("isPrivate")
    private boolean isPrivate;
    @SerializedName("sourceLat")
    private int sourceLat=0;
    @SerializedName("sourceLong")
    private int sourceLong=0;
    @SerializedName("desLat")
    private int desLat=0;
    @SerializedName("desLong")
    private int desLong=0;

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
