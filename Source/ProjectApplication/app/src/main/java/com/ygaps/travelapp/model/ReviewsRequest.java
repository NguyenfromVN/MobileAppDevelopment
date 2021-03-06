package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class ReviewsRequest {
    @SerializedName("serviceId")
    private  int id;
    @SerializedName("feedback")
    private  String feedback;

    @SerializedName("point")
    private int point;

    public int getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getPoint() {
        return point;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
