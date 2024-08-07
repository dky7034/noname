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

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Post Content: " + posts.getPostContent());
            System.out.println("User ID in PostDao: " + posts.getUserId());
            System.out.println("Hash Tags: " + posts.getHashTags());

            pstmt.setString(1, posts.getPostContent());
            pstmt.setString(2, posts.getUserId());
            pstmt.setString(3, posts.getHashTags());
            pstmt.executeUpdate();

            System.out.println("게시글 추가 성공");
        } catch (SQLException e) {
            System.out.println("SQL 예외 발생: " + e.getMessage());
            throw e; // 예외를 다시 던져서 호출한 메서드에서도 예외를 처리할 수 있게 합니다.
        }
    }

    /**
     * 데이터베이스에서 모든 게시글을 가져옵니다.
     * @return 게시글 목록
     */
    public List<Posts> getPosts() {
        String sql = "SELECT P.POST_ID, P.USER_ID, " +
                "(SELECT SUBSTR(U.USER_EMAIL, 0, INSTR(U.USER_EMAIL, '@') - 1) FROM USERS U WHERE U.USER_ID = P.USER_ID) AS USER_NAME, " +
                "P.POST_CONTENT, P.CREATE_DATE, " +
                "(SELECT COUNT(*) FROM COMMENTS C WHERE C.POST_ID = P.POST_ID) AS COMMENT_COUNT " +
                "FROM POSTS P " +
                "ORDER BY P.CREATE_DATE DESC"; ;
        List<Posts> posts = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Posts post = new Posts();
                post.setPostId(rs.getString("POST_ID"));
                post.setUserId(rs.getString("USER_ID"));
                post.setUserName(rs.getString("USER_NAME"));
                post.setPostContent(rs.getString("POST_CONTENT"));
                post.setCreateDate(rs.getDate("CREATE_DATE"));
                post.setCommentsCount(rs.getInt("COMMENT_COUNT")); // 댓글 수 설정
                posts.add(post);
            }

        } catch (SQLException e) {
            System.out.println("SQL 예외 발생: " + e.getMessage());
        }
        return posts;
    }
}
