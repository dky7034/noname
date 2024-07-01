package com.kostagram.model;

import java.sql.Timestamp;

public class Comments {
    private int id;
    private String commentContent;
    private String userId;
    private String postId;
    private Timestamp createDate;

    public Comments(int id, String commentContent, String userId, String postId, Timestamp createDate) {
        this.id = id;
        this.commentContent = commentContent;
        this.userId = userId;
        this.postId = postId;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }
}
