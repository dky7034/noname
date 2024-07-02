package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyPageDao {
    private static MyPageDao instance = new MyPageDao();

    private MyPageDao() {}

    public static MyPageDao getInstance() {
        return instance;
    }

    // 게시물 검색 메서드(UserEmail로 검색)
    public List<Posts> searchPostsByUserId(String userId) {
        List<Posts> postsList = new ArrayList<>();
        String sql = "SELECT POST_ID, POST_CONTENT, CREATE_DATE, (SELECT SUBSTR(USERS.USER_EMAIL, 0, INSTR(USER_EMAIL, '@')-1) FROM USERS WHERE USER_ID =P.USER_ID) AS USER_NAME, LIKES_COUNT FROM POSTS P WHERE USER_ID = ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String postId = rs.getString("post_id");
                String content = rs.getString("post_content");
                Date createDate = rs.getDate("create_date");
                String userName = rs.getString("USER_NAME");
                int likesCount = rs.getInt("likes_count");
                String hashTags ="";


                Posts post = new Posts(postId, content, createDate, userId, likesCount, hashTags, userName);
                postsList.add(post);
            }
        } catch (SQLException e) {
            System.out.println("SearchDao searchPostsByUserEmail에서 예외발생 => "+e);
        }
        return postsList;
    }

    //UserEmail으로 총 게시물 갯수 구하기
    public int getTotalPostsByEmail(String email){
        int cnt = 0;
        String sql = "SELECT COUNT(p.POST_ID) AS CNT " +
                "FROM POSTS p, USERS u " +
                "WHERE p.USER_ID = u.USER_ID AND u.USER_EMAIL = ?";
        try (Connection conn = ConnectionProvider.getConnection();// 데이터베이스 연결 얻기
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // SQL 문 준비
            pstmt.setString(1, email); // SQL 문에 이메일 값 설정
            ResultSet rs = pstmt.executeQuery(); // 쿼리 실행 및 결과 집합 얻기

            if (rs.next()) { // 결과 집합이 비어 있지 않은지 확인
                cnt = rs.getInt("CNT");
            }
//
        } catch (SQLException e) {
            System.out.println("MyPageDao의 getTotalPostsByEmail에서 예외 발생: " + e); // 예외 발생 시 오류 메시지 출력
        }
        return cnt;
    }

    //UserEmail으로 총 likes 갯수 구하기
    public int getTotalLikesByEmail(String email){
        int cnt = 0;
        String sql = "SELECT SUM(p.LIKES_COUNT) AS LIKES_CNT " +
                "FROM POSTS p, USERS u " +
                "WHERE p.USER_ID = u.USER_ID AND u.USER_EMAIL = ?";
        try (Connection conn = ConnectionProvider.getConnection();// 데이터베이스 연결 얻기
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // SQL 문 준비
            pstmt.setString(1, email); // SQL 문에 이메일 값 설정
            ResultSet rs = pstmt.executeQuery(); // 쿼리 실행 및 결과 집합 얻기

            if (rs.next()) { // 결과 집합이 비어 있지 않은지 확인
                cnt = rs.getInt("LIKES_CNT");
            }
//
        } catch (SQLException e) {
            System.out.println("MyPageDao의 getTotalLikesByEmail 예외 발생: " + e); // 예외 발생 시 오류 메시지 출력
        }
        return cnt;
    }
}