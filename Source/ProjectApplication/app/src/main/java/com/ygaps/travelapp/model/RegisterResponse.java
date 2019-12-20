package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("fullName")
    private  String fullName;

    @SerializedName("createdOn")
    private  long createdOn;

    @SerializedName("dob")
    private  String dob;

    @SerializedName("gender")
    private  int gender;

    @SerializedName("password")
    private  String password;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private  String phone;

    @SerializedName("address")
    private  String address;

    @SerializedName("imgUrl")
    private  String imgUrl;

    @SerializedName("id")
    private  String id;

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
