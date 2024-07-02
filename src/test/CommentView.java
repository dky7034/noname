package test;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommentView extends JFrame {
    // 댓글 화면의 타이틀
    private JLabel commentTitle = new JLabel("댓글");
    Font titleFont = new Font("Bauhaus 93", Font.BOLD, 20);

    // 하단 패널에 들어갈 프로필 이미지, 버튼
    private JLabel profileImage = new JLabel();
    private JTextField commentText = new JTextField("댓글을 입력하세요.");
    private JButton commentAddButton = new JButton();
    private Color backgroundColor = new Color(38, 41, 46);

    private JPanel contentPanel = new JPanel();
    private JScrollPane commentScroll;

    public CommentView() {
        setTitle("Comment");
        setSize(450, 920);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 상단 타이틀 패널
        JPanel northPanel = new JPanel();
        northPanel.setBackground(backgroundColor);
        northPanel.add(commentTitle);

        // 타이틀 패널 생성 및 크기 조절
        northPanel.setPreferredSize(new Dimension(450, 35));
        commentTitle.setFont(titleFont);
        commentTitle.setForeground(Color.white);

        // 게시글이랑 댓글 보여줄 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 프로필, 댓글입력창, 전송버튼의 크기 및 색상 조절
        profileImage.setPreferredSize(new Dimension(44, 50));
        profileImage.setIcon(new ImageIcon(getClass().getResource("profile.png")));

        commentText.setPreferredSize(new Dimension(300, 45));
        commentAddButton.setPreferredSize(new Dimension(44, 50));
        // 댓글창 배경 및 폰트 색깔
        commentText.setBackground(backgroundColor);
        commentText.setForeground(Color.WHITE);
        // 전송 버튼 배경색 및 폰트 색깔
        commentAddButton.setOpaque(false);
        commentAddButton.setContentAreaFilled(false);
        commentAddButton.setBorderPainted(false);
        commentAddButton.setIcon(new ImageIcon(getClass().getResource("add_comment.png")));
//        commentAddButton.addActionListener(e -> addComment());

        // 하단에 버튼과 프로필 텍스트 필드를 넣을 패널
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.setBackground(backgroundColor);

        southPanel.add(profileImage);
        southPanel.add(commentText);
        southPanel.add(commentAddButton);
        southPanel.setPreferredSize(new Dimension(450, 55)); // 하단의 패널 크기 조절

        // 게시물 내용과 댓글을 보여줄 패널
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(backgroundColor);

        // 스크롤 패널 안에 게시글 패널과 댓글 패널 삽입
        commentScroll = new JScrollPane(contentPanel);
        commentScroll.getViewport().setBackground(backgroundColor); // 스크롤 뷰포트 배경색 설정
        commentScroll.setBackground(backgroundColor); // 스크롤 패널 배경색 설정

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(commentScroll, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
        // 초기 데이터 로드
        loadComments();
    }

    private void loadComments() {
        contentPanel.removeAll();


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 데이터베이스 연결 설정
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("드라이브 로드 성공");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            System.out.println("데이터베이스 접속 성공");
            String user = "c##kosta";
            String password = "kosta";

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // 게시글 가져오기
            String postQuery = "SELECT POST_CONTENT FROM POSTS WHERE post_id = '1BD64188B84B06E7E063020011AC9DF1'"; // 예시 쿼리, 적절하게 수정
            resultSet = statement.executeQuery(postQuery);
            System.out.println(resultSet);

            if (resultSet.next()) {
                String postContent = resultSet.getString("POST_CONTENT");
                JLabel postLabel = new JLabel(postContent);
                postLabel.setForeground(Color.WHITE); // 텍스트 색상 변경
                contentPanel.add(postLabel);
            }

            // 댓글 가져오기
//            String commentQuery = "SELECT COMMENT_CONTENT, (SELECT USER_EMAIL FROM USERS u WHERE u.USER_ID = c.USER_ID), " +
//                    "(SELECT USER_IMAGE FROM USERS u WHERE u.USER_ID = c.USER_ID) FROM COMMENTS c WHERE POST_ID = '1BD64188B84B06E7E063020011AC9DF1' "; // 쿼리 문
            String commentQuery = "SELECT COMMENT_CONTENT, USER_EMAIL, USER_IMAGE " +
                    "FROM COMMENTS c JOIN USERS u ON c.USER_ID = u.USER_ID " +
                    "WHERE POST_ID = '1BD64188B84B06E7E063020011AC9DF1'";

            resultSet = statement.executeQuery(commentQuery);

            while (resultSet.next()) {
                String commentContent = resultSet.getString("COMMENT_CONTENT");
                String user_email = resultSet.getString("USER_EMAIL");
                String user_image = resultSet.getString("USER_IMAGE");
                JPanel commentPanel = new JPanel(new BorderLayout());
                commentPanel.setBackground(backgroundColor);
                // 프로필 이미지와 사용자명 패널
                JPanel profilePanel = new JPanel(new BorderLayout());
                profilePanel.setBackground(backgroundColor);
//                JLabel userProfileImage = new JLabel(new ImageIcon(getClass().getResource(user_image)));
                JLabel userProfileImage = new JLabel(new ImageIcon(getClass().getResource(user_image)));
                userProfileImage.setPreferredSize(new Dimension(44, 44));
                JLabel useremailLabel = new JLabel(user_email);
                useremailLabel.setForeground(Color.WHITE);
                profilePanel.add(userProfileImage, BorderLayout.WEST);
                profilePanel.add(useremailLabel, BorderLayout.CENTER);

                // 댓글 내용 패널
                JLabel commentLabel = new JLabel(commentContent);
                commentLabel.setForeground(Color.WHITE);

                commentPanel.add(profilePanel, BorderLayout.WEST);
                commentPanel.add(commentLabel, BorderLayout.CENTER);

                contentPanel.add(commentPanel);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

//    private void addComment() {
//        String newComment = commentText.getText();
//        if (newComment.isEmpty() || newComment.equals("댓글을 입력하세요.")) {
//            return;
//        }
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            // 데이터베이스 연결 설정
//            String url = "jdbc:mysql://localhost:3306/your_database";
//            String user = "c##kosta";
//            String password = "kosta";
//
//            connection = DriverManager.getConnection(url, user, password);
//
//            // 새로운 댓글 추가
//            String insertQuery = "INSERT INTO comments (POST_ID, user_id, content) VALUES (?, ?, ?)";
//            preparedStatement = connection.prepareStatement(insertQuery);
//            preparedStatement.setInt(1, 1); // 예시로 post_id를 1로 설정, 적절하게 수정
//            preparedStatement.setInt(2, 1); // 예시로 user_id를 1로 설정, 적절하게 수정
//            preparedStatement.setString(3, newComment);
//            preparedStatement.executeUpdate();
//
//            // 입력 필드 초기화
//            commentText.setText("댓글을 입력하세요.");
//
//            // 댓글 목록 갱신
//            loadComments();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CommentView().setVisible(true);
            }
        });
    }
}

