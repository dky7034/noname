package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PostView extends JFrame {
    private JTextArea contentArea = new JTextArea(10, 30);
    private JButton postButton = new JButton("Post");

    public PostView() {
        setTitle("Add Post");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        panel.add(postButton, BorderLayout.SOUTH);

        add(panel);
    }

    public String getContent() {
        return contentArea.getText();
    }

    public void addPostListener(ActionListener listener) {
        postButton.addActionListener(listener);
    }
}
