package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class CreateTourResponse {
    @SerializedName("hostId")
    private String hostId;
    @SerializedName("status")
    private int status;
    @SerializedName("name")
    private String name;
    @SerializedName("minCost")
    private int minCost;
    @SerializedName("maxCost")
    private int maxCost;
    @SerializedName("startDate")
    private long startDate;
    @SerializedName("endDate")
    private long endDate;
    @SerializedName("adults")
    private int adults;
    @SerializedName("childs")
    private int childs;
    @SerializedName("isPrivate")
    private boolean isPrivate;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("createdOn")
    private long createdOn;
    @SerializedName("id")
    private String id;

    public String getHostId() {
        return hostId;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getMinCost() {
        return minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChilds() {
        return childs;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public String getId() {
        return id;
    }
}
