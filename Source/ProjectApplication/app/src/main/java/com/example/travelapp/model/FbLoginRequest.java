package com.example.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class FbLoginRequest {
    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
