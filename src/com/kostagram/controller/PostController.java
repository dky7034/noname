package com.kostagram.controller;

import com.kostagram.model.PostDao;
import com.kostagram.model.Posts;
import com.kostagram.model.Users;
import com.kostagram.view.PostView;
import javax.swing.*;
import java.sql.SQLException;

public class PostController {
    private PostView postView;
    private Users userInfo;
    private PostDao postDao;

    // 생성자
    public PostController() {}

    public PostController(PostView postView, Users userInfo, PostDao postDao) {
        this.postView = postView;
        this.userInfo = userInfo;
        this.postDao = postDao;
    }

    public boolean addPost(String content, String hashTag) {
        Posts post = new Posts();
        post.setUserId(userInfo.getUserId());
        post.setPostContent(content);
        post.setHashTags(hashTag);
        try {
            postDao.addPost(post);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(postView, "게시글 저장 중 오류 발생!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
