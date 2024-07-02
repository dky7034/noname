package com.kostagram.view;

import com.kostagram.controller.CommentController;
import com.kostagram.controller.LoginController;
import com.kostagram.model.CommentDao;
import com.kostagram.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class test2 extends JFrame {
    MainView mainView = new MainView();
    private JPanel mainPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(mainPanel);
    private JButton commentAddBtn = new JButton();
    private JTextField textField = new JTextField();
    protected static final Font titleFont = new Font("맑은 고딕", Font.BOLD, 16); // 글꼴 설정
    protected static final Font contentFont = new Font("맑은 고딕", Font.PLAIN, 14); // 내용 글꼴 설정
    protected static final Color bgColor = Color.BLACK; // 배경 색 설정
    protected static final Color fgColor = Color.WHITE; // 글자 색 설정
    public static Users user;


    public test2(CommentController commentController) {
        setTitle("댓글 작성");
        setSize(450, 920);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        user = LoginController.users;

        // 메인 패널 설정
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgColor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 상단 패널 설정
        JPanel topPanel = new JPanel();
        topPanel.setBackground(bgColor);
        topPanel.setPreferredSize(new Dimension(450, 35));
        JLabel titleLabel = new JLabel("댓글");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        titleLabel.setForeground(fgColor);
        topPanel.add(titleLabel);

        // 하단 패널 설정
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(450, 45));
        bottomPanel.setBackground(bgColor);

        // 사용자 이미지
        JLabel userImageLabel = new JLabel();
        ImageIcon userIcon = new ImageIcon("src/com/kostagram/icon/user.png");
        userImageLabel.setIcon(userIcon);

        // 텍스트 필드 설정
        textField.setBackground(bgColor);
        textField.setForeground(fgColor);
        textField.setPreferredSize(new Dimension(230, 20));

        // 댓글 추가 버튼 설정
        ImageIcon commentAddIcon = new ImageIcon("src/com/kostagram/icon/add_comment.png");
        commentAddBtn.setIcon(commentAddIcon);
        commentAddBtn.setContentAreaFilled(false);
        commentAddBtn.setBorderPainted(false);
        commentAddBtn.setPreferredSize(new Dimension(30, 24));
        bottomPanel.add(userImageLabel, BorderLayout.WEST);
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(commentAddBtn, BorderLayout.EAST);

        // 레이아웃 설정
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 댓글 추가 버튼 리스너 등록
        commentAddBtn.addActionListener(e -> {
            String commentText = textField.getText();
            if (!commentText.isEmpty()) {
                commentController.addComment(commentText);
                textField.setText(""); // 댓글 입력 필드 초기화
            }
        });

        setVisible(true);
    }

    public test2() {}

    public String getComment() {
        return textField.getText();
    }

    public void addCommentListener(ActionListener listener) {
        commentAddBtn.addActionListener(listener);
    }

    public static void main(String[] args) {
        Users user = new Users(); // 사용자 정보 설정
        user.setUserId("testUser");
        CommentDao commentDao = CommentDao.getInstance();
        CommentController commentController = new CommentController(null, commentDao, "postId", user);
        new test2(commentController);
    }
}