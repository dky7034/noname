package com.kostagram.view;

import com.kostagram.controller.PostController;
import com.kostagram.model.PostDao;
import com.kostagram.model.Users;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostView extends JFrame {
    private BottomPanel bottomPanel;
    private JTextArea contentArea;
    private JTextArea hashtagArea;
    private PostController postController;

    public PostView(Users userInfo, PostDao postDao) {
        this.postController = new PostController(this, userInfo, postDao);

        setTitle("새 게시물 만들기");
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // 우측 패널
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // 내용 입력 필드
        JLabel contentLabel = new JLabel("내용 입력", JLabel.CENTER);
        styleLabel(contentLabel);
        rightPanel.add(contentLabel);

        contentArea = new JTextArea(10, 30);
        styleTextArea(contentArea);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(contentScrollPane);

        // 해시태그 입력 필드
        JLabel hashtagLabel = new JLabel("해시태그", JLabel.CENTER);
        styleLabel(hashtagLabel);
        rightPanel.add(hashtagLabel);

        hashtagArea = new JTextArea(3, 30);
        styleTextArea(hashtagArea);
        JScrollPane hashtagScrollPane = new JScrollPane(hashtagArea);
        hashtagScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hashtagScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(hashtagScrollPane);

        // 게시 버튼 추가
        JButton postButton = new JButton("게시");
        styleButton(postButton);
        postButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = contentArea.getText();
                String hashtags = hashtagArea.getText();
                boolean isSuccess = postController.addPost(content, hashtags);
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

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(new RoundedBorder(10)); // 둥근 테두리
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // 둥근 테두리를 위한 커스텀 Border 클래스
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

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
