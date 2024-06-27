package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class PostView extends JFrame {
    private JTextArea contentArea = new JTextArea(10, 30);
    private JButton postButton = new JButton("Post");
    private JButton uploadButton = new JButton("Upload Image");
    private JLabel imageLabel = new JLabel();
    private File selectedFile;

    public PostView() {
        setTitle("Add Post");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(uploadButton, BorderLayout.WEST);
        southPanel.add(postButton, BorderLayout.EAST);
        panel.add(southPanel, BorderLayout.SOUTH);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        panel.add(imagePanel, BorderLayout.NORTH);

        add(panel);

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                imageLabel.setIcon(imageIcon);
            }
        });
    }

    public String getContent() {
        return contentArea.getText();
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void addPostListener(ActionListener listener) {
        postButton.addActionListener(listener);
    }
}
