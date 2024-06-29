package com.kostagram.view;

import com.kostagram.controller.SearchController;
import com.kostagram.model.Posts;
import com.kostagram.model.SearchDao;
import com.kostagram.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class SearchView extends JFrame {
    private RoundJTextField searchField = new RoundJTextField(20, Color.gray, Color.gray, "검색");
    private JPanel resultsPanel;
    private JPanel searchPanel;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JButton searchButton; // 검색페이지에서 사용하는 검색버튼
    private BottomPanel bottomPanel; // 화면 하단 패널(버튼 4개)
    private Font font = new Font("맑은 고딕", Font.BOLD, 16);
    private Users userInfo = new Users();

    public SearchView() {
        // JFrame 기본 설정
        setTitle("Search"); // 창 제목 설정
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫을 때 종료 설정
        setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        setBackground(Color.black); // 배경 색상 설정
        setResizable(false); // 사이즈 조절 불가 설정

        // <하단> => bottomPanel 호출(하단 4개 버튼)
        bottomPanel = new BottomPanel();

        // <상단>
        // 메인 패널 설정
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // BorderLayout 사용
        mainPanel.setBackground(Color.black); // 메인 패널 배경 색상 설정

        // 검색 패널 설정
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout()); // GridBagLayout 사용
        searchPanel.setBackground(Color.black); // 검색 패널 배경 색상 설정

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 여백 설정

        // 검색 버튼을 돋보기 이미지로 대체
        searchButton = new JButton();
        searchButton.setPreferredSize(new Dimension(45, 45)); // 버튼 크기 설정
        ImageIcon searchIcon = new ImageIcon("image/search_icon_03.png"); // 아이콘 설정
        searchButton.setIcon(searchIcon); // 버튼에 아이콘 추가
        searchButton.setBackground(Color.black); // 버튼 배경 색상 설정
        searchButton.setBorderPainted(false); // 버튼 테두리 제거

        // 검색 버튼을 GridBagLayout의 0, 0 위치에 추가
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(searchButton, gbc);

        // 검색 필드 추가
        searchField.setFont(font);
        searchField.setPreferredSize(new Dimension(200, 30)); // 검색 필드 크기 조절
        searchField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // 내부 여백 설정
        searchField.setBackground(Color.gray); // 검색 필드 배경 색상 설정
        searchField.setForeground(Color.LIGHT_GRAY); // 검색 필드 글자 색상 설정
        searchField.setCaretColor(Color.LIGHT_GRAY); // 검색 필드 커서 색상 설정

        // 검색 필드에 FocusListener 추가
        searchField.addFocusListener(new FocusListener() {
            // 포커스가 되면 필드에 "검색" 글자 사라짐
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("검색")) {
                    searchField.setText("");
                    searchField.setForeground(Color.LIGHT_GRAY); // 입력 글자색 지정
                }
            }
            // 포커스가 사라지면 필드에 "검색" 글자 추가
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("검색");
                    searchField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        // 검색 필드를 GridBagLayout의 1, 0 위치에 추가
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(searchField, gbc);

        // 검색 결과 표시 패널 설정
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3열 그리드 레이아웃, 간격 설정
        resultsPanel.setBackground(Color.black); // 결과 패널 배경 색상 설정

        // JScrollPane을 사용하여 결과 패널을 스크롤 가능하게 설정
        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤바 필요 시 표시 설정
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤바는 절대 표시하지 않음
        scrollPane.getViewport().setBackground(Color.black); // 스크롤뷰포트 배경 색상 설정
        scrollPane.setBackground(Color.black); // 스크롤패널 배경 색상 설정

        // 메인 패널에 구성 요소 추가
        mainPanel.add(searchPanel, BorderLayout.NORTH); // 검색 패널을 메인 패널의 북쪽에 추가
        mainPanel.add(scrollPane, BorderLayout.CENTER); // 스크롤 패널을 메인 패널의 중앙에 추가

        // JFrame에 메인 패널 추가
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH); // JFrame에 하단 패널 추가

        // 검색 버튼에 ActionListener 추가
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            if (!searchText.isEmpty() && !searchText.equals("검색")) {
                // SearchDao를 사용하여 검색 수행
                SearchDao searchDao = SearchDao.getInstance();
                List<Posts> searchResults = searchDao.searchPosts(searchText);

                // 검색 결과를 화면에 표시
                displayPosts(searchResults);
            }
        });
    }

    // 검색 기능 관련 코드
    public String getSearchText() {
        return searchField.getText();
    }

    public void displayPosts(List<Posts> posts) {
        resultsPanel.removeAll();
        if (posts.isEmpty()) {
            JLabel noResultsLabel = new JLabel("검색 결과가 없습니다.");
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


    private void showPostDetail(Posts post) {
        EventQueue.invokeLater(() -> {
            PostDetailView postDetailView = new PostDetailView(post.getPostId(), userInfo);
            postDetailView.setVisible(true);
        });
    }

    private JPanel createPostPanel(Posts post) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BorderLayout());
        postPanel.setBackground(Color.white); // 패널 배경색 설정
        postPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1)); // 테두리 설정

        JLabel postIdLabel = new JLabel("게시물 ID: " + post.getPostId());
        postIdLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        postIdLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea postContentArea = new JTextArea(post.getPostContent());
        postContentArea.setEditable(false); // 편집 불가능하도록 설정
        postContentArea.setLineWrap(true); // 텍스트 영역이 행 넘침 시 자동 줄 바꿈 설정
        postContentArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈 설정
        postContentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        postPanel.add(postIdLabel, BorderLayout.NORTH);
        postPanel.add(new JScrollPane(postContentArea), BorderLayout.CENTER);

        // 패널 크기를 정사각형으로 설정
        Dimension panelSize = new Dimension(120, 120); // 가로와 세로를 동일하게 설정
        postPanel.setPreferredSize(panelSize);
        postPanel.setMaximumSize(panelSize);
        postPanel.setMinimumSize(panelSize);

        return postPanel;
    }


    /*
        화면 하단에 공통으로 들어가는 버튼들 (홈, 검색, 게시물 작성, 유저페이지)
        액션리스너 추가
     */
    public void addHomeButtonListener(ActionListener listener) {
        bottomPanel.addHomeButtonListener(listener);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        bottomPanel.addAddButtonListener(listener);
    }

    public void addUserButtonListener(ActionListener listener) {
        bottomPanel.addUserButtonListener(listener);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SearchView frame = new SearchView(); // SearchView 인스턴스 생성
            SearchDao searchDao = SearchDao.getInstance();
            SearchController controller = new SearchController(frame, searchDao);
            frame.setVisible(true); // 창을 보이게 설정
        });
    }
}
