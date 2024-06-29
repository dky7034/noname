package com.kostagram.view;

import com.kostagram.model.Posts;
import com.kostagram.model.SearchDao;
import com.kostagram.model.Users;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PostDetailView extends JFrame {
    private JPanel mainPanel;
    private JLabel postIdLabel;
    private JLabel postContentLabel;
    private JLabel postCreateDateLabel;
    private JLabel postLikesCountLabel;

    public PostDetailView(String postId, Users userInfo) {
        setTitle("게시물 상세 화면");
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 내부 여백 설정

        // SearchDao를 사용하여 게시물 상세 정보 가져오기
        SearchDao searchDao = SearchDao.getInstance();
        Posts post = searchDao.searchPostById(postId);

        if (post != null) {
            postIdLabel = new JLabel("게시물 ID: " + post.getPostId());
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
        panel.setBackground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10)); // 내부 여백 설정
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // 실제 게시물 ID를 매개변수로 넘겨주어 화면을 띄우는 예제
            String postId = "실제 게시물 ID"; // 실제 게시물 ID로 변경해야 함

            // 사용자 정보 예시 (임의로 생성)
            Users userInfo = new Users();
            userInfo.setUserId("사용자 ID"); // 사용자 ID 설정 등

            PostDetailView frame = new PostDetailView(postId, userInfo);
            frame.setVisible(true);
        });
    }
}
