package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private static CommentDao instance;
    private Connection conn;

    private CommentDao() {
        conn = ConnectionProvider.getConnection();
    }

    public static CommentDao getInstance() {
        if (instance == null) {
            instance = new CommentDao();
        }
        return instance;
    }

    public List<Comments> getCommentsByPostId(String postId) {
        List<Comments> comments = new ArrayList<>();
        String sql = "SELECT c.comment_content, c.create_date, substr(u.user_email,1,instr(u.USER_EMAIL,'@')-1) AS USER_EMAIL\n" +
                "FROM comments c, users u Where c.user_id = u.user_id AND c.post_id = ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comments comment = new Comments();
                    comment.setUserEmail(rs.getString("user_email"));
                    comment.setCommentContent(rs.getString("comment_content"));
                    comment.setCreateDate(rs.getTimestamp("create_date"));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void addComment(Comments comment) {
        try {
            String query = "INSERT INTO comments (comment_content, user_id, post_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, comment.getCommentContent());
            pstmt.setString(2, comment.getUserId());
            pstmt.setString(3, comment.getPostId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

