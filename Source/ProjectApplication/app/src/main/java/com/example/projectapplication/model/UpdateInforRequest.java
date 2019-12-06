package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class UpdateInforRequest {
    @SerializedName("fullName")
    String fullName;
    @SerializedName("gender")
    long gender;
    @SerializedName("dob")
    String dob;

    public String getFullName() {
        return fullName;
    }

    public long getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
