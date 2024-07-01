package com.kostagram.model;

import java.util.Date;

public class Posts {
    private String postId;
    private String postContent;
    private Date createDate;
    private String userId;
    private String hashTag;
    private String userName;
    private int likesCount;
    private int commentsCount; // 댓글 수 필드 추가

    public Posts() {}

    public Posts(String postId, String postContent, Date createDate, String userId, int likesCount, String hashTag, String userName) {
        this.postId = postId;
        this.postContent = postContent;
        this.createDate = createDate;
        this.userId = userId;
        this.likesCount = likesCount;
        this.hashTag = hashTag;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for postId
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getHashTags() {
        return hashTag;
    }

    public void setHashTags(String hashTag) {
        this.hashTag = hashTag;
    }

    // Getter and Setter for postContent
    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    // Getter and Setter for createDate
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and Setter for likesCount
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    // Getter and Setter for commentsCount
    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
