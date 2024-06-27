package com.kostagram.model;

import javax.swing.ImageIcon;
import java.util.Date;

public class Posts {
    private String postId;
    private String userId;
    private String content;
    private String imagePath;
    private Date createDate;
    private ImageIcon imageIcon;

    // Getters and Setters for all fields
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ImageIcon getImageIcon() {
        if (imagePath != null) {
            imageIcon = new ImageIcon(imagePath);
        }
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
}
