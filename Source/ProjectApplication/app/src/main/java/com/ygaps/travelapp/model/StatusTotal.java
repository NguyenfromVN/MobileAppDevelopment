package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class StatusTotal {
    @SerializedName("status")
    private int status;

    @SerializedName("total")
    private int total;

    public int getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }
}
