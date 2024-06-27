package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame {

    private JButton addPostButton = new JButton("Add Post");
    private JButton logoutButton = new JButton("Logout");
    private JPanel postsPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(postsPanel);

    public MainView() {
        setTitle("Main");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addPostButton);
        buttonsPanel.add(logoutButton);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public void addPost(String postContent) {
        JTextArea postArea = new JTextArea(postContent);
        postArea.setLineWrap(true);
        postArea.setWrapStyleWord(true);
        postArea.setEditable(false);
        postArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        postPanel.add(postArea, BorderLayout.CENTER);

        postsPanel.add(postPanel);
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    public void setPosts(String posts) {
        postsPanel.removeAll();
        for (String post : posts.split("\n")) {
            addPost(post);
        }
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    public void addAddPostListener(ActionListener listener) {
        addPostButton.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}
