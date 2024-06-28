package com.kostagram.controller;

import com.kostagram.model.PostDao;
import com.kostagram.model.SearchDao;
import com.kostagram.model.UserDao;
import com.kostagram.model.Users;
import com.kostagram.view.MainView;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

import java.awt.image.BufferedImage;
import java.util.List;

public class SearchController {
    private SearchView searchView;
    private Users userInfo;
    private PostDao postDao;
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
        MainView mainView = new MainView();
        MainController mainController = new MainController(mainView, userInfo);
        searchView.dispose();
        mainView.setVisible(true);
    }

    private void goToAddPost() {
        PostView postView = new PostView(userInfo, postDao);
        PostController postController = new PostController(postView, userInfo, postDao);
        searchView.dispose();
        postView.setVisible(true);
    }

    private void goToSearch() {
        String keyword = searchView.getSearchText();
        List<BufferedImage> searchResults = searchDao.searchImages(keyword);
        searchView.displayImages(searchResults);
    }

    private void goToUserPage() {
        // 여기에 User Page로 이동하는 로직을 추가하세요
    }
}
