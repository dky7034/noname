package com.kostagram.model;


import java.util.Date;

public class Comments {
    private String id;
    private String commentContent;
    private String userId;
    private String postId;
    private Date createDate;

    public Comments(String id, String commentContent, String userId, String postId, Date createDate) {
        this.id = id;
        this.commentContent = commentContent;
        this.userId = userId;
        this.postId = postId;
        this.createDate = createDate;
    }

    public Comments(String commentContent) {
        this.commentContent = commentContent;
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
}

