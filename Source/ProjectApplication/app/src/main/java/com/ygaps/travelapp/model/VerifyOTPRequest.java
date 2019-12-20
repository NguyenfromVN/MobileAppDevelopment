package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPRequest {
    @SerializedName("userId")
    private int userId;
    @SerializedName("newPassword")
    private  String newPass;
    @SerializedName("verifyCode")
    private  String verifyCode;

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
