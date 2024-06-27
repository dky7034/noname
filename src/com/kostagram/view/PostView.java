package com.kostagram.view;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PostView extends JFrame {
    private JTextArea contentArea = new JTextArea(10, 30);
    private JButton postButton = new JButton("Post");
    private FadeButton newImgBtn;
    public JPanel leftPanel;
    public PostView() {
        setSize(1050, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel(new BorderLayout());
        // 상단
        JPanel topPanel = new JPanel();
        topPanel.setBackground(MainView.bgColor);
        topPanel.setPreferredSize(new Dimension(1050, 42));
        //게시글 내용 작성 패널
        leftPanel = new JPanel();
        leftPanel.setBackground(MainView.bgColor);
        leftPanel.setPreferredSize(new Dimension(710, 710));
        newImgBtn = new FadeButton(new Color(0,150,247), new Color(0,92,192), Color.white, "컴퓨터에서 선택");
        leftPanel.add(newImgBtn);
        String defaultImagePath = "./src/com/kostagram/image/";
        JLabel imageLabel = new JLabel(new ImageIcon(defaultImagePath+"test1.jpg"));
        imageLabel.setPreferredSize(new Dimension(710, 710));
        leftPanel.add(imageLabel);

        //게시글 내용 작성 패널
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(MainView.fgColor);
        rightPanel.setPreferredSize(new Dimension(340, 710));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        this.add(panel);
        this.setVisible(true);
    }

    public String getContent() {
        return contentArea.getText();
    }
    public void addNewImgListener(ActionListener listener){ newImgBtn.addActionListener(listener);}
    public void addPostListener(ActionListener listener) {
        postButton.addActionListener(listener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PostView::new);
    }
}

