package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SearchView extends JFrame {

    public SearchView() {
        // JFrame 기본 설정
        setTitle("Instagram Search");
        setSize(480, 920);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.black);

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.black);

        // 검색 패널 설정
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(Color.black);

        // 검색 필드 추가
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 64));
        searchField.setBackground(Color.gray);
        searchField.setForeground(Color.white);
        searchField.setCaretColor(Color.white);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // 검색 버튼을 돋보기 이미지로 대체
        JButton searchButton = new JButton();
        searchButton.setPreferredSize(new Dimension(55, 55));
        ImageIcon searchIcon = new ImageIcon("images/search_icon_02.png");
        searchButton.setIcon(searchIcon);
        searchButton.setBackground(Color.black);
        searchButton.setBorderPainted(false);
        searchPanel.add(searchButton, BorderLayout.WEST);

        // 검색 결과 표시 패널 설정
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 3, 0, 0)); // 3열 그리드 레이아웃, 이미지 간격 설정
        //=> GridLayout(행의 개수, 열의 개수, 수평 간격, 수직 간격);
        resultsPanel.setBackground(Color.black);

        // 예제 이미지 추가
        for (int i = 1; i <= 15; i++) {
            try {
                BufferedImage img = ImageIO.read(new File("images/dummy_img" + i + ".jpg")); // 각 이미지 경로 설정
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // SCALE_SMOOTH를 사용하여 이미지를 부드럽게 리사이징
                JLabel resultLabel = new JLabel(new ImageIcon(scaledImg));
                resultsPanel.add(resultLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // JScrollPane을 사용하여 결과 패널을 스크롤 가능하게 설정
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(Color.black);
        scrollPane.setBackground(Color.black);

        // 검색 버튼에 액션 리스너 추가
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                // 검색 로직 추가 (예: 데이터베이스에서 검색)
                // 여기서는 예제 결과 추가
                resultsPanel.removeAll();  // 기존 결과 삭제
                for (int i = 1; i <= 15; i++) {
                    try {
                        BufferedImage img = ImageIO.read(new File("images/dummy_img" + i + ".jpg")); // 각 이미지 경로 설정
                        Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        JLabel resultLabel = new JLabel(new ImageIcon(scaledImg));
//                        resultLabel.setText("Result " + i + " for query: " + query); // 검색 결과 텍스트 추가
//                        resultLabel.setForeground(Color.white);  // 검색 결과 텍스트 색상 변경
                        resultsPanel.add(resultLabel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                resultsPanel.revalidate();
                resultsPanel.repaint();
            }
        });

        // 메인 패널에 구성 요소 추가
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // JFrame에 메인 패널 추가
        add(mainPanel);
    }

    public static void main(String[] args) {
        // EventQueue를 사용하여 스윙 컴포넌트를 생성 및 관리
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SearchView frame = new SearchView();
                frame.setVisible(true);
            }
        });
    }
}
