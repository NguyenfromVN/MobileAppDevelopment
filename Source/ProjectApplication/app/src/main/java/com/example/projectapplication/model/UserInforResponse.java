package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserInforResponse {
    @SerializedName("id")
    int id;
    @SerializedName("fullName")
    String full_name;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    String phone;
    @SerializedName("address")
    String address;
    @SerializedName("dob")
    String dob;
    @SerializedName("gender")
    int gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }


}
