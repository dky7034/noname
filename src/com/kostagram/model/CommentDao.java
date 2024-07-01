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

    public List<Comments> getComments(String postId) {
        List<Comments> comments = new ArrayList<>();
        try {
            String query = "SELECT COMMENT_CONTENT, (SELECT USER_EMAIL FROM USERS u WHERE u.user_id = c.user_id) FROM comments c WHERE post_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, postId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comments comment = new Comments(
                        rs.getString("comment_content")
                );
                comments.add(comment);
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

