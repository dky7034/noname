package com.kostagram.view;

import com.kostagram.controller.MyPageController;
import com.kostagram.controller.SearchController;
import com.kostagram.model.MyPageDao;
import com.kostagram.model.Posts;
import com.kostagram.model.SearchDao;
import com.kostagram.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class MyPageView extends JFrame{
    private JPanel userNamePanel;
    private JPanel mainPanel;
    private JPanel userInfoPanel;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;
    private BottomPanel bottomPanel; // 화면 하단 패널(버튼 4개)
    private Font font = new Font("맑은 고딕", Font.BOLD, 16);
    private Users userInfo;

    public MyPageView(Users userInfo) {
        this.userInfo = userInfo;
        MyPageDao myPageDao = MyPageDao.getInstance(); //MypageDao 사용하기 위해서 인스턴스 가져오기
        String email = userInfo.getEmail();
//        System.out.println(email);
        // JFrame 기본 설정
        setTitle("MyPage"); // 창 제목 설정
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫을 때 종료 설정
        setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        setBackground(Color.black); // 배경 색상 설정
        setResizable(false); // 사이즈 조절 불가 설정

        // <하단> => bottomPanel 호출(하단 4개 버튼)
        bottomPanel = new BottomPanel();

        //useremail 나오는 패널
        userNamePanel = new JPanel();
        userNamePanel.setBackground(new Color(0,0,0));
        userNamePanel.setPreferredSize(new Dimension(450,100));
        //UserEmail 출력
        JLabel userEmail = new JLabel(email);
        userEmail.setFont(font);
        userEmail.setForeground(Color.WHITE);
        System.out.println(userInfo.getEmail());
        userNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userNamePanel.add(userEmail);

        //userInfo 패널
        userInfoPanel = new JPanel();
        userInfoPanel.setBackground(new Color(0,0,0));
        userInfoPanel.setLayout(new GridLayout(2,2));
        userInfoPanel.setPreferredSize(new Dimension(450,100));
        //User에 따른 total 포스트 개수
        JLabel totalPosts = new JLabel("Posts");
        JLabel intTP = new JLabel(String.valueOf(myPageDao.getTotalPostsByEmail(email)));
        totalPosts.setLayout(new FlowLayout(FlowLayout.CENTER));
        totalPosts.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 , 10));
        totalPosts.setForeground(Color.WHITE);
        totalPosts.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        intTP.setLayout(new FlowLayout(FlowLayout.CENTER));
        intTP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 , 10));
        intTP.setForeground(Color.WHITE);
        intTP.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        //User에 따른 total Likes 개수
        JLabel totalLikes = new JLabel("Likes");
        JLabel intTL = new JLabel(String.valueOf(myPageDao.getTotalLikesByEmail(email)));
        totalLikes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 , 10));
        totalLikes.setForeground(Color.WHITE);
        totalLikes.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        intTL.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 , 10));
        intTL.setForeground(Color.WHITE);
        intTL.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        userInfoPanel.add(totalPosts);
        userInfoPanel.add(totalLikes);
        userInfoPanel.add(intTP);
        userInfoPanel.add(intTL);

        
        // 메인 패널 설정 (메인 패널 안에 UserInfoPanel + Scrollpane(resultPanel)) 포함
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // BorderLayout 사용
        mainPanel.setBackground(new Color(0,0,0)); // 메인 패널 배경 색상 설정

//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5); // 여백 설정

        // 검색 결과 표시 패널 설정
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3열 그리드 레이아웃, 간격 설정
        resultsPanel.setBackground(Color.black); // 결과 패널 배경 색상 설정
        //email로 post가져오기
        List<Posts> postsList = myPageDao.searchPostsByUserEmail(userInfo.getEmail());
        displayPosts(postsList);

        // JScrollPane을 사용하여 결과 패널을 스크롤 가능하게 설정
        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤바 필요 시 표시 설정
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤바는 절대 표시하지 않음
        scrollPane.getViewport().setBackground(Color.black); // 스크롤뷰포트 배경 색상 설정
        scrollPane.setBackground(Color.black); // 스크롤패널 배경 색상 설정

        // 메인 패널에 구성 요소 추가
        mainPanel.add(userInfoPanel, BorderLayout.NORTH); // 검색 패널을 메인 패널의 북쪽에 추가
        mainPanel.add(scrollPane, BorderLayout.CENTER); // 스크롤 패널을 메인 패널의 중앙에 추가

        // JFrame에 메인 패널 추가
        add(userNamePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH); // JFrame에 하단 패널 추가


    }

    private void showPostDetail(Posts post) {
        EventQueue.invokeLater(() -> {
            PostDetailView postDetailView = new PostDetailView(post.getPostId(), userInfo);
            postDetailView.setVisible(true);
        });
    }
    //post 뿌려주기
    public void displayPosts(List<Posts> posts) {
        resultsPanel.removeAll();
        if (posts.isEmpty()) {
            JLabel noResultsLabel = new JLabel("아직 포스트가 없습니다.");
            noResultsLabel.setForeground(Color.WHITE);
            resultsPanel.add(noResultsLabel);
        } else {
            for (Posts post : posts) {
                JPanel postPanel = createPostPanel(post);
                resultsPanel.add(postPanel);
                postPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        showPostDetail(post);
                    }
                });
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createPostPanel(Posts post) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BorderLayout());
        postPanel.setBackground(Color.white); // 패널 배경색 설정
        postPanel.setPreferredSize(new Dimension(120, 120)); // 패널 크기 고정
        postPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1)); // 테두리 설정

        JLabel postIdLabel = new JLabel("게시물 ID: " + post.getPostId());
        postIdLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        postIdLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea postContentArea = new JTextArea(post.getPostContent());
        postContentArea.setEditable(false); // 편집 불가능하도록 설정
        postContentArea.setLineWrap(true); // 텍스트 영역이 행 넘침 시 자동 줄 바꿈 설정
        postContentArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈 설정
        postContentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(postContentArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        postPanel.add(postIdLabel, BorderLayout.NORTH);
        postPanel.add(scrollPane, BorderLayout.CENTER);

        // 패널 클릭 시 상세 페이지로 이동하는 리스너 추가(중복됨)
//        postPanel.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                showPostDetail(post);
//            }
//        });

        return postPanel;
    }



    /*
        화면 하단에 공통으로 들어가는 버튼들 (홈, 검색, 게시물 작성, 유저페이지)
        액션리스너 추가
     */
    public void addHomeButtonListener(ActionListener listener) {
        bottomPanel.addHomeButtonListener(listener);
    }

    public void addSearchBtnListener(ActionListener listener) {
        bottomPanel.addSearchButtonListener(listener); // 하단 패널에 검색 버튼 리스너를 추가하는 메서드를 호출합니다.
    }

    public void addAddButtonListener(ActionListener listener) {
        bottomPanel.addAddButtonListener(listener);
    }

    public void addUserButtonListener(ActionListener listener) {
        bottomPanel.addUserButtonListener(listener);
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            Users userInfo = new Users("E9D3B1CB301F462CA67BB245415084B8", "user2@example.com",
//                    "password2",null);
//            MyPageView frame = new MyPageView(userInfo); // SearchView 인스턴스 생성
//            MyPageDao myPageDao = MyPageDao.getInstance();
//            MyPageController myPageController = new MyPageController(frame,myPageDao);
//            frame.setVisible(true); // 창을 보이게 설정
//        });
//    }
}
