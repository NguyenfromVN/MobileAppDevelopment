package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadSpeedLimitNotificationResponse {

    @SerializedName("notiList")
    private List<SpeedLimitNotification> notiList;

    public List<SpeedLimitNotification> getNotiList() {
        return notiList;
    }
}
