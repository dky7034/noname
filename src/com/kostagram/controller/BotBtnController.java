package com.kostagram.controller;

import com.kostagram.model.MyPageDao;
import com.kostagram.model.PostDao;
import com.kostagram.model.Posts;
import com.kostagram.model.Users;
import com.kostagram.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BotBtnController {
    private MainView mainView;
    private Users userInfo;
    private BottomPanel bottomPanel;
    // DAO 생성
    PostDao postDao = PostDao.getInstance();
    MyPageDao myPageDao = MyPageDao.getInstance();

    public BotBtnController(BottomPanel bottomPanel, Users userInfo) {
        this.bottomPanel = bottomPanel;
        this.userInfo = userInfo;

        loadPosts();

        this.bottomPanel.addHomeButtonListener(new HomeBtnListener());
        this.bottomPanel.addSearchButtonListener(new SearchBtnListener());
        this.bottomPanel.addAddButtonListener(new AddBtnListener());
        this.bottomPanel.addUserButtonListener(new UserBtnListener());

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
