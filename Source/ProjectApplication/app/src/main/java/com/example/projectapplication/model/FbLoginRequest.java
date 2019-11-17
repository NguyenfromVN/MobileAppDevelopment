package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class FbLoginRequest {
    @SerializedName("accessToken")
    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
