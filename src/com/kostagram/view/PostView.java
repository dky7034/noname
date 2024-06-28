package com.kostagram.view;

import com.kostagram.controller.PostController;
import com.kostagram.model.PostDao;
import com.kostagram.model.Users;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PostView extends JFrame {
    private BottomPanel bottomPanel;
    private JLabel imageLabel;
    private JTextArea contentArea;
    private JTextArea hashtagArea;
    private PostController postController;

    public PostView(Users userInfo, PostDao postDao) {
        this.postController = new PostController(this, userInfo, postDao);

        setTitle("새 게시물 만들기");
        setSize(1050, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 메인 패널
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // 상단 패널
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(1050, 42));
        panel.add(topPanel, BorderLayout.NORTH);

        // 게시글 이미지 패널
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setPreferredSize(new Dimension(710, 710));
        String defaultImagePath = "./src/com/kostagram/image/";
        imageLabel = new JLabel(new ImageIcon(defaultImagePath + "test.jpg"));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // 이미지 파일 선택 버튼 추가
        JButton selectImageButton = new JButton("이미지 선택");
        styleButton(selectImageButton);
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // 파일 필터 추가
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imageLabel.setIcon(new ImageIcon(selectedFile.getPath()));
                }
            }
        });
        leftPanel.add(selectImageButton, BorderLayout.SOUTH);

        panel.add(leftPanel, BorderLayout.CENTER);

        // 게시글 내용 작성 패널
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setPreferredSize(new Dimension(340, 710));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // 내용 입력 필드
        JLabel contentLabel = new JLabel("내용 입력", JLabel.CENTER);
        styleLabel(contentLabel);
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(contentLabel);

        contentArea = new JTextArea(10, 30);
        styleTextArea(contentArea);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(contentScrollPane);

        // 해시태그 입력 필드
        JLabel hashtagLabel = new JLabel("해시태그", JLabel.CENTER);
        styleLabel(hashtagLabel);
        hashtagLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(hashtagLabel);

        hashtagArea = new JTextArea(3, 30);
        styleTextArea(hashtagArea);
        JScrollPane hashtagScrollPane = new JScrollPane(hashtagArea);
        hashtagScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hashtagScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        hashtagScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        panel.add(rightPanel, BorderLayout.EAST);

        add(panel);
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
