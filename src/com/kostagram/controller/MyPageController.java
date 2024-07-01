package com.kostagram.controller;

import com.kostagram.model.MyPageDao;
import com.kostagram.model.PostDao;
import com.kostagram.model.SearchDao;
import com.kostagram.model.Users;
import com.kostagram.view.MyPageView;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

public class MyPageController {
    private MyPageView myPageView;
    private MyPageDao myPageDao;


    public MyPageController(MyPageView myPageView, MyPageDao myPageDao) {
        this.myPageView = myPageView;
        this.myPageDao = myPageDao;

        this.myPageView.addHomeButtonListener(e -> goToHome());
        this.myPageView.addSearchBtnListener(e -> goToSearch());
        this.myPageView.addAddButtonListener(e -> goToAddPost());
        this.myPageView.addUserButtonListener(e -> goToUserPage());
    }

    private void goToHome() {
        myPageView.dispose();
    }

    private void goToAddPost() {
        Users userInfo = new Users();
        PostView postView = new PostView(userInfo, PostDao.getInstance());
        PostController postController = new PostController();
        myPageView.dispose();
        postView.setVisible(true);
    }

    private void goToSearch() {
        Users userInfo = new Users();
        // 현재 뷰를 다시 보이도록 설정
        SearchView searchView = new SearchView(userInfo);
        SearchController searchController = new SearchController(searchView, SearchDao.getInstance());
        myPageView.dispose();
        searchView.setVisible(true);
    }

    private void goToUserPage() {
        // 여기에 User Page로 이동하는 로직을 추가하세요
    }
}
