package com.example.travelapp.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class StopPointForTour {

    @SerializedName("id")
    private int id;
    @SerializedName("serviceId")
    private int serviceId;
    @SerializedName("address")
    private String address;
    @SerializedName("name")
    private String name;
    @SerializedName("provinceId")
    private int provinceId;
    @SerializedName("lat")
    private double Lat;
    @SerializedName("long")
    private double Long;
    @SerializedName("serviceTypeId")
    private int serviceTypeId;
    @SerializedName("arrivalAt")
    private long arrivalAt;
    @SerializedName("leaveAt")
    private long leaveAt;
    @SerializedName("minCost")
    private int minCost;
    @SerializedName("maxCost")
    private int maxCost;
    @SerializedName("index")
    private int index;

    public void setName(String name) { this.name = name; }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public void setArrivalAt(long arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public void setLeaveAt(long leaveAt) {
        this.leaveAt = leaveAt;
    }

    public void setMinCost(int minCost) { this.minCost = minCost; }

    public void setMaxCost(int maxCost) { this.maxCost = maxCost; }

    public void setId(int id) { this.id = id; }

    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public void setIndex(int index) { this.index = index; }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public double getLat() {
        return Lat;
    }

    public double getLong() {
        return Long;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public long getArrivalAt() {
        return arrivalAt;
    }

    public long getLeaveAt() {
        return leaveAt;
    }

    public int getMinCost() {
        return minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public int getId() { return id; }

    public int getServiceId() { return serviceId; }

    public int getIndex() { return index; }

    @NonNull
    @Override
    public String toString() { return name; }
}
