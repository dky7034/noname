package com.kostagram.view;

import com.kostagram.controller.PostController;
import com.kostagram.model.Users;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 게시물 작성 뷰 클래스
public class PostView extends JFrame {
    private JTextArea contentArea; // 게시물 내용 입력 필드
    private JTextArea hashtagArea; // 해시태그 입력 필드
    private PostController postController; // 게시물 컨트롤러

    // 생성자: 사용자 정보와 게시물 DAO를 매개변수로 받아 초기화
    public PostView(Users userInfo) {
        this.postController = new PostController(this, userInfo);

        // 프레임 설정
        setTitle("새 게시물 만들기");
        setSize(475, 650); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정
        setLocationRelativeTo(null); // 화면 중앙에 창 배치
        setResizable(false); // 창 크기 조절 불가

        // 메인 패널 설정
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 내용 입력 레이블 설정 및 추가
        JLabel contentLabel = new JLabel("내용 입력", JLabel.CENTER);
        styleLabel(contentLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(contentLabel, gbc);

        // 내용 입력 필드 설정 및 추가
        contentArea = new JTextArea(10, 30);
        styleTextArea(contentArea);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(contentScrollPane, gbc);

        // 해시태그 입력 레이블 설정 및 추가
        JLabel hashtagLabel = new JLabel("해시태그", JLabel.CENTER);
        styleLabel(hashtagLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(hashtagLabel, gbc);

        // 해시태그 입력 필드 설정 및 추가
        hashtagArea = new JTextArea(3, 30);
        styleTextArea(hashtagArea);
        JScrollPane hashtagScrollPane = new JScrollPane(hashtagArea);
        hashtagScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hashtagScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(hashtagScrollPane, gbc);

        // 게시 버튼 설정 및 추가
        JButton postButton = new JButton("게시");
        styleButton(postButton);
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = contentArea.getText(); // 내용 가져오기
                String hashtags = hashtagArea.getText(); // 해시태그 가져오기
                boolean isSuccess = postController.addPost(content, hashtags, userInfo); // 게시물 추가 시도
                if (isSuccess) {
                    JOptionPane.showMessageDialog(null, "게시글 작성 성공!\n내용: " + content + "\n해시태그: " + hashtags);
                    dispose(); // 창 닫기
                } else {
                    JOptionPane.showMessageDialog(null, "게시글 작성 실패! 다시 시도해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(postButton, gbc);

        // 프레임에 메인 패널 추가
        add(mainPanel);
        setVisible(true); // 창 보이기
    }

    // 버튼 스타일 설정 메서드
    private void styleButton(JButton button) {
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x007BFF)));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(new RoundedBorder(10)); // 둥근 테두리
        button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 40));
    }

    // 레이블 스타일 설정 메서드
    private void styleLabel(JLabel label) {
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
    }

    // 텍스트 영역 스타일 설정 메서드
    private void styleTextArea(JTextArea textArea) {
        textArea.setBackground(Color.LIGHT_GRAY); // 배경 색상
        textArea.setForeground(Color.BLACK); // 글자 색상
        textArea.setCaretColor(Color.BLACK); // 커서 색상
        textArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    // 둥근 테두리를 위한 커스텀 Border 클래스
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        // 생성자: 테두리 반지름을 매개변수로 받음
        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0x007BFF)); // 테두리 색상을 변경
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius + 1;
            return insets;
        }
    }
}
