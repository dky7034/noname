package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    ConnectionProvider connectionProvider = new ConnectionProvider();

    private static CommentDao instance = new CommentDao();

    private CommentDao() {
        try {
            ConnectionProvider.getConnection();
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
    }

    public static CommentDao getInstance() {
        return instance;
    }

    public void addComment(Comments comments) {
        String sql = "INSERT INTO comments(post_id, user_id, comment_content, create_date) VALUES(?,?,?,?)";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, comments.getPostId());
            pstmt.setString(2, comments.getUserId());
            pstmt.setString(3, comments.getCommentContent());
            pstmt.setTimestamp(4, new Timestamp(comments.getCreateDate().getTime()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 추가 메서드에서 예외 발생: " + e.getMessage());
        }
    }

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
                comment.setCommentContent(rs.getString("comment_content"));
                comment.setCreateDate(rs.getTimestamp("create_date"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println("특정 게시글 댓글을 불러오는 메서드에서 예외 발생: " + e.getMessage());
        }
        return comments;
    }
}
