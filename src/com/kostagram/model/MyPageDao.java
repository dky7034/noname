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

    // 게시물 검색 메서드(UserId로 검색)
    public List<Posts> searchPostsByUserId(String userId) {
        List<Posts> postsList = new ArrayList<>();
        String sql = "SELECT P.POST_ID, P.POST_CONTENT, P.CREATE_DATE, " +
                "(SELECT SUBSTR(U.USER_EMAIL, 0, INSTR(U.USER_EMAIL, '@')-1) " +
                "FROM USERS U WHERE U.USER_ID = P.USER_ID) AS USER_NAME, " +
                "P.LIKES_COUNT, " +
                "LISTAGG(H.HASHTAG_CONTENT, ',') WITHIN GROUP (ORDER BY H.HASHTAG_CONTENT) AS HASH_TAGS " +
                "FROM POSTS P " +
                "LEFT JOIN POST_HASHTAG PH ON P.POST_ID = PH.POST_ID " +
                "LEFT JOIN HASHTAG H ON PH.HASHTAG_ID = H.HASHTAG_ID " +
                "WHERE P.USER_ID = ? " +
                "GROUP BY P.POST_ID, P.POST_CONTENT, P.CREATE_DATE, P.USER_ID, P.LIKES_COUNT";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String postId = rs.getString("POST_ID");
                String content = rs.getString("POST_CONTENT");
                Date createDate = rs.getDate("CREATE_DATE");
                String userName = rs.getString("USER_NAME");
                int likesCount = rs.getInt("LIKES_COUNT");
                String hashTags = rs.getString("HASH_TAGS");

                Posts post = new Posts(postId, content, createDate, userId, likesCount, hashTags, userName);
                postsList.add(post);
            }
        } catch (SQLException e) {
            System.out.println("MyPageDao의 searchPostsByUserId에서 예외 발생 => " + e);
        }
        return postsList;
    }

    // UserEmail으로 총 게시물 갯수 구하기
    public int getTotalPostsByEmail(String email) {
        int cnt = 0;
        String sql = "SELECT COUNT(P.POST_ID) AS CNT " +
                "FROM POSTS P, USERS U " +
                "WHERE P.USER_ID = U.USER_ID AND U.USER_EMAIL = ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            System.out.println("MyPageDao의 getTotalPostsByEmail에서 예외 발생: " + e);
        }
        return cnt;
    }

    // UserEmail으로 총 likes 갯수 구하기
    public int getTotalLikesByEmail(String email) {
        int cnt = 0;
        String sql = "SELECT SUM(P.LIKES_COUNT) AS LIKES_CNT " +
                "FROM POSTS P, USERS U " +
                "WHERE P.USER_ID = U.USER_ID AND U.USER_EMAIL = ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("LIKES_CNT");
            }
        } catch (SQLException e) {
            System.out.println("MyPageDao의 getTotalLikesByEmail에서 예외 발생: " + e);
        }
        return cnt;
    }
}
