package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class CreateNotificationOnRoad {

    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double Long;
    @SerializedName("tourId")
    private int tourId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("notificationType")
    private int notificationType;
    @SerializedName("speed")
    private int speed;
    @SerializedName("note")
    private String note;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
