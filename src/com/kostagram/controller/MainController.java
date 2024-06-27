package com.kostagram.controller;

import com.kostagram.model.Posts;
import com.kostagram.model.PostDao;
import com.kostagram.model.Users;
import com.kostagram.model.UserDao;
import com.kostagram.view.LoginView;
import com.kostagram.view.MainView;
import com.kostagram.view.PostView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    private MainView mainView;
    private Users userInfo;

    // DAO 생성
    UserDao userDao = UserDao.getInstance();
    PostDao postDao = PostDao.getInstance();

    public MainController(MainView mainView, Users userInfo) {
        this.mainView = mainView;
        this.userInfo = userInfo;

        loadPosts();

        this.mainView.addAddPostListener(new AddPostListener());
        this.mainView.addLogoutListener(new LogoutListener());
    }

    private void loadPosts() {
        List<Posts> posts = postDao.getPosts();
        String postsText = posts.stream()
                .map(post -> post.getUserId() + ": " + post.getContent())
                .collect(Collectors.joining("\n"));
        mainView.setPosts(postsText);
    }

    class AddPostListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PostView postView = new PostView();
            PostController postController = new PostController(postView, userInfo, postDao);
            postView.setVisible(true);
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView, UserDao.getInstance());
            mainView.dispose();
            loginView.setVisible(true);
        }
    }
}
