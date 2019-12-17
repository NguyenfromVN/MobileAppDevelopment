package com.example.travelapp.model;

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
    private double sourceLat=0;
    @SerializedName("sourceLong")
    private double sourceLong=0;
    @SerializedName("desLat")
    private double desLat=0;
    @SerializedName("desLong")
    private double desLong=0;

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

    public void setSourceLat(double sourceLat) { this.sourceLat = sourceLat; }

    public void setSourceLong(double sourceLong) { this.sourceLong = sourceLong; }

    public void setDesLat(double desLat) { this.desLat = desLat; }

    public void setDesLong(double desLong) { this.desLong = desLong; }
}
