package com.example.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Tour {
    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private int status;
    @SerializedName("name")
    private String name;
    @SerializedName("minCost")
    private String minCost;
    @SerializedName("maxCost")
    private String maxCost;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("adults")
    private int adults;
    @SerializedName("childs")
    private int childs;
    @SerializedName("isPrivate")
    private Boolean isPrivate;
    @SerializedName("avatar")
    private String avatar;

    public int getId() {
        return id;
    }
    public int getStatus() {
        return status;
    }
    public String getName() {
        return name;
    }
    public String getMinCost() {
        return minCost;
    }
    public String getMaxCost() {
        return maxCost;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public int getAdults() {
        return adults;
    }
    public int getChilds() {
        return childs;
    }
    public boolean getIsPrivate() {
        return isPrivate;
    }
    public String getAvatar() {
        return avatar;
    }
}
