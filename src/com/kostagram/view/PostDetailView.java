package com.kostagram.view;

import com.kostagram.model.Posts;
import com.kostagram.model.Users;
import oracle.jdbc.proxy.annotation.Pre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Date;

public class PostDetailView extends JFrame {
    private JPanel mainPanel;
    private JLabel postIdLabel;
    private JLabel postContentLabel;
    private JLabel postCreateDateLabel;
    private JLabel postLikesCountLabel;

    public PostDetailView(Posts post, Users userInfo) {
        setTitle("게시물 상세 화면");
        setSize(475, 950); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setForeground(Color.WHITE);
        // mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 내부 여백 설정

        if (post != null) {
            postIdLabel = new JLabel(post.getUserName());
            postIdLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//            postContentLabel = new JLabel("게시물 내용: " + post.getPostContent());
//            postContentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            postCreateDateLabel = new JLabel("게시물 생성일: " + post.getCreateDate());
            postCreateDateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            postLikesCountLabel = new JLabel("좋아요 수: " + post.getLikesCount());
            postLikesCountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

            //topPanel 만들기
            postIdLabel.setForeground(Color.WHITE);
            JPanel topPanel = new JPanel();
            topPanel.setSize(mainPanel.getWidth(),100);
            topPanel.setBackground(Color.BLACK);
            topPanel.add(postIdLabel,BorderLayout.CENTER);
            topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

            //JtextArea 생성
            JTextArea contentTextArea = new JTextArea(); // 게시글내용 라벨 생성
            contentTextArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈 활성화
            contentTextArea.setLineWrap(true); // 텍스트 영역이 행 넘침 시 자동 줄 바꿈 설정
            contentTextArea.setSize(450,150);
//            contentTextArea.setPreferredSize(new Dimension(450, 150)); // 게시글내용 라벨 크기 설정
            contentTextArea.setForeground(Color.WHITE); // 게시글내용 라벨 글자 색 설정
            contentTextArea.setBackground(Color.BLACK);
            contentTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            contentTextArea.append(post.getPostContent());
            contentTextArea.repaint();

            // JScrollPane에 JTextArea 직접 추가
            JScrollPane scrollPane = new JScrollPane(contentTextArea);
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setUI(new PrettyScrollBar());
            scrollPane.setBackground(Color.BLACK);
            scrollPane.setBorder(null);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//            scrollPane.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

//            // 구분선 생성 및 색상 설정
//            JSeparator separator1 = new JSeparator(JSeparator.HORIZONTAL);
//            separator1.setForeground(Color.WHITE); // 구분선 색상 설정
//            JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
//            separator2.setForeground(Color.WHITE); // 구분선 색상 설정

            //bottomPanel 생성
            postLikesCountLabel.setForeground(Color.WHITE);
            postCreateDateLabel.setForeground(Color.WHITE);
            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.BLACK);
            bottomPanel.setForeground(Color.GRAY);
            bottomPanel.add(createLabelPanel(postLikesCountLabel));
            bottomPanel.add(createLabelPanel(postCreateDateLabel));
            // mainPanel에 컴포넌트 추가
            mainPanel.add(topPanel, BorderLayout.NORTH);
//            mainPanel.add(separator1, BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
//            mainPanel.add(separator2, BorderLayout.CENTER);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        } else {
            // 데이터베이스에서 해당 게시물을 찾지 못했을 때의 처리
            JLabel errorLabel = new JLabel("해당 게시물을 찾을 수 없습니다.");
            errorLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            mainPanel.add(errorLabel);
        }

        // JFrame에 메인 패널 추가
        add(mainPanel);
    }

    // 레이블을 감싸는 패널을 생성하는 메서드
    private JPanel createLabelPanel(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        label.setForeground(Color.WHITE);
        panel.setBackground(Color.BLACK);
        panel.add(label, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10)); // 내부 여백 설정
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // 실제 게시물 ID를 매개변수로 넘겨주어 화면을 띄우는 예제
            String postId = "1"; // 실제 게시물 ID로 변경해야 함

            // 사용자 정보 예시 (임의로 생성)
            Users userInfo = new Users();
            userInfo.setUserId("사용자 ID"); // 사용자 ID 설정
            userInfo.setEmail("example@example.com"); // 사용자 이메일 설정

            // 게시물 정보 예시 (임의로 생성)
            Posts post = new Posts("1",
                    "■ 교통사고처리특례법 위반 혐의 우선 적용될 듯…최고 5년 이하의 금고\n" +
                            "\n" +
                            "사고의 원인이 정확히 밝혀진 것은 아니나, 우선 A 씨에겐 교통사고처리특례법(교통사고처리법) 위반(치사·상) 혐의가 적용될 것으로 보입니다.\n" +
                            "\n" +
                            "교통사고처리법 제3조 제1항은 운전자가 교통사고를 내 업무상과실 또는 중과실로 사람을 사망이나 상해에 이르게 하는 경우, 5년 이하의 금고 또는 2천만 원 이하의 벌금에 처하도록 규정하고 있습니다.\n" +
                            "\n" +
                            "9명의 사망자와 4명의 부상자가 발생한 이상, A 씨에겐 해당 혐의가 적용됩니다.\n" +
                            "\n" +
                            "원칙적으로는 형법상 업무상 과실치사상 혐의도 적용될 수 있지만, 교통사고로 인한 부상, 사망사고의 경우 교통사고처리법이 일반법인 형법보다 먼저 적용되게 됩니다.\n" +
                            "\n" +
                            "사고 당시 A 씨의 도로교통법 위반(보도침범, 횡단보도 보행자보호의무 위반) 등도 문제가 될 소지가 있고, 만약 A씨가 음주나 약물의 영향으로 사고를 일으킨 경우라면 특정범죄가중처벌 등에 관한 법률 제5조의11에 따른 위험운전치사상 혐의가 적용돼 형량이 크게 높아질 수 있습니다.\n" +
                            "\n" +
                            "교통사고처리법은 차량이 종합보험에 가입하면 사고를 냈더라도 교통사고처리법상의 형사처벌을 면제(공소제기 불가)해 주도록 규정하고 있는데, 인도 침범은 도로교통법상 12대 중과실에 해당돼 A 씨 차량의 종합보험 가입 유무와 상관없이 형사처벌을 받을 여지가 있습니다.",
                    new Date(), "user1", 10, "해시태그", "USER1");

            PostDetailView frame = new PostDetailView(post, userInfo);
            frame.setVisible(true);
        });
    }
}
