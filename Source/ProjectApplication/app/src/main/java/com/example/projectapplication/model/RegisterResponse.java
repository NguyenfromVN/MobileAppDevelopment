package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("fullName")
    String fullName;

    @SerializedName("createdOn")
    long createdOn;

    @SerializedName("dob")
    String dob;

    @SerializedName("gender")
    int gender;

    @SerializedName("password")
    String password;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("address")
    String address;

    @SerializedName("imgUrl")
    String imgUrl;

    @SerializedName("id")
    String id;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
