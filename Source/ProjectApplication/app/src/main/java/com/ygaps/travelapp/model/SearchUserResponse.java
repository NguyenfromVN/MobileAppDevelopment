package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUserResponse {
    @SerializedName("total")
    int total;
    @SerializedName("users")
    List<UserInforResponse> users;

    public int getTotal() {
        return total;
    }

    public List<UserInforResponse> getUsers() {
        return users;
    }
}
