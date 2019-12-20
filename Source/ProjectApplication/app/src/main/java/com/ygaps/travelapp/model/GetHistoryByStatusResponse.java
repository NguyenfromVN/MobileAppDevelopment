package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetHistoryByStatusResponse {
    @SerializedName("totalToursGroupedByStatus")
    private List<StatusTotal> totalToursGroupedByStatus;

    public List<StatusTotal> getTotalToursGroupedByStatus() {
        return totalToursGroupedByStatus;
    }
}
