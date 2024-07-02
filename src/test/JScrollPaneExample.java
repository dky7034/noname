package test;

import javax.swing.*;
import java.awt.*;

public class JScrollPaneExample extends JFrame {
    public static void main(String[] args) {
        // 프레임 생성
        JFrame frame = new JFrame("JScrollPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 스크롤 패널 생성
        JScrollPane scrollPane = new JScrollPane();

        // 패널 생성 및 설정
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 텍스트 에리어 생성
        JTextArea textArea = new JTextArea("여기에 텍스트를 입력하세요.");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // 텍스트 에리어를 패널에 추가
        panel.add(textArea, BorderLayout.CENTER);

        // 패널을 스크롤 패널에 추가
        scrollPane.setViewportView(panel);

        // 프레임에 스크롤 패널 추가
        frame.add(scrollPane);

        // 프레임 표시
        frame.setVisible(true);
    }
}
