package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("password")
    String password;
    @SerializedName("fullName")
    String fullName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
