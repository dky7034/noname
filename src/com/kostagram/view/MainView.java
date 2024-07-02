package com.kostagram.view;

import com.kostagram.controller.*;
import com.kostagram.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainView extends JFrame {
    private JPanel postsPanel; // 포스트들을 담는 패널
    private JScrollPane scrollPane; // 패널을 스크롤할 수 있도록 하는 JScrollPane
    protected static final Font kostagramFont = new Font("맑은 고딕", Font.BOLD, 20);
    protected static final Font titleFont = new Font("맑은 고딕", Font.BOLD, 16); // 글꼴 설정
    protected static final Font contentFont = new Font("맑은 고딕", Font.PLAIN, 14); // 내용 글꼴 설정
    protected static final Color bgColor = Color.BLACK; // 배경 색 설정
    protected static final Color fgColor = Color.WHITE; // 글자 색 설정

    private BottomPanel bottomPanel; // BottomPanel 클래스 사용
    private PostDao postDao; // 데이터베이스 접근 객체(Posts)
    private Users user; // 현재 로그인한 사용자

    public MainView() {
        setTitle("Main"); // 프레임 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료 시 동작 설정
        setSize(475, 950); // 프레임 크기 설정
        setResizable(false); // 프레임 크기 조절 불가 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 위치시킴
        user = LoginController.users; // 로그인된 사용자 정보 가져오기
        postDao = PostDao.getInstance(); // PostDao 인스턴스 초기화

        // 상단 패널
        JPanel topPanel = new JPanel(); // 상단 패널 생성
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // 상단 패널 레이아웃 설정
        topPanel.setBackground(bgColor); // 상단 패널 배경 색 설정
        topPanel.setPreferredSize(new Dimension(470, 45)); // 상단 패널 크기 설정
        JLabel titleLabel = new JLabel(" Kostagram"); // 제목 라벨 생성
        titleLabel.setFont(kostagramFont); // 제목 라벨 글꼴 설정
        titleLabel.setForeground(Color.white); // 제목 라벨 글자 색 설정
        topPanel.add(titleLabel); // 상단 패널에 제목 라벨 추가
        topPanel.add(Box.createHorizontalGlue()); // 상단 패널에 수평 글루 추가 (라벨 오른쪽 정렬)

        // 포스트 패널 및 스크롤 페인
        postsPanel = new JPanel(); // 포스트 패널 초기화
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS)); // 포스트 패널 레이아웃 설정
        postsPanel.setBackground(bgColor); // 포스트 패널 배경 색 설정
        scrollPane = new JScrollPane(postsPanel); // 포스트 패널을 담는 스크롤 페인 초기화
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar(); // 수직 스크롤 바 가져오기
        verticalScrollBar.setUI(new PrettyScrollBar()); // 스크롤 바 UI 커스터마이징
        scrollPane.setBackground(bgColor); // 스크롤 페인 배경 색 설정
        scrollPane.setBorder(null); // 스크롤 페인 경계선 제거
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 좌우 스크롤 막기

        // 하단 패널
        bottomPanel = new BottomPanel(); // 하단 패널 초기화

        // 초기 데이터 로드
        loadPost(); // 초기 데이터 로드 메서드 호출

        // 메인 패널에 추가
        JPanel mainPanel = new JPanel(); // 메인 패널 생성
        mainPanel.setLayout(new BorderLayout()); // 메인 패널 레이아웃 설정
        mainPanel.setBackground(bgColor); // 메인 패널 배경 색 설정
        mainPanel.add(topPanel, BorderLayout.NORTH); // 메인 패널에 상단 패널 추가
        mainPanel.add(scrollPane, BorderLayout.CENTER); // 메인 패널에 스크롤 페인 추가
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // 메인 패널에 하단 패널 추가
        setVisible(true); // 프레임을 보이도록 설정

        add(mainPanel); // 메인 패널을 프레임에 추가
    }

    public void setPosts(List<Posts> posts) {
        postsPanel.removeAll(); // 포스트 패널 초기화
        for (Posts post : posts) {
            postsPanel.add(createPostPanel(post)); // 포스트 패널에 포스트 추가
        }
        postsPanel.revalidate(); // 포스트 패널 갱신
        postsPanel.repaint(); // 포스트 패널 다시 그리기
    }

    private void loadPost() {
        SwingUtilities.invokeLater(() -> {
            List<Posts> posts = postDao.getPosts(); // 데이터베이스에서 실제 데이터를 가져옴
            if (posts != null && !posts.isEmpty()) {
                setPosts(posts); // 포스트 설정
            }
        });
    }

    private JPanel createPostPanel(Posts post) {
        JPanel panel = new JPanel(); // 패널 생성
        panel.setLayout(new GridBagLayout()); // 패널 레이아웃 설정
        GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints 객체 생성
        panel.setBackground(bgColor); // 패널 배경 색 설정
        panel.setSize(440, 150);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 패널 경계선 설정

        JLabel userLabel = new JLabel(post.getUserName()); // 사용자 이름 라벨 생성
        userLabel.setFont(titleFont); // 사용자 이름 라벨 글꼴 설정
        userLabel.setForeground(fgColor); // 사용자 이름 라벨 글자 색 설정
        userLabel.setPreferredSize(new Dimension(panel.getWidth(), 40)); // 사용자 이름 라벨 크기 설정

        JTextArea contentTextArea = new JTextArea(5, 10); // 게시글내용 라벨 생성
        contentTextArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈 활성화
        contentTextArea.setLineWrap(true); // 텍스트 영역이 행 넘침 시 자동 줄 바꿈 설정
        contentTextArea.setPreferredSize(new Dimension(panel.getWidth(), 30)); // 게시글내용 라벨 크기 설정
        contentTextArea.setForeground(fgColor); // 게시글내용 라벨 글자 색 설정
        contentTextArea.setBackground(bgColor);
        contentTextArea.setFont(contentFont);
        contentTextArea.append(post.getPostContent());
        contentTextArea.repaint();// 게시글 내용을 수정할 수 없도록 설정

        // 좋아요 및 댓글 수
        JButton likeButton = createIconButton("like"); // 좋아요 버튼 생성
        JButton commentButton = createIconButton("comment"); // 댓글 버튼 생성

        // 좋아요 버튼에 액션 리스너 추가
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Liked post: " + post.getPostId());
            }
        });

        // 댓글 버튼에 액션 리스너 추가
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentView commentView = new CommentView(post.getPostId()); // 댓글 보기 창 열기
                commentView.setVisible(true);
            }
        });

        // 좋아요 라벨
        JLabel likeLabel = new JLabel(); // 좋아요 라벨 생성
        likeLabel.setFont(contentFont); // 좋아요 라벨 글꼴 설정
        likeLabel.setText(post.getLikesCount() + "명이 좋아합니다."); // 좋아요 라벨 텍스트 설정
        likeLabel.setForeground(fgColor); // 좋아요 라벨 글자 색 설정
        likeLabel.setPreferredSize(new Dimension(panel.getWidth(), 20)); // 좋아요 라벨 크기 설정

        // 댓글 수 라벨
        JLabel commentCountLabel = new JLabel("댓글 " + post.getCommentsCount() + "개"); // 댓글 수 라벨 생성
        commentCountLabel.setFont(contentFont); // 댓글 수 라벨 글꼴 설정
        commentCountLabel.setForeground(fgColor); // 댓글 수 라벨 글자 색 설정
        commentCountLabel.setPreferredSize(new Dimension(panel.getWidth(), 20)); // 댓글 수 라벨 크기 설정
        commentCountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 댓글 수 라벨 커서 설정

        gbc.insets = new Insets(0, 0, 5, 0); // 여백 설정
        gbc.gridx = 0; // 그리드 x 위치 설정
        gbc.gridy = 0; // 그리드 y 위치 설정
        gbc.gridwidth = 2; // 그리드 폭 설정
        gbc.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
        panel.add(userLabel, gbc); // 패널에 사용자 이름 라벨 추가

        gbc.gridy++; // 그리드 y 위치 설정
        gbc.gridwidth = 2; // 그리드 폭 설정
        gbc.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
        panel.add(contentTextArea, gbc); // 패널에 게시글 내용 텍스트 영역 추가

        gbc.gridy++; // 그리드 y 위치 설정
        gbc.gridwidth = 1; // 그리드 폭 설정
        gbc.fill = GridBagConstraints.NONE; // 크기 조절 설정
        gbc.anchor = GridBagConstraints.WEST; // 앵커 설정
        panel.add(likeButton, gbc); // 패널에 좋아요 버튼 추가
        gbc.gridx++; // 그리드 x 위치 설정
        gbc.anchor = GridBagConstraints.WEST; // 앵커 설정
        panel.add(commentButton, gbc); // 패널에 댓글 버튼 추가
        gbc.gridx++; // 그리드 x 위치 설정
        gbc.weightx = 1.0; // 가중치 설정
        gbc.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
        panel.add(new JLabel(), gbc); // 빈 라벨 추가

        gbc.gridx = 0; // 그리드 x 위치 설정
        gbc.gridy++; // 그리드 y 위치 설정
        gbc.gridwidth = 2; // 그리드 폭 설정
        gbc.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
        panel.add(likeLabel, gbc); // 패널에 좋아요 라벨 추가

        gbc.gridy++; // 그리드 y 위치 설정
        gbc.gridwidth = 2; // 그리드 폭 설정
        gbc.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
        panel.add(commentCountLabel, gbc); // 패널에 댓글 수 라벨 추가

        return panel; // 패널 반환
    }

    public static JButton createIconButton(String name) {
        // 이미지를 로드합니다.
        String defaultIconPath = "./src/com/kostagram/icon/"; // 아이콘 파일의 기본 경로를 설정합니다.
        ImageIcon icon = new ImageIcon(defaultIconPath + name + ".png"); // 아이콘 파일을 로드하여 ImageIcon 객체를 생성합니다.
        // 이미지 아이콘을 사용하여 버튼을 생성합니다.
        JButton button = new JButton(icon); // 아이콘을 버튼에 설정하여 JButton 객체를 생성합니다.
        button.setPreferredSize(new Dimension(36, 36)); // 버튼의 선호 크기를 설정합니다.
        // 투명화
        button.setOpaque(false); // 버튼의 불투명 속성을 false로 설정합니다.
        button.setContentAreaFilled(false); // 버튼의 내용 영역 채우기를 비활성화합니다.
        button.setBorderPainted(false); // 버튼의 경계선 그리기를 비활성화합니다.
        return button; // 버튼 객체를 반환합니다.
    }

    public void addHomeBtnListener(ActionListener listener) {
        bottomPanel.addHomeButtonListener(listener); // 하단 패널에 홈 버튼 리스너를 추가하는 메서드를 호출합니다.
    }

    public void addSearchBtnListener(ActionListener listener) {
        bottomPanel.addSearchButtonListener(listener); // 하단 패널에 검색 버튼 리스너를 추가하는 메서드를 호출합니다.
    }

    public void addAddBtnListener(ActionListener listener) {
        bottomPanel.addAddButtonListener(listener); // 하단 패널에 추가 버튼 리스너를 추가하는 메서드를 호출합니다.
    }

    public void addUserBtnListener(ActionListener listener) {
        bottomPanel.addUserButtonListener(listener); // 하단 패널에 사용자 버튼 리스너를 추가하는 메서드를 호출합니다.
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Test::new); // Swing 애플리케이션을 Event Dispatch Thread에서 실행하여 MainView를 생성합니다.
//    }
}