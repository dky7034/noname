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
    // 메인 화면 뷰 객체
    private MainView mainView;
    // 사용자 정보 객체
    private Users userInfo;

    // DAO 생성
    PostDao postDao = PostDao.getInstance();

    // MainController 생성자
    public MainController(MainView mainView, Users users) {
        this.mainView = mainView;
        this.userInfo = users;

        // 포스트 로드
        loadPosts();

        // 리스너 추가
        this.mainView.addAddPostListener(new AddPostListener());
        this.mainView.addLogoutListener(new LogoutListener());
    }

    // 포스트 로드 메서드
    private void loadPosts() {
        // 모든 포스트를 가져옴
        List<Posts> posts = postDao.getPosts();
        // 포스트 내용을 문자열로 변환
        String postsText = posts.stream()
                .map(post -> post.getUserId() + ": " + post.getContent())
                .collect(Collectors.joining("\n"));
        // 메인 뷰에 포스트 설정
        mainView.setPosts(postsText);
    }

    // 포스트 추가 리스너 클래스
    class AddPostListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 새로운 포스트 뷰 생성
            PostView postView = new PostView();
            // 포스트 컨트롤러 생성
            PostController postController = new PostController(postView, userInfo, postDao);
            // 포스트 뷰를 화면에 표시
            postView.setVisible(true);
        }
    }

    // 로그아웃 리스너 클래스
    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 새로운 로그인 뷰 생성
            LoginView loginView = new LoginView();
            // 로그인 컨트롤러 생성
            LoginController loginController = new LoginController(loginView, UserDao.getInstance());
            // 메인 뷰 닫기
            mainView.dispose();
            // 로그인 뷰를 화면에 표시
            loginView.setVisible(true);
        }
    }
}
