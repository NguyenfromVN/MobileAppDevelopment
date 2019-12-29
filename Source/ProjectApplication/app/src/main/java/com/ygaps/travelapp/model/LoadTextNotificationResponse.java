package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadTextNotificationResponse {

    @SerializedName("notiList")
    private List<TextNotification> notiList;

    public List<TextNotification> getNotiList() {
        return notiList;
    }
}
