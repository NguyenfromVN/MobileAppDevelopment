package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class TextNotification {

    @SerializedName("userId")
    private int userId;
    @SerializedName("name")
    private String name;
    @SerializedName("notification")
    private String notification;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getNotification() {
        return notification;
    }
}
