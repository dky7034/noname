package com.kostagram.view;

import com.kostagram.model.Posts;
import com.kostagram.model.Users;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class PostDetailView extends JFrame {
    private JPanel mainPanel;
    private JLabel postIdLabel;
    private JLabel postContentLabel;
    private JLabel postCreateDateLabel;
    private JLabel postLikesCountLabel;

    public PostDetailView(Posts post, Users userInfo) {
        setTitle("게시물 상세 화면");
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        mainPanel.setBackground(Color.black);
        // mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 내부 여백 설정

        if (post != null) {
            postIdLabel = new JLabel("작성자 Email: " + userInfo.getEmail());
            postIdLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            postContentLabel = new JLabel("게시물 내용: " + post.getPostContent());
            postContentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            postCreateDateLabel = new JLabel("게시물 생성일: " + post.getCreateDate());
            postCreateDateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            postLikesCountLabel = new JLabel("게시물 좋아요 수: " + post.getLikesCount());
            postLikesCountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

            // 레이블을 패널에 추가
            mainPanel.add(createLabelPanel(postIdLabel));
            mainPanel.add(createLabelPanel(postContentLabel));
            mainPanel.add(createLabelPanel(postCreateDateLabel));
            mainPanel.add(createLabelPanel(postLikesCountLabel));
        } else {
            // 데이터베이스에서 해당 게시물을 찾지 못했을 때의 처리
            JLabel errorLabel = new JLabel("해당 게시물을 찾을 수 없습니다.");
            errorLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            mainPanel.add(errorLabel);
        }

        // JFrame에 메인 패널 추가
        add(mainPanel, BorderLayout.CENTER);
    }

    // 레이블을 감싸는 패널을 생성하는 메서드
    private JPanel createLabelPanel(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(label, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10)); // 내부 여백 설정
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // 실제 게시물 ID를 매개변수로 넘겨주어 화면을 띄우는 예제
            String postId = "1"; // 실제 게시물 ID로 변경해야 함

            // 사용자 정보 예시 (임의로 생성)
            Users userInfo = new Users();
            userInfo.setUserId("사용자 ID"); // 사용자 ID 설정
            userInfo.setEmail("example@example.com"); // 사용자 이메일 설정

            // 게시물 정보 예시 (임의로 생성)
            Posts post = new Posts("1", "This is a test post.", new Date(), "user1", 10, "해시태그", "USER1");

            PostDetailView frame = new PostDetailView(post, userInfo);
            frame.setVisible(true);
        });
    }
}
