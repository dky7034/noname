package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchDao {
    private static SearchDao instance = new SearchDao();

    private SearchDao() {}

    public static SearchDao getInstance() {
        return instance;
    }

    // 게시물 검색 메서드
    public List<Posts> searchPosts(String query) {
        List<Posts> postsList = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE post_content LIKE ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String postId = rs.getString("post_id");
                String content = rs.getString("post_content");
                Date createDate = rs.getDate("create_date");
                String userId = rs.getString("user_id");
                int likesCount = rs.getInt("likes_count");
                String hashTag = rs.getString("hash_tag");
                String userName = rs.getString("user_name");

                Posts post = new Posts(postId, content, createDate, userId, likesCount, hashTag, userName);
                postsList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postsList;
    }

    // 게시물 ID로 특정 게시물 조회 메서드 추가
    public Posts searchPostById(String postId) {
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, postId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String content = rs.getString("post_content");
                Date createDate = rs.getDate("create_date");
                String userId = rs.getString("user_id");
                int likesCount = rs.getInt("likes_count");
                String hashTag = rs.getString("hash_tag");
                String userName = rs.getString("user_name");

                return new Posts(postId, content, createDate, userId, likesCount, hashTag, userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 해당 게시물 아이디에 해당하는 게시물이 없을 경우 null 반환
    }
}
