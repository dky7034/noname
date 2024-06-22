package com.kostagram.controller;

import com.kostagram.model.Posts;
import com.kostagram.model.PostDao;
import com.kostagram.model.Users;
import com.kostagram.view.PostView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class PostController {
    private PostView postView;
    private Users userInfo;
    private PostDao postDao;

    public PostController(PostView postView, Users userInfo, PostDao postDao) {
        this.postView = postView;
        this.userInfo = userInfo;
        this.postDao = postDao;

        this.postView.addPostListener(new PostListener());
    }

    class PostListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String content = postView.getContent();
            Posts posts = new Posts();
            posts.setUserId(userInfo.getUserId());
            posts.setContent(content);
            posts.setCreateDate(new Date());

            try {
                postDao.addPost(posts);
                postView.dispose();
            } catch (Exception ex) {
                //postView.displayErrorMessage("Failed to add post: " + ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }
    }
}
