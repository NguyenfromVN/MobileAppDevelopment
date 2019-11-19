package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class StopPoint {
    @SerializedName("id")
    private String id;
    @SerializedName("address")
    private String address;
    @SerializedName("provinceId")
    private int provinceId;
    @SerializedName("contact")
    private String contact;
    @SerializedName("lat")
    private double Lat;
    @SerializedName("long")
    private double Long;
    @SerializedName("minCost")
    private int minCost;
    @SerializedName("maxCost")
    private int maxCost;
    @SerializedName("serviceTypeId")
    private int serviceTypeId;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("landingTimesOfUser")
    private int landingTimesOfUser;

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getContact() {
        return contact;
    }

    public double getLat() {
        return Lat;
    }

    public double getLong() {
        return Long;
    }

    public int getMinCost() {
        return minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getLandingTimesOfUser() {
        return landingTimesOfUser;
    }
}
