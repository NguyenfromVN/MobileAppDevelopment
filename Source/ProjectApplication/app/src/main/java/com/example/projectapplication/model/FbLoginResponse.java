package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class FbLoginResponse {
    @SerializedName("avatar")
    String avatar;
    @SerializedName("fullName")
    String fullName;
    @SerializedName("token")
    String token;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
