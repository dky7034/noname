package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;
import java.sql.*;

public class UserDao {

    // 연결 담당 클래스 객체 생성
    ConnectionProvider connectionProvider = new ConnectionProvider();

    /*
     * DAO 클래스는 단순히 DB 연동만 담당하므로 불필요하게 객체를 여러 개 생성할 필요가 없습니다.
     * 그래서 싱글톤 패턴을 적용해서 객체를 1개만 생성하도록 만듭니다.
     */
    // 1. 스스로 객체를 1개 생성합니다.
    private static UserDao instance = new UserDao();

    // 2. 외부에서 생성자를 호출할 수 없도록 생성자에 private 제한을 붙입니다.
    private UserDao() {
        try {
            ConnectionProvider.getConnection();
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
    }

    // 3. 외부에서 객체 생성을 요구하면 getter 메서드를 이용하여 1번의 객체를 반환
    public static UserDao getInstance() {
        return instance;
    }

    // 회원 가입 메서드
    public int addUser(Users userInfo) {
        String sql = "INSERT INTO USERS(USER_EMAIL, USER_PASSWORD) VALUES(?,?)";
        int re = -1;
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userInfo.getEmail());
            pstmt.setString(2, userInfo.getPassword());
            re = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("회원 가입 메서드에서 예외 발생: " + e.getMessage());
        }
        return re;
    }

    // 이메일을 입력받아 해당 이메일의 사용자 정보를 반환하는 메서드
    public Users getUserByEmail(String email) {
        // SQL 쿼리 정의: USERS 테이블에서 특정 이메일을 가진 사용자의 정보를 선택
        String sql = "SELECT USER_ID, USER_EMAIL, USER_PASSWORD, CREATE_DATE FROM USERS WHERE USER_EMAIL = ?";
        Users users = null; // 결과를 저장할 users 객체 선언

        try (Connection conn = ConnectionProvider.getConnection();// 데이터베이스 연결 얻기
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // SQL 문 준비
            pstmt.setString(1, email); // SQL 문에 이메일 값 설정
            ResultSet rs = pstmt.executeQuery(); // 쿼리 실행 및 결과 집합 얻기

            if (rs.next()) { // 결과 집합이 비어 있지 않은지 확인
                users = new Users(); // users 객체 생성
                users.setUserId(rs.getString("USER_ID")); // user_id 값을 Users 객체에 설정
                users.setEmail(rs.getString("USER_EMAIL")); // email 값을 Users 객체에 설정
                users.setPassword(rs.getString("USER_PASSWORD")); // password 값을 Users 객체에 설정
                users.setCreateDate(rs.getDate("CREATE_DATE")); // create_date 값을 Users 객체에 설정
            }
//            System.out.println("사용자 정보 출력: " + users.toString());
        } catch (SQLException e) {
            System.out.println("사용자 정보 반환 메서드에서 예외 발생: " + e); // 예외 발생 시 오류 메시지 출력
        }
        return users; // users 객체 반환 (결과가 없으면 null)
    }

}
