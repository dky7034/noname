package com.kostagram.controller;

import com.kostagram.model.SearchDao;
import com.kostagram.model.Users;
import com.kostagram.view.PostView;
import com.kostagram.view.SearchView;

// SearchController 클래스: 검색 뷰와 모델을 제어
public class SearchController {
    private SearchView searchView; // 검색 뷰 인스턴스
    private SearchDao searchDao; // 검색 DAO(Data Access Object) 인스턴스

    // 생성자: 검색 뷰를 매개변수로 받아 초기화
    public SearchController(SearchView searchView) {
        this.searchView = searchView;
        this.searchDao = SearchDao.getInstance(); // 여기서 SearchDao 인스턴스 초기화

        // 검색 뷰의 버튼에 리스너 추가
        this.searchView.addHomeButtonListener(e -> goToHome());
        this.searchView.addSearchButtonListener(e -> goToSearch());
        this.searchView.addAddButtonListener(e -> goToAddPost());
        this.searchView.addUserButtonListener(e -> goToUserPage());
    }

    // 홈으로 이동하는 메서드: 현재 검색 뷰를 닫음
    private void goToHome() {
        searchView.dispose();
    }

    // 게시물 작성 페이지로 이동하는 메서드
    private void goToAddPost() {
        Users userInfo = new Users(); // 새로운 사용자 정보 객체 생성
        PostView postView = new PostView(userInfo); // 게시물 뷰 인스턴스 생성
        PostController postController = new PostController(); // 게시물 컨트롤러 인스턴스 생성
        searchView.dispose(); // 현재 검색 뷰 닫기
        postView.setVisible(true); // 게시물 뷰 표시
    }

    // 검색 페이지로 이동하는 메서드: 현재 뷰를 다시 보이도록 설정
    private void goToSearch() {
        searchView.setVisible(true);
    }

    // 사용자 페이지로 이동하는 메서드 (구현 필요)
    private void goToUserPage() {
        // 여기에 User Page로 이동하는 로직을 추가하세요
    }
}
