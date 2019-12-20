package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class RegisterFirebaseRequest {

    @SerializedName("fcmToken")
    private String fcmToken;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("platform")
    private int platform;
    @SerializedName("appVersion")
    private String appVersion;

    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public void setPlatform(int platform) { this.platform = platform; }

    public void setAppVersion(String appVersion) { this.appVersion = appVersion; }
}
