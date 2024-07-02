package com.kostagram.model;


import java.util.Date;

public class Comments {
    private String id;
    private String commentContent;
    private String userId;
    private String postId;
    private Date createDate;
    private String userEmail;


    public Comments(String id, String commentContent, String userId, String postId, Date createDate, String userEmail) {
        this.id = id;
        this.commentContent = commentContent;
        this.userId = userId;
        this.postId = postId;
        this.createDate = createDate;
        this.userEmail = userEmail;
    }

    public Comments() {
        super();
    }

    public String getId() {
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

    public Date getCreateDate() {
        return createDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}