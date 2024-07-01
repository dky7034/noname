package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomPanel extends JPanel {
    private JButton homeButton;
    private JButton searchButton;
    private JButton addButton;
    private JButton userButton;

    public BottomPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 35, 10, 35);

        homeButton = createIconButton("home");
        searchButton = createIconButton("search");
        addButton = createIconButton("add");
        userButton = createIconButton("user");

        gbc.gridx = 0;
        add(homeButton, gbc);
        gbc.gridx = 1;
        add(searchButton, gbc);
        gbc.gridx = 2;
        add(addButton, gbc);
        gbc.gridx = 3;
        add(userButton, gbc);
    }

    private JButton createIconButton(String name) {
        String defaultIconPath = "./src/com/kostagram/icon/";
        ImageIcon icon = new ImageIcon(defaultIconPath + name + ".png");
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(36, 36));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    public void addHomeButtonListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUserButtonListener(ActionListener listener) {
        userButton.addActionListener(listener);
    }


}
