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
            loadPosts(); // 게시물 목록을 다시 로드
            mainView.revalidate(); // 레이아웃 관리자에게 컴포넌트 유효성 재확인 요청
            mainView.repaint(); // 다시 그리기 요청
        }
    }

    class SearchBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SearchView searchView = new SearchView(userInfo);
            SearchController searchController = new SearchController(searchView);
            searchView.setVisible(true);
        }
    }

    class AddBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PostView postView = new PostView(userInfo);
            PostController postController = new PostController(postView, userInfo);
            postView.setVisible(true);
        }
    }

    // 마이페이지 이동 버튼
    class UserBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MyPageView myPageView = new MyPageView(userInfo);
            MyPageController myPageController = new MyPageController(myPageView, myPageDao, userInfo);
            myPageView.setVisible(true);
        }
    }
}
