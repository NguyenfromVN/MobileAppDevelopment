package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class InvitedRequest {
    @SerializedName("tourId")
    int tourId;
    @SerializedName("invitedUserId")
    int invitedUserId;
    @SerializedName("isInvited")
    boolean isInvited;

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public void setInvitedUserId(int invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }
}
