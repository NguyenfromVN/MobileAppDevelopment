package com.example.projectapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("feedbackList")
    private List<FeedBack> feedBackList;

    public int getTotal() {
        return total;
    }

    public List<FeedBack> getFeedBackList() {
        return feedBackList;
    }
}
