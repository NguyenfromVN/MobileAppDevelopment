package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadNotificationsResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("tours")
    private List<NotificationItem> tours;

    public int getTotal() {
        return total;
    }

    public List<NotificationItem> getTours() {
        return tours;
    }
}
