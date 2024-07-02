package com.kostagram.controller;

import com.kostagram.model.MyPageDao;
import com.kostagram.model.Users;
import com.kostagram.view.MyPageView;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

public class MyPageController {
    private MyPageView myPageView;
    private MyPageDao myPageDao;
    private Users users;

    public MyPageController(MyPageView myPageView, MyPageDao myPageDao, Users users) {
        this.myPageView = myPageView;
        this.myPageDao = myPageDao;
        this.users = users;

        this.myPageView.addHomeButtonListener(e -> goToHome());
        this.myPageView.addSearchBtnListener(e -> goToSearch());
        this.myPageView.addAddButtonListener(e -> goToAddPost());
        this.myPageView.addUserButtonListener(e -> goToUserPage());
    }

    private void goToHome() {
        myPageView.dispose();
    }

    private void goToAddPost() {
        PostView postView = new PostView(users); // 사용자 정보를 전달
        PostController postController = new PostController(postView, users);
        myPageView.dispose();
        postView.setVisible(true);
    }

    private void goToSearch() {
        SearchView searchView = new SearchView(users); // 사용자 정보를 전달
        SearchController searchController = new SearchController(searchView);
        myPageView.dispose();
        searchView.setVisible(true);
    }

    private void goToUserPage() {
        myPageView.setVisible(true);
    }
}