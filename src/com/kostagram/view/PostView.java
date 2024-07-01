package com.kostagram.view;

import com.kostagram.controller.PostController;
import com.kostagram.model.PostDao;
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
    public PostView(Users userInfo, PostDao postDao) {
        this.postController = new PostController(this, userInfo, postDao);

        // 프레임 설정
        setTitle("새 게시물 만들기");
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정
        setLocationRelativeTo(null); // 화면 중앙에 창 배치
        setResizable(false); // 창 크기 조절 불가

        // 메인 패널 설정
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // 우측 패널 설정
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // 내용 입력 레이블 설정 및 추가
        JLabel contentLabel = new JLabel("내용 입력", JLabel.CENTER);
        styleLabel(contentLabel);
        rightPanel.add(contentLabel);

        // 내용 입력 필드 설정 및 추가
        contentArea = new JTextArea(10, 30);
        styleTextArea(contentArea);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(contentScrollPane);

        // 해시태그 입력 레이블 설정 및 추가
        JLabel hashtagLabel = new JLabel("해시태그", JLabel.CENTER);
        styleLabel(hashtagLabel);
        rightPanel.add(hashtagLabel);

        // 해시태그 입력 필드 설정 및 추가
        hashtagArea = new JTextArea(3, 30);
        styleTextArea(hashtagArea);
        JScrollPane hashtagScrollPane = new JScrollPane(hashtagArea);
        hashtagScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hashtagScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(hashtagScrollPane);

        // 게시 버튼 설정 및 추가
        JButton postButton = new JButton("게시");
        styleButton(postButton);
        postButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = contentArea.getText(); // 내용 가져오기
                String hashtags = hashtagArea.getText(); // 해시태그 가져오기
                boolean isSuccess = postController.addPost(content); // 게시물 추가 시도
                if (isSuccess) {
                    JOptionPane.showMessageDialog(null, "게시글 작성 성공!\n내용: " + content + "\n해시태그: " + hashtags);
                    dispose(); // 창 닫기
                } else {
                    JOptionPane.showMessageDialog(null, "게시글 작성 실패! 다시 시도해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        rightPanel.add(Box.createVerticalStrut(10)); // 간격 추가
        rightPanel.add(postButton);

        // 메인 패널에 우측 패널 추가
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // 프레임에 메인 패널 추가
        add(mainPanel);
        setVisible(true); // 창 보이기
    }

    // 버튼 스타일 설정 메서드
    private void styleButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setForeground(Color.LIGHT_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(new RoundedBorder(10)); // 둥근 테두리
    }

    // 레이블 스타일 설정 메서드
    private void styleLabel(JLabel label) {
        label.setForeground(Color.LIGHT_GRAY);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // 텍스트 영역 스타일 설정 메서드
    private void styleTextArea(JTextArea textArea) {
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setCaretColor(Color.LIGHT_GRAY);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
            g2.setColor(Color.LIGHT_GRAY); // 테두리 색상을 회색으로 설정
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
