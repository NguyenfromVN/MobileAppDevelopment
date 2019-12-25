package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class SendReviewTour {
    @SerializedName("tourId")
    int tourId;
    @SerializedName("point")
    int point;
    @SerializedName("review")
    String review;

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
