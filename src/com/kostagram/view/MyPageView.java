package com.kostagram.view;

import com.kostagram.model.MyPageDao;
import com.kostagram.model.Posts;
import com.kostagram.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MyPageView extends JFrame {
    private JPanel postPanel; // 게시물들을 담는 패널
    private JScrollPane scrollPane; // 리스트를 스크롤할 수 있도록 하는 JScrollPane
    private Users user; // 현재 로그인한 사용자

    private JLabel emailLabel;
    private JLabel postCountLabel;
    private JLabel commentCountLabel;
    private BottomPanel bottomPanel; // 하단 패널

    // 생성자
    public MyPageView(Users user) {
        this.user = user; // 로그인된 사용자 정보 설정

        setTitle("My Page");
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정
        setLocationRelativeTo(null); // 화면 중앙에 창 배치
        setResizable(false); // 창 크기 조절 불가

        // 상단 패널 설정
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(475, 100));

        // 이메일 라벨
        emailLabel = new JLabel(user.getEmail());
        emailLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 게시물 및 댓글 수 패널
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new FlowLayout());
        countPanel.setBackground(Color.BLACK);

        postCountLabel = new JLabel("게시물 작성 수: 0");
        postCountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        postCountLabel.setForeground(Color.WHITE);

        commentCountLabel = new JLabel("댓글 작성 수: 0");
        commentCountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        commentCountLabel.setForeground(Color.WHITE);

        countPanel.add(postCountLabel);
        countPanel.add(Box.createRigidArea(new Dimension(20, 0))); // 공간 추가
        countPanel.add(commentCountLabel);

        topPanel.add(emailLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 공간 추가
        topPanel.add(countPanel);

        // 게시물 패널 설정
        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.BLACK);

        // 스크롤 페인 설정
        scrollPane = new JScrollPane(postPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setBorder(null);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new PrettyScrollBar());

        // 초기 데이터 로드
        loadPosts();

        // 하단 패널 설정
        bottomPanel = new BottomPanel();

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public Users getUser() {
        return user;
    }

    public void addHomeButtonListener(ActionListener listener) {
        bottomPanel.addHomeButtonListener(listener);
    }

    public void addSearchBtnListener(ActionListener listener) {
        bottomPanel.addSearchButtonListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        bottomPanel.addAddButtonListener(listener);
    }

    public void addUserButtonListener(ActionListener listener) {
        bottomPanel.addUserButtonListener(listener);
    }

    // 게시물 로드 메서드
    private void loadPosts() {
        SwingUtilities.invokeLater(() -> {
            MyPageDao myPageDao = MyPageDao.getInstance();
            List<Posts> posts = myPageDao.searchPostsByUserId(user.getUserId());
            for (Posts post : posts) {
                addPostToPanel(post);
            }
            updateCounts(posts.size(), calculateCommentCount(posts));
        });
    }

    // 게시물을 패널에 추가하는 메서드
    private void addPostToPanel(Posts post) {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // 간격 조정
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextArea contentArea = new JTextArea(post.getPostContent());
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentArea.setForeground(Color.WHITE);
        contentArea.setBackground(Color.BLACK);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBorder(null);
        contentArea.setPreferredSize(new Dimension(400, 60)); // 크기 설정

        JLabel infoLabel = new JLabel("작성자: " + post.getUserName() + " | 댓글: " + post.getCommentsCount() + " | 좋아요: " + post.getLikesCount());
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(contentArea, BorderLayout.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 더블 클릭 시
                    showPostDetail(post);
                }
            }
        });

        postPanel.add(panel);
        postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 패널 간의 간격 추가
        postPanel.revalidate();
        postPanel.repaint();
    }

    // 댓글 수 계산 메서드
    private int calculateCommentCount(List<Posts> posts) {
        return posts.stream().mapToInt(Posts::getCommentsCount).sum();
    }

    // 게시물 및 댓글 수 업데이트 메서드
    private void updateCounts(int postCount, int commentCount) {
        postCountLabel.setText("게시물 작성 수: " + postCount);
        commentCountLabel.setText("댓글 작성 수: " + commentCount);
    }

    // 게시물 상세 페이지 표시 메서드
    private void showPostDetail(Posts post) {
        EventQueue.invokeLater(() -> {
            PostDetailView postDetailView = new PostDetailView(post, user);
            postDetailView.setVisible(true);
        });
    }

    public static JButton createIconButton(String name) {
        String defaultIconPath = "./src/com/kostagram/icon/";
        ImageIcon icon = new ImageIcon(defaultIconPath + name + ".png");
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(36, 36));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }
}
