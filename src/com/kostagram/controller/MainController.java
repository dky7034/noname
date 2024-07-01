package com.kostagram.controller;

import com.kostagram.model.*;
import com.kostagram.view.MainView;
import com.kostagram.view.MyPageView;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainController {
    private MainView mainView;
    private Users userInfo;

    // DAO 생성
    PostDao postDao = PostDao.getInstance();
    MyPageDao myPageDao = MyPageDao.getInstance();

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
    }

    class UserBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MyPageView myPageView = new MyPageView(userInfo);
            MyPageController myPageController = new MyPageController(myPageView,myPageDao);
            myPageView.setVisible(true);
        }
    }
}
