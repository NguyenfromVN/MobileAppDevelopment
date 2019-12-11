package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class UpdateInforRequest {
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("gender")
    private int gender;
    @SerializedName("dob")
    private  String dob;

    public String getFullName() {
        return fullName;
    }

    public int getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
