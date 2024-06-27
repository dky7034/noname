package com.kostagram.view;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {
    private DefaultListModel<Post> listModel;
    private JList<Post> list;
    private JScrollPane scrollPane;
    private JPanel postsPanel;
    private boolean loading = false;
    protected static final Font font = new Font("맑은 고딕",Font.BOLD,12);
    protected static final Color bgColor = Color.BLACK;
    protected static final Color fgColor = Color.WHITE;
    public Test() {
        setTitle("Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470, 950);
        setResizable(false);
        setLocationRelativeTo(null);

        // 상단 패널
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(bgColor);
        topPanel.setPreferredSize(new Dimension(450, 55));
        JLabel titleLabel = new JLabel(" Kostagram");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.white);
        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());

        // 리스트 모델 및 리스트
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new PostRenderer());


        // 스크롤 페인
        scrollPane = new JScrollPane(list);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new PrettyScrollBar());
        scrollPane.setBackground(bgColor);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (!loading && !e.getValueIsAdjusting() &&
                    (e.getAdjustable().getMaximum() - e.getAdjustable().getVisibleAmount() - e.getAdjustable().getValue() < 10)) {
                loadMoreData();
            }
        });

        //하단 패널
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        botPanel.setBackground(bgColor);
        botPanel.setPreferredSize(new Dimension(450, 55));
        // 이미지 아이콘을 사용하여 버튼을 생성합니다.
        JButton homeButton = createIconButton("home");
        JButton searchButton = createIconButton("search");
        JButton addButton = createIconButton("add");
        JButton userButton = createIconButton("user");
        // 위치 설정
        gbc.insets = new Insets(10, 10, 10, 10); // 간격 설정
        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(homeButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        botPanel.add(searchButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        botPanel.add(addButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        botPanel.add(userButton, gbc);
        botPanel.add(Box.createHorizontalGlue());

        // 초기 데이터 로드
        loadMoreData();

        // 메인 패널에 추가
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(botPanel, BorderLayout.SOUTH);
        setVisible(true);

        add(mainPanel);
    }

    private void loadMoreData() {
        loading = true;
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 20; i++) {
                listModel.addElement(new Post(
                        "User " + (listModel.getSize() + 1),
                        "This is a post content " + (listModel.getSize() + 1),
                        "http://example.com/image.jpg",
                        (int) (Math.random() * 100),
                        (int) (Math.random() * 20)
                ));
            }
            loading = false;
        });
    }

    private static class PostRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Post post = (Post) value;
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            panel.setBackground(bgColor);
            panel.setPreferredSize(new Dimension(450, 670));
            panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));

            // 사용자 이름
            JLabel userLabel = new JLabel(post.getUsername());
            userLabel.setFont(font);
            userLabel.setForeground(fgColor);
            userLabel.setPreferredSize(new Dimension(450, 30));

            // 게시물 이미지
            String defaultImagePath = "./src/com/kostagram/image/";
            JLabel imageLabel = new JLabel(new ImageIcon(defaultImagePath+"test.jpg"));
            imageLabel.setPreferredSize(new Dimension(450, 450));

            // 좋아요 및 댓글 수
            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridBagLayout());
            GridBagConstraints gbc1 = new GridBagConstraints();
            panel1.setBackground(bgColor);
            panel1.setPreferredSize(new Dimension(450, 55));
            JButton likeButton = createIconButton("like_false");
            JButton commentButton = createIconButton("comment");

            // 위치 설정
            gbc1.insets = new Insets(0, 5, 0, 0);
            gbc1.gridx = 0;gbc1.gridy = 0;
            gbc1.fill = GridBagConstraints.NONE;
            gbc1.anchor = GridBagConstraints.WEST;
            panel1.add(likeButton, gbc1);
            gbc1.gridx = 1;gbc1.gridy = 0;
            panel1.add(commentButton, gbc1);
            gbc1.gridx = 2;gbc1.gridy = 0; // 첫 번째 행
            gbc1.weightx = 1.0; // 남은 공간을 채우기 위해 가중치 설정
            gbc1.fill = GridBagConstraints.HORIZONTAL;
            panel1.add(new JLabel(), gbc1);

            JLabel interactionLabel = new JLabel();
            interactionLabel.setFont(font);
            interactionLabel.setText("  "+post.getLikes() + "명이 좋아합니다." + "   " + post.getComments()+"개의 댓글");
            interactionLabel.setForeground(fgColor);
            interactionLabel.setPreferredSize(new Dimension(450, 50));

            // 게시물 내용
            JLabel contentLabel = new JLabel("<html><b>     "+post.getUsername()+"</b>   The soy sauce needs to be pre-boiled and completely.</html>");
            contentLabel.setFont(font);
            contentLabel.setForeground(fgColor);
            contentLabel.setPreferredSize(new Dimension(450, 100));

            // 위치 설정
            gbc.insets = new Insets(5, 5, 5, 5); // 간격 설정
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(userLabel, gbc);
            gbc.gridx = 0;gbc.gridy = 1;
            panel.add(imageLabel, gbc);
            gbc.gridx = 0;gbc.gridy = 2;
            panel.add(panel1, gbc);
            gbc.gridx = 0;gbc.gridy = 3;
            panel.add(interactionLabel, gbc);
            gbc.gridx = 0;gbc.gridy = 4;
            panel.add(contentLabel, gbc);

            return panel;
        }
    }
    public static JButton createIconButton(String name) {
        // 이미지를 로드합니다.
        String defaultIconPath = "./src/com/kostagram/icon/";
        ImageIcon icon = new ImageIcon(defaultIconPath+name+".png");
        // 이미지 아이콘을 사용하여 버튼을 생성합니다.
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(36, 36));
        // 투명화
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }
    
    private static class Post {
        private String username;
        private String content;
        private String imageUrl;
        private int likes;
        private int comments;

        public Post(String username, String content, String imageUrl, int likes, int comments) {
            this.username = username;
            this.content = content;
            this.imageUrl = imageUrl;
            this.likes = likes;
            this.comments = comments;
        }

        public String getUsername() {
            return username;
        }

        public String getContent() {
            return content;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getLikes() {
            return likes;
        }

        public int getComments() {
            return comments;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }
}
