//package com.kostagram.view;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionListener;
//
//public class PostViewTest extends JFrame {
//    private JTextArea contentArea = new JTextArea(10, 30);
//    private JButton postButton = new JButton("Post");
//
//    public PostViewTest(String postContent, String imagePath) {
//        setSize(1050, 750);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 해당하는 view만 종료하도록 작성
//        setLocationRelativeTo(null);
//        setResizable(false);
//
//        JPanel panel = new JPanel(new BorderLayout());
//
//        // 상단 패널
//        JPanel topPanel = new JPanel();
//        topPanel.setBackground(MainView.bgColor);
//        topPanel.setPreferredSize(new Dimension(1050, 42));
//
//        // 게시글 내용 작성 패널 (왼쪽 패널)
//        JPanel leftPanel = new JPanel();
//        leftPanel.setBackground(MainView.bgColor);
//        leftPanel.setPreferredSize(new Dimension(710, 710));
//
//        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
//        imageLabel.setPreferredSize(new Dimension(710, 710));
//        leftPanel.add(imageLabel);
//
//        // 게시글 내용 작성 패널 (오른쪽 패널)
//        JPanel rightPanel = new JPanel();
//        rightPanel.setBackground(MainView.fgColor);
//        rightPanel.setPreferredSize(new Dimension(340, 710));
//        rightPanel.setLayout(new BorderLayout());
//
//        contentArea.setText(postContent);
//        contentArea.setLineWrap(true);
//        contentArea.setWrapStyleWord(true);
//        contentArea.setEditable(false); // 내용 수정 불가
//        JScrollPane scrollPane = new JScrollPane(contentArea);
//        rightPanel.add(scrollPane, BorderLayout.CENTER);
//
//        // 버튼 추가
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setBackground(MainView.fgColor);
//        buttonPanel.add(postButton);
//        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        panel.add(topPanel, BorderLayout.NORTH);
//        panel.add(leftPanel, BorderLayout.WEST);
//        panel.add(rightPanel, BorderLayout.EAST);
//
//        this.add(panel);
//        this.setVisible(true);
//    }
//
//    public String getCommentContent() {
//        return contentArea.getText();
//    }
//
//    public void addPostListener(ActionListener listener) {
//        postButton.addActionListener(listener);
//    }
//
//    public static void main(String[] args) {
//        // 예제 데이터를 사용하여 PostView 인스턴스 생성
//        SwingUtilities.invokeLater(() -> new PostViewTest("This is a sample post content.", "./src/com/kostagram/image/test1.jpg"));
//    }
//}
