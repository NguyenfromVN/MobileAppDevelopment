package com.example.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class FeedBack {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("point")
    private String point;
    @SerializedName("feedback")
    private String feedback;
    @SerializedName("avatar")
    private String avatar;


    public FeedBack(int id, String name, String point, String feedback, String avatar) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.feedback = feedback;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
