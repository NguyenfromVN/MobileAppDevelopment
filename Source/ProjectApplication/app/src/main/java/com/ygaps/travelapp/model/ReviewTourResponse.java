package com.ygaps.travelapp.model;

import android.widget.ListView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewTourResponse {
    @SerializedName("reviewList")
    List<Review> reviewList;

    public List<Review> getReviewList() {
        return reviewList;
    }
}
