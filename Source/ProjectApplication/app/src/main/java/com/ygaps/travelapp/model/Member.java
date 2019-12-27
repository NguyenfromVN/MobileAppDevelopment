package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Member {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("phone")
    String phone;
    @SerializedName("isHost")
    boolean isHost;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isHost() {
        return isHost;
    }
}
