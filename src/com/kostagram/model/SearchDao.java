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
        String sql = "SELECT POST_ID, POST_CONTENT, CREATE_DATE, USER_ID, (SELECT SUBSTR(USERS.USER_EMAIL, 0, INSTR(USER_EMAIL, '@')-1) FROM USERS WHERE USER_ID =P.USER_ID) AS USER_NAME, LIKES_COUNT FROM POSTS P WHERE P.post_content LIKE ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String postId = rs.getString("POST_ID");
                String content = rs.getString("POST_CONTENT");
                Date createDate = rs.getDate("CREATE_DATE");
                String userId = rs.getString("USER_ID");
                String userName = rs.getString("USER_NAME");
                int likesCount = rs.getInt("LIKES_COUNT");

                Posts post = new Posts(postId, content, createDate, userId, likesCount, userName);
              
                postsList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postsList;
    }

}
