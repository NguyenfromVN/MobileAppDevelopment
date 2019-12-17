package com.example.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class AddStopPointResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() { return message; }
}
