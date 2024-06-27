package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CommentView extends JFrame {
    private JTextArea commentArea = new JTextArea(5, 30);
    private JButton commentButton = new JButton("Comment");

    public CommentView() {
        setTitle("Add Comment");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(commentArea), BorderLayout.CENTER);
        panel.add(commentButton, BorderLayout.SOUTH);

        add(panel);
    }

    public String getComment() {
        return commentArea.getText();
    }

    public void addCommentListener(ActionListener listener) {
        commentButton.addActionListener(listener);
    }
}
