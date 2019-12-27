package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("comment")
    String comment;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
