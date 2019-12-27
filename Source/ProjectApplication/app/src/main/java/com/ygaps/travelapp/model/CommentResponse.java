package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Comment;

import java.util.List;

public class CommentResponse {
    @SerializedName("commentList")
    List<Comments> commentList;

    public List<Comments> getCommentList() {
        return commentList;
    }
}
