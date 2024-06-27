package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    // 싱글톤 패턴 적용
    private static PostDao instance = new PostDao();

    // 외부에서 생성자를 호출할 수 없도록 생성자에 private 제한을 붙입니다.
    private PostDao() {
        try {
            ConnectionProvider.getConnection();
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
    }

    // 외부에서 객체 생성을 요구하면 getter 메서드를 이용하여 1번의 객체를 반환
    public static PostDao getInstance() {
        return instance;
    }

    // 포스트 추가 메서드
    public void addPost(Posts posts) throws SQLException {
        String sql = "INSERT INTO posts(user_id, post_content) VALUES(?, ?)";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, posts.getUserId());
            pstmt.setString(2, posts.getContent());
            pstmt.executeUpdate();
        }
    }

    // 모든 포스트를 가져오는 메서드
    public List<Posts> getPosts() {
        String sql = "SELECT post_id, user_id, post_content, create_date FROM posts";
        List<Posts> posts = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Posts post = new Posts();
                post.setPostId(rs.getString("post_id"));
                post.setUserId(rs.getString("user_id"));
                post.setContent(rs.getString("post_content"));
                post.setCreateDate(rs.getDate("create_date"));
                //post.setCreateDate(rs.getInt("likes_count"));
                //post.setCreateDate(rs.getString("post_image"));
                posts.add(post);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }
}
