package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("review")
    String review;
    @SerializedName("point")
    int point;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }

    public int getPoint() {
        return point;
    }
}
