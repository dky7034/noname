package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    // 연결 담당 클래스 객체 생성
    ConnectionProvider connectionProvider = new ConnectionProvider();

    /*
     * DAO 클래스는 단순히 DB 연동만 담당하므로 불필요하게 객체를 여러 개 생성할 필요가 없습니다.
     * 그래서 싱글톤 패턴을 적용해서 객체를 1개만 생성하도록 만듭니다.
     */
    // 1. 스스로 객체를 1개 생성합니다.
    private static CommentDao instance = new CommentDao();

    // 2. 외부에서 생성자를 호출할 수 없도록 생성자에 private 제한을 붙입니다.
    private CommentDao() {
        try {
            ConnectionProvider.getConnection();
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
    }

    // 3. 외부에서 객체 생성을 요구하면 getter 메서드를 이용하여 1번의 객체를 반환
    public static CommentDao getInstance() {
        return instance;
    }

    // 댓글 추가 메서드
    public void addComment(Comments comments) {
        String sql = "INSERT INTO comments(post_id, user_id, comment_content) VALUES(?,?,?)";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, comments.getPostId());
            pstmt.setString(2, comments.getUserId());
            pstmt.setString(3, comments.getContent());
            pstmt.setDate(4, new Date(comments.getCreateDate().getTime()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 추가 메서드에서 예외 발생: " + e.getMessage());
        }
    }

    // 특정 게시글의 모든 댓글을 불러오는 메서드
    public List<Comments> getCommentsByPostId(int postId) {
        String sql = "SELECT comment_id, post_id, user_id, comment_content, create_date FROM comments WHERE post_id = ?";
        List<Comments> comments = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Comments comment = new Comments();
                comment.setCommentId(rs.getString("comment_id"));
                comment.setPostId(rs.getString("post_id"));
                comment.setUserId(rs.getString("user_id"));
                comment.setContent(rs.getString("comment_content"));
                comment.setCreateDate(rs.getDate("create_date"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println("특정 게시글 댓글을 불러오는 메서드에서 예외 발생: " + e.getMessage());
        }
        return comments;
    }
}
