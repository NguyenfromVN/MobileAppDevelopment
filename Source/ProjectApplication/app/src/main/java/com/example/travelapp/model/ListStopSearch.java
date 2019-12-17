package com.example.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListStopSearch {
    @SerializedName("total")
    private int total;
    @SerializedName("stopPoints")
   private  List<StopPointSearch> listStop;

    public List<StopPointSearch> getListStop() {
        return listStop;
    }

    public int getTotal() {
        return total;
    }
}
