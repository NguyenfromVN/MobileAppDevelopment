package com.example.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class UpdatePasswordRequest {
    @SerializedName("userId")
    int userId;
    @SerializedName("currentPassword")
    String currentPassword;
    @SerializedName("newPassword")
    String newPassword;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
