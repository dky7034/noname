package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ImageUploadView extends JFrame {
    private JButton uploadButton;
    private JLabel imageLabel;
    private File selectedFile;

    public ImageUploadView() {
        setTitle("Image Upload");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        uploadButton = new JButton("Upload Image");
        imageLabel = new JLabel();

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    imageLabel.setIcon(imageIcon);
                }
            }
        });

        add(uploadButton);
        add(imageLabel);

        setVisible(true);
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public static void main(String[] args) {
        new ImageUploadView();
    }
}
