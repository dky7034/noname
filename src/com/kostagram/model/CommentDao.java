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
            String query = "SELECT * FROM comments WHERE post_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, postId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comments comment = new Comments(
                        rs.getInt("id"),
                        rs.getString("comment_content"),
                        rs.getString("user_id"),
                        rs.getString("post_id"),
                        rs.getTimestamp("create_date")
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
            String query = "INSERT INTO comments (comment_content, user_id, post_id, create_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, comment.getCommentContent());
            pstmt.setString(2, comment.getUserId());
            pstmt.setString(3, comment.getPostId());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
