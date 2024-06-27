package com.kostagram.view;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class RoundedBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int radius;

    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRoundRect(x, y, width - thickness, height - thickness, radius, radius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }
}

public class SearchView extends JFrame {

    public SearchView() {
        // JFrame 기본 설정
        setTitle("Kostagram Search"); // 창 제목 설정
        setSize(480, 920); // 창 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫을 때 종료 설정
        setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        setBackground(Color.black); // 배경 색상 설정

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // BorderLayout 사용
        mainPanel.setBackground(Color.black); // 메인 패널 배경 색상 설정

        // 검색 패널 설정
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout()); // BorderLayout 사용
        searchPanel.setBackground(Color.black); // 검색 패널 배경 색상 설정

        // 검색 필드 추가
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 20)); // 글자 크기와 스타일 설정
        searchField.setBorder(new RoundedBorder(Color.GRAY, 2, 15)); // 둥근 모서리 테두리 설정
        searchField.setBackground(Color.gray); // 검색 필드 배경 색상 설정
        searchField.setForeground(Color.LIGHT_GRAY); // 검색 필드 글자 색상 설정
        searchField.setCaretColor(Color.LIGHT_GRAY); // 검색 필드 커서 색상 설정
        searchField.setMargin(new Insets(0, 10, 0, 0)); // 검색 필드 내부 여백 설정
        searchPanel.add(searchField, BorderLayout.CENTER); // 검색 필드를 패널의 중앙에 추가

        // 검색 버튼을 돋보기 이미지로 대체
        JButton searchButton = new JButton();
        searchButton.setPreferredSize(new Dimension(45, 45)); // 버튼 크기 설정
        ImageIcon searchIcon = new ImageIcon("images/search_icon_03.png"); // 아이콘 설정
        searchButton.setIcon(searchIcon); // 버튼에 아이콘 추가
        searchButton.setBackground(Color.black); // 버튼 배경 색상 설정
        searchButton.setBorderPainted(false); // 버튼 테두리 제거
        searchPanel.add(searchButton, BorderLayout.WEST); // 버튼을 패널의 서쪽에 추가

        // 검색 결과 표시 패널 설정
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 3, 0, 0)); // 3열 그리드 레이아웃, 이미지 간격 설정
        resultsPanel.setBackground(Color.black); // 결과 패널 배경 색상 설정

        // 예제 이미지 추가
        for (int i = 1; i <= 20; i++) { // 예제 이미지를 패널에 추가
            try {
                BufferedImage img = ImageIO.read(new File("images/dummy_img" + i + ".jpg")); // 각 이미지 경로 설정
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 이미지 리사이징
                JLabel resultLabel = new JLabel(new ImageIcon(scaledImg)); // 리사이징된 이미지를 JLabel에 추가
                resultsPanel.add(resultLabel); // 결과 패널에 라벨 추가
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // JScrollPane을 사용하여 결과 패널을 스크롤 가능하게 설정
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤바 필요 시 표시 설정
        scrollPane.getViewport().setBackground(Color.black); // 스크롤뷰포트 배경 색상 설정
        scrollPane.setBackground(Color.black); // 스크롤패널 배경 색상 설정

        // 커스텀 스크롤바 UI 적용
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI()); // 수직 스크롤바에 커스텀 UI 적용
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI()); // 수평 스크롤바에 커스텀 UI 적용

        // 검색 버튼에 액션 리스너 추가
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText(); // 검색어 가져오기
                // 검색 로직 추가 (예: 데이터베이스에서 검색)
                // 여기서는 예제 결과 추가
                resultsPanel.removeAll();  // 기존 결과 삭제
                for (int i = 1; i <= 20; i++) { // 예제 이미지를 패널에 추가
                    try {
                        BufferedImage img = ImageIO.read(new File("images/dummy_img" + i + ".jpg")); // 각 이미지 경로 설정
                        Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 이미지 리사이징
                        JLabel resultLabel = new JLabel(new ImageIcon(scaledImg)); // 리사이징된 이미지를 JLabel에 추가
                        resultsPanel.add(resultLabel); // 결과 패널에 라벨 추가
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                resultsPanel.revalidate(); // 패널 갱신
                resultsPanel.repaint(); // 패널 다시 그리기
            }
        });

        // 메인 패널에 구성 요소 추가
        mainPanel.add(searchPanel, BorderLayout.NORTH); // 검색 패널을 메인 패널의 북쪽에 추가
        mainPanel.add(scrollPane, BorderLayout.CENTER); // 스크롤 패널을 메인 패널의 중앙에 추가

        // JFrame에 메인 패널 추가
        add(mainPanel);
    }

    public static void main(String[] args) {
        // EventQueue를 사용하여 스윙 컴포넌트를 생성 및 관리
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SearchView frame = new SearchView(); // SearchView 인스턴스 생성
                frame.setVisible(true); // 창을 보이게 설정
            }
        });
    }
}
