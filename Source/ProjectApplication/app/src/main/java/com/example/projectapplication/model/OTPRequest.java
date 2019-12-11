package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class OTPRequest {
    @SerializedName("type")
    private  String type;
    @SerializedName("value")
    private String value;

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
