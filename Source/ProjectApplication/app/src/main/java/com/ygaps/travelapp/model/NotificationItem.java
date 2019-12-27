package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class NotificationItem implements Comparable{
    @SerializedName("id")
    private int id;
    @SerializedName("hostId")
    private String hostId;
    @SerializedName("hostName")
    private String hostName;
    @SerializedName("hostPhone")
    private String hostPhone;
    @SerializedName("hostEmail")
    private String hostEmail;
    @SerializedName("hostAvatar")
    private String hostAvatar;
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
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("createdOn")
    private long createdOn;

    public int getId() {
        return id;
    }

    public String getHostId() {
        return hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public String getHostPhone() {
        return hostPhone;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public String getHostAvatar() {
        return hostAvatar;
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

    public String getAvatar() {
        return avatar;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setHostPhone(String hostPhone) {
        this.hostPhone = hostPhone;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public void setHostAvatar(String hostAvatar) {
        this.hostAvatar = hostAvatar;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int compareTo(Object x) {
        long compareDate=((NotificationItem)x).getCreatedOn();
        if (compareDate-createdOn>0)
            return 1;
        if (compareDate-createdOn==0)
            return 0;
        return -1;
    }
}
