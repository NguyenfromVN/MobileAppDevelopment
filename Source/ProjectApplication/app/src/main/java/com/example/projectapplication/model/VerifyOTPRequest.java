package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPRequest {
    @SerializedName("userId")
    int userId;
    @SerializedName("newPassword")
    String newPass;
    @SerializedName("verifyCode")
    String verifyCode;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
