package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListToursResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("tours")
    private List<Tour> tours;

    public int getTotal() {
        return total;
    }
    public List<Tour> getTours() {
        return tours;
    }
}