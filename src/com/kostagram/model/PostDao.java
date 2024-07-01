package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PostDao 클래스는 게시글 데이터를 데이터베이스에 저장하고 가져오는 역할을 합니다.
 */
public class PostDao {
    private static PostDao instance = new PostDao();

    /**
     * PostDao 생성자. 데이터베이스 연결을 확인합니다.
     */
    private PostDao() {
        try {
            ConnectionProvider.getConnection();
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
    }

    /**
     * PostDao의 인스턴스를 반환합니다.
     * @return PostDao 인스턴스
     */
    public static PostDao getInstance() {
        return instance;
    }

    /**
     * 새로운 게시글을 데이터베이스에 추가합니다.
     * @param posts 게시글 객체
     * @throws SQLException SQL 예외
     */
    public void addPost(Posts posts) throws SQLException {
        String sql = "CALL PROC_NEW_POST(?, ?, ?)";
        //String sql = "INSERT INTO posts(user_id, post_content) VALUES(?, ?)";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println(posts.getPostContent()+";"+ posts.getUserId()+";"+ posts.getHashTags());
            pstmt.setString(1, posts.getPostContent());
            pstmt.setString(2, posts.getUserId());
            pstmt.setString(3, posts.getHashTags());
            pstmt.executeUpdate();
        }
        System.out.println("sql = " + sql);
        System.out.println("게시글 추가 성공");
    }

    /**
     * 데이터베이스에서 모든 게시글을 가져옵니다.
     * @return 게시글 목록
     */
    public List<Posts> getPosts() {
//        String sql = "SELECT post_id, user_id, post_content, create_date FROM posts";
        String sql = "SELECT p.post_id, (SELECT SUBSTR(USERS.USER_EMAIL, 0, INSTR(USER_EMAIL, '@')-1) AS user_name, p.post_content, p.create_date, "
                + "(SELECT COUNT(*) FROM comments c WHERE c.post_id = p.post_id) AS comments_count "
                + "FROM posts p";
        List<Posts> posts = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Posts post = new Posts();
                post.setPostId(rs.getString("post_id"));
                post.setUserId(rs.getString("user_id"));
                post.setPostContent(rs.getString("post_content"));
                post.setCreateDate(rs.getDate("create_date"));
                post.setCommentsCount(rs.getInt("comments_count")); // 댓글 수 설정
                posts.add(post);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }
}
