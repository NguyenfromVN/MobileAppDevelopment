package com.ygaps.travelapp.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class StopPoint {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
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

    public String getName() {
        return name;
    }

    public int getId() { return id; }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLandingTimesOfUser(int landingTimesOfUser) {
        this.landingTimesOfUser = landingTimesOfUser;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
