package com.kostagram.view;

import com.kostagram.controller.*;
import com.kostagram.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainView extends JFrame {
    private DefaultListModel<Posts> listModel; // 포스트들을 담는 리스트 모델
    private JList<Posts> list; // 포스트들을 보여줄 JList
    private JScrollPane scrollPane; // 리스트를 스크롤할 수 있도록 하는 JScrollPane
    private boolean loading = false; // 데이터 로딩 중 여부를 나타내는 플래그
    private boolean allDataLoaded = false; // 추가 데이터가 더 이상 없는지 확인하기 위한 변수
    protected static final Font kostagramFont = new Font("맑은 고딕", Font.BOLD, 20);
    protected static final Font font = new Font("맑은 고딕", Font.BOLD, 16); // 글꼴 설정
    protected static final Font contentFont = new Font("맑은 고딕", Font.BOLD, 14); // 내용 글꼴 설정
    protected static final Color bgColor = Color.BLACK; // 배경 색 설정
    protected static final Color fgColor = Color.WHITE; // 글자 색 설정

    private BottomPanel bottomPanel; // BottomPanel 클래스 사용
    private PostDao postDao; // 데이터베이스 접근 객체(Posts)
    public static Users user; // 현재 로그인한 사용자

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
        topPanel.setPreferredSize(new Dimension(450, 45)); // 상단 패널 크기 설정
        JLabel titleLabel = new JLabel(" Kostagram"); // 제목 라벨 생성
        titleLabel.setFont(kostagramFont); // 제목 라벨 글꼴 설정
        titleLabel.setForeground(Color.white); // 제목 라벨 글자 색 설정
        topPanel.add(titleLabel); // 상단 패널에 제목 라벨 추가
        topPanel.add(Box.createHorizontalGlue()); // 상단 패널에 수평 글루 추가 (라벨 오른쪽 정렬)

        // 리스트 모델 및 리스트
        listModel = new DefaultListModel<>(); // 리스트 모델 초기화
        list = new JList<>(listModel); // 리스트 초기화
        list.setCellRenderer(new PostRenderer()); // 리스트의 렌더러 설정

        // 스크롤 페인
        scrollPane = new JScrollPane(list); // 리스트를 담는 스크롤 페인 초기화
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar(); // 수직 스크롤 바 가져오기
        verticalScrollBar.setUI(new PrettyScrollBar()); // 스크롤 바 UI 커스터마이징
        scrollPane.setBackground(bgColor); // 스크롤 페인 배경 색 설정
        scrollPane.setBorder(null); // 스크롤 페인 경계선 제거
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> { // 스크롤 이벤트 리스너 추가
            if (!loading && !e.getValueIsAdjusting() &&
                    (e.getAdjustable().getMaximum() - e.getAdjustable().getVisibleAmount() - e.getAdjustable().getValue() < 10)) {
                loadMoreData(); // 데이터 로드
            }
        });

        // 하단 패널
        bottomPanel = new BottomPanel(); // 하단 패널 초기화

        // 초기 데이터 로드
        loadInitialData(); // 초기 데이터 로드 메서드 호출

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
        listModel.clear(); // 리스트 모델 초기화
        for (Posts post : posts) {
            listModel.addElement(post); // 리스트 모델에 포스트 추가
        }
    }

    public DefaultListModel<Posts> getListModel() {
        return listModel; // 리스트 모델 반환
    }

    private void loadInitialData() {
        loading = true; // 데이터 로딩 중 설정
        SwingUtilities.invokeLater(() -> {
            List<Posts> posts = postDao.getPosts(); // 데이터베이스에서 실제 데이터를 가져옴
            if (posts != null && !posts.isEmpty()) {
                for (Posts post : posts) {
                    listModel.addElement(post); // 리스트 모델에 포스트 추가
                }
            } else {
                allDataLoaded = true; // 데이터가 더 이상 없을 경우 로드 중지
            }
            loading = false; // 데이터 로딩 완료 설정
        });
    }

    private void loadMoreData() {
        if (loading || allDataLoaded) {
            return; // 로딩 중이거나 모든 데이터가 로드된 경우 리턴
        }

        loading = true; // 데이터 로딩 중 설정
        SwingUtilities.invokeLater(() -> {
            // 추가 데이터 로드 로직
            List<Posts> posts = postDao.getPosts(); // 추가 데이터를 불러옴
            if (posts != null && !posts.isEmpty()) {
                for (Posts post : posts) {
                    listModel.addElement(post); // 리스트 모델에 포스트 추가
                }
            } else {
                allDataLoaded = true; // 데이터가 더 이상 없을 경우 로드 중지
            }
            loading = false; // 데이터 로딩 완료 설정
        });
    }

    private static class PostRenderer extends DefaultListCellRenderer { // 커스텀 리스트 셀 렌더러 클래스
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Posts post = (Posts) value; // 포스트 객체 가져오기
            JPanel panel = new JPanel(); // 패널 생성
            panel.setLayout(new GridBagLayout()); // 패널 레이아웃 설정
            GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints 객체 생성
            panel.setBackground(bgColor); // 패널 배경 색 설정
            panel.setPreferredSize(new Dimension(450, 670)); // 패널 크기 설정
            panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0)); // 패널 경계선 설정

            // 사용자 이름
            JLabel userLabel = new JLabel(post.getUserId()); // 사용자 이름 라벨 생성
            userLabel.setFont(font); // 사용자 이름 라벨 글꼴 설정
            userLabel.setForeground(fgColor); // 사용자 이름 라벨 글자 색 설정
            userLabel.setPreferredSize(new Dimension(450, 44)); // 사용자 이름 라벨 크기 설정

            // 게시물 ID (이미지 대체)
            JLabel postIdLabel = new JLabel(post.getPostId()); // 게시물 ID 라벨 생성
            postIdLabel.setFont(font); // 게시물 ID 라벨 글꼴 설정
            postIdLabel.setForeground(fgColor); // 게시물 ID 라벨 글자 색 설정
            postIdLabel.setPreferredSize(new Dimension(450, 450)); // 게시물 ID 라벨 크기 설정
            postIdLabel.setHorizontalAlignment(SwingConstants.CENTER); // 게시물 ID 라벨 수평 정렬 설정
            postIdLabel.setVerticalAlignment(SwingConstants.CENTER); // 게시물 ID 라벨 수직 정렬 설정
            postIdLabel.setOpaque(true); // 배경 색 설정 가능하도록 설정
            postIdLabel.setBackground(Color.DARK_GRAY); // 배경 색 설정

            // 좋아요 및 댓글 수
            JPanel panel1 = new JPanel(); // 좋아요 및 댓글 수 패널 생성
            panel1.setLayout(new GridBagLayout()); // 좋아요 및 댓글 수 패널 레이아웃 설정
            GridBagConstraints gbc1 = new GridBagConstraints(); // GridBagConstraints 객체 생성
            panel1.setBackground(bgColor); // 좋아요 및 댓글 수 패널 배경 색 설정
            panel1.setPreferredSize(new Dimension(450, 40)); // 좋아요 및 댓글 수 패널 크기 설정
            JButton likeButton = createIconButton("like_false"); // 좋아요 버튼 생성
            JButton commentButton = createIconButton("comment"); // 댓글 버튼 생성

            // 댓글 버튼에 액션 리스너 추가
            commentButton.addActionListener(e -> {
                CommentView commentView = new CommentView(); // 댓글 뷰 생성
                CommentController commentController = new CommentController(commentView, CommentDao.getInstance(), post.getPostId(), LoginController.users); // 댓글 컨트롤러 생성
                commentView.setVisible(true); // 댓글 뷰 보이기 설정
            });

            // 위치 설정
            gbc1.insets = new Insets(0, 0, 0, 0); // 여백 설정
            gbc1.gridx = 0; // 그리드 x 위치 설정
            gbc1.gridy = 0; // 그리드 y 위치 설정
            gbc1.fill = GridBagConstraints.NONE; // 크기 조절 설정
            gbc1.anchor = GridBagConstraints.WEST; // 앵커 설정
            panel1.add(likeButton, gbc1); // 좋아요 버튼 추가
            gbc1.gridx = 1; // 그리드 x 위치 설정
            gbc1.gridy = 0; // 그리드 y 위치 설정
            panel1.add(commentButton, gbc1); // 댓글 버튼 추가
            gbc1.gridx = 2; // 그리드 x 위치 설정
            gbc1.gridy = 0; // 그리드 y 위치 설정
            gbc1.weightx = 1.0; // 가중치 설정
            gbc1.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
            panel1.add(new JLabel(), gbc1); // 빈 라벨 추가

            // 좋아요 라벨
            JLabel likeLabel = new JLabel(); // 좋아요 라벨 생성
            likeLabel.setFont(contentFont); // 좋아요 라벨 글꼴 설정
            likeLabel.setText(post.getLikesCount() + "명이 좋아합니다."); // 좋아요 라벨 텍스트 설정
            likeLabel.setForeground(fgColor); // 좋아요 라벨 글자 색 설정
            likeLabel.setPreferredSize(new Dimension(450, 20)); // 좋아요 라벨 크기 설정

            // 댓글 수 라벨
            JLabel commentCountLabel = new JLabel("댓글 " + post.getCommentsCount() + "개"); // 댓글 수 라벨 생성
            commentCountLabel.setFont(contentFont); // 댓글 수 라벨 글꼴 설정
            commentCountLabel.setForeground(fgColor); // 댓글 수 라벨 글자 색 설정
            commentCountLabel.setPreferredSize(new Dimension(450, 20)); // 댓글 수 라벨 크기 설정
            commentCountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 댓글 수 라벨 커서 설정

            // 댓글 수 라벨에 액션 리스너 추가
            commentCountLabel.addMouseListener(new MouseAdapter() { // 마우스 리스너 추가
                public void mouseClicked(MouseEvent evt) {
                    CommentView commentView = new CommentView(); // 댓글 뷰 생성
                    CommentController commentController = new CommentController(commentView, CommentDao.getInstance(), post.getPostId(), LoginController.users); // 댓글 컨트롤러 생성
                    commentView.setVisible(true); // 댓글 뷰 보이기 설정
                }
            });

            // 게시물 내용 라벨
            JLabel contentLabel = new JLabel(post.getPostContent()); // 게시물 내용 라벨 생성
            contentLabel.setFont(contentFont); // 게시물 내용 라벨 글꼴 설정
            contentLabel.setForeground(fgColor); // 게시물 내용 라벨 글자 색 설정
            contentLabel.setPreferredSize(new Dimension(450, 20)); // 게시물 내용 라벨 크기 설정

            // 위치 설정
            gbc.insets = new Insets(0, 0, 0, 0); // 여백 설정
            gbc.gridx = 0; // 그리드 x 위치 설정
            gbc.gridy = 0; // 그리드 y 위치 설정
            gbc.fill = GridBagConstraints.HORIZONTAL; // 크기 조절 설정
            panel.add(userLabel, gbc); // 사용자 이름 라벨 추가
            gbc.gridy = 1; // 그리드 y 위치 설정
            panel.add(postIdLabel, gbc); // 게시물 ID 라벨 추가
            gbc.gridy = 2; // 그리드 y 위치 설정
            panel.add(panel1, gbc); // 좋아요 및 댓글 수 패널 추가
            gbc.gridy = 3; // 그리드 y 위치 설정
            panel.add(likeLabel, gbc); // 좋아요 라벨 추가
            gbc.gridy = 4; // 그리드 y 위치 설정
            panel.add(commentCountLabel, gbc); // 댓글 수 라벨 추가
            gbc.gridy = 5; // 그리드 y 위치 설정
            panel.add(contentLabel, gbc); // 게시물 내용 라벨 추가

            return panel; // 패널 반환
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new); // Swing 애플리케이션을 Event Dispatch Thread에서 실행하여 MainView를 생성합니다.
    }
}
