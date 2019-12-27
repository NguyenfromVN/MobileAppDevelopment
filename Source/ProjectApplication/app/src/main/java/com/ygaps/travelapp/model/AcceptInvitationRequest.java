package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class AcceptInvitationRequest {

    @SerializedName("tourId")
    private  String tourId;
    @SerializedName("isAccepted")
    private  Boolean isAccepted;

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}
