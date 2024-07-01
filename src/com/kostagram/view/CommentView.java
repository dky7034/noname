package com.kostagram.view;

import com.kostagram.controller.CommentController;
import com.kostagram.controller.LoginController;
import com.kostagram.model.CommentDao;
import com.kostagram.model.Users;
import com.kostagram.model.Comments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class CommentView extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(mainPanel);
    private JButton commentAddBtn = new JButton();
    private JTextField textField = new JTextField();
    private Users user;
    private CommentController commentController;
    private CommentDao commentDao;

    protected static final Font font = new Font("맑은 고딕", Font.BOLD, 12);
    protected static final Color bgColor = new Color(38, 41, 46);

    public CommentView() {
        user = LoginController.users;  // user 초기화

        setTitle("Comment");
        setSize(450, 920);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgColor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(bgColor);
        topPanel.setPreferredSize(new Dimension(450, 35));
        JLabel titleLabel = new JLabel("댓글");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        titleLabel.setForeground(Color.white);
        topPanel.add(titleLabel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(450, 45));
        bottomPanel.setBackground(bgColor);

        JLabel userImageLabel = new JLabel();
        ImageIcon userIcon = createImageIcon("user.png");
        if (userIcon != null) {
            userImageLabel.setIcon(userIcon);
        } else {
            userImageLabel.setText("No Image");
        }

        textField.setBackground(bgColor);
        textField.setForeground(Color.white);
        textField.setPreferredSize(new Dimension(230, 20));

        ImageIcon commentAddIcon = createImageIcon("add_comment.png");
        if (commentAddIcon != null) {
            commentAddBtn.setIcon(commentAddIcon);
        } else {
            commentAddBtn.setText("Add");
        }
        commentAddBtn.setContentAreaFilled(false);
        commentAddBtn.setBorderPainted(false);
        commentAddBtn.setPreferredSize(new Dimension(30, 24));

        bottomPanel.add(userImageLabel, BorderLayout.WEST);
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(commentAddBtn, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // CommentController 초기화
        commentDao = CommentDao.getInstance();
        String postId = "samplePostId";  // 실제 포스트 ID로 변경
        commentController = new CommentController(commentDao, this, postId, user);

        // 기존 댓글 불러오기
        displayComments();

        // 댓글 추가 버튼 리스너
        commentAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText();
                if (!content.trim().isEmpty()) {
                    commentController.addComment(content);
                    textField.setText("");
                    displayComments();  // 새로운 댓글을 포함하여 댓글 목록 갱신
                }
            }
        });

        setVisible(true);
    }

    private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CommentView.class.getResource("/com/kostagram/icon/" + path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("파일을 찾을 수 없습니다: " + path);
            return null;
        }
    }

    public String getComment() {
        return textField.getText();
    }

    public void addCommentListener(ActionListener listener) {
        commentAddBtn.addActionListener(listener);
    }

    public void addCommentPanel(Comments comment) {
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BorderLayout());
        commentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        commentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentPanel.setBackground(new Color(23, 23, 23));

        JPanel userInfoPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 16);
        userInfoPanel.setLayout(flowLayout);
        userInfoPanel.setBackground(new Color(23, 23, 23));

        JLabel userImage = new JLabel();
        ImageIcon icon = createImageIcon("user_image1.png");
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
            userImage.setIcon(new ImageIcon(scaledImage));
        } else {
            userImage.setText("No Image");
        }

        JLabel userId = new JLabel(comment.getUserId());
        userId.setForeground(Color.WHITE);
        JLabel postTime = new JLabel(comment.getCreateDate().toString());
        postTime.setForeground(Color.LIGHT_GRAY);

        userInfoPanel.add(userImage);
        userInfoPanel.add(userId);
        userInfoPanel.add(Box.createHorizontalStrut(10));
        userInfoPanel.add(postTime);

        JTextArea commentTextArea = new JTextArea();
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        commentTextArea.setEditable(false);
        commentTextArea.setBackground(new Color(23, 23, 23));
        commentTextArea.setForeground(Color.WHITE);
        commentTextArea.setText(comment.getCommentContent());

        commentTextArea.setSize(new Dimension(350, commentTextArea.getPreferredSize().height));
        commentTextArea.setPreferredSize(new Dimension(350, commentTextArea.getPreferredSize().height));

        commentPanel.add(userInfoPanel, BorderLayout.NORTH);
        commentPanel.add(commentTextArea, BorderLayout.CENTER);

        mainPanel.add(commentPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void displayComments() {
        mainPanel.removeAll();
        List<Comments> comments = commentController.getComments();
        for (Comments comment : comments) {
            addCommentPanel(comment);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CommentView().setVisible(true);
            }
        });
    }
}
