package com.kostagram.controller;

import com.kostagram.model.PostDao;
import com.kostagram.model.SearchDao;
import com.kostagram.model.Users;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

public class SearchController {
    private SearchView searchView;
    private SearchDao searchDao;

    public SearchController(SearchView searchView, SearchDao searchDao) {
        this.searchView = searchView;
        this.searchDao = searchDao;

        this.searchView.addHomeButtonListener(e -> goToHome());
        this.searchView.addSearchButtonListener(e -> goToSearch());
        this.searchView.addAddButtonListener(e -> goToAddPost());
        this.searchView.addUserButtonListener(e -> goToUserPage());
    }

    private void goToHome() {
        searchView.dispose();
    }

    private void goToAddPost() {
        Users userInfo = new Users();
        PostView postView = new PostView(userInfo, PostDao.getInstance());
        PostController postController = new PostController();
        searchView.dispose();
        postView.setVisible(true);
    }

    private void goToSearch() {
        // 현재 뷰를 다시 보이도록 설정
        searchView.setVisible(true);
    }

    private void goToUserPage() {
        // 여기에 User Page로 이동하는 로직을 추가하세요
    }
}
