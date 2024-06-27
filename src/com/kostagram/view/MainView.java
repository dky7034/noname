package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    // 버튼 컴포넌트 생성
    private JButton addPostButton = new JButton("Add Post");
    private JButton logoutButton = new JButton("Logout");

    // 게시물 패널 및 스크롤 페인 생성
    private JPanel postsPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(postsPanel);

    // 생성자
    public MainView() {
        // JFrame 기본 설정
        setTitle("Main");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 게시물 패널 레이아웃 설정
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

        // 스크롤 페인 크기 설정 (게시물이 1.5개 정도만 보이도록)
        scrollPane.setPreferredSize(new Dimension(400, 300));

        // 메인 패널 생성 및 레이아웃 설정
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성 및 버튼 추가
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addPostButton);
        buttonsPanel.add(logoutButton);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // 메인 패널을 JFrame에 추가
        add(mainPanel);
    }

    // 게시물 추가 메서드
    public void addPost(String postContent) {
        // 게시물 내용 텍스트 영역 생성 및 설정
        JTextArea postArea = new JTextArea(postContent);
        postArea.setLineWrap(true);
        postArea.setWrapStyleWord(true);
        postArea.setEditable(false);
        postArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 게시물 패널 생성 및 설정
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        postPanel.add(postArea, BorderLayout.CENTER);

        // 게시물 패널을 postsPanel에 추가
        postsPanel.add(postPanel);
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    // 모든 게시물을 설정하는 메서드 (기존 게시물 제거 후 새로 추가)
    public void setPosts(String posts) {
        postsPanel.removeAll();
        for (String post : posts.split("\n")) {
            addPost(post);
        }
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    // Add Post 버튼의 액션 리스너 설정 메서드
    public void addAddPostListener(ActionListener listener) {
        addPostButton.addActionListener(listener);
    }

    // Logout 버튼의 액션 리스너 설정 메서드
    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}
