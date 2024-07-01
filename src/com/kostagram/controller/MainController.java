package com.kostagram.controller;

import com.kostagram.model.*;
import com.kostagram.view.MainView;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainController {
    private MainView mainView;
    private Users userInfo;

    // DAO 생성
    PostDao postDao = PostDao.getInstance();

    public MainController(MainView mainView, Users userInfo) {
        this.mainView = mainView;
        this.userInfo = userInfo;

        loadPosts();

        this.mainView.addHomeBtnListener(new HomeBtnListener());
        this.mainView.addSearchBtnListener(new SearchBtnListener());
        this.mainView.addAddBtnListener(new AddBtnListener());
        this.mainView.addUserBtnListener(new UserBtnListener());
    }

    private void loadPosts() {
        List<Posts> posts = postDao.getPosts();
        mainView.setPosts(posts);
    }

    class HomeBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 현재 뷰를 다시 보이도록 설정
            mainView.setVisible(true);
        }
    }

    class SearchBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SearchView searchView = new SearchView();
            SearchController searchController = new SearchController(searchView, SearchDao.getInstance());
            searchView.setVisible(true);
        }
    }

    class AddBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PostView postView = new PostView(userInfo, postDao);
            PostController postController = new PostController(postView, userInfo, postDao);
            postView.setVisible(true);
        }
    };

    class UserBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // UserView userView = new UserView();  // UserView가 구현되어 있다면 주석을 풀고 사용
            // UserController userController = new UserController(userView, userInfo, postDao);
            // userView.setVisible(true);
        }
    }
}
