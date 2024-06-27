package com.kostagram.view;

import com.kostagram.controller.LoginController;
import com.kostagram.model.UserDao;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginView extends JFrame {
    private RoundJTextField emailField = new RoundJTextField(25,new Color(54,54,54), Color.white, "Enter your Email");
    private RoundJPasswordField passwordField = new RoundJPasswordField(25,new Color(54,54,54), Color.white,"Password");
    private FadeButton loginButton = new FadeButton(new Color(0,150,247), new Color(0,92,192),
            Color.white, "Login");
    private FadeButton registerButton = new FadeButton(new Color(0,150,247), new Color(0,92,192),
            Color.white, "Register");

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(450, 920);
        setLocationRelativeTo(null);

        // 로그인 화면 구성
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(0, 0, 0));
        JLabel logoLabel = new JLabel("Kostagram");
        Font font = new Font("Bauhaus 93",Font.BOLD,50);
        logoLabel.setFont(font);
        logoLabel.setForeground(Color.white);
        logoLabel.setHorizontalAlignment(0);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 20, 10);

        //이메일 입력란
//        emailField.setBackground(new Color(25,25,25));
        emailField.setPreferredSize(new Dimension(200, 50));
        emailField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Enter your Email");
//                    emailField.setForeground(Color.white);
                    emailField.changeForeground(Color.white);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Enter your Email")) {
                    emailField.setText("");
//                    emailField.setForeground(new Color(0,150,247));
                    emailField.changeForeground(new Color(0,150,247));
                }
            }
        });

        //비밀번호 입력란
        passwordField.setEchoChar('\u0000');
        passwordField.setPreferredSize(new Dimension(2000, 50));
        passwordField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar('\u0000');
                    passwordField.changeForeground(Color.white);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('●');
                    passwordField.changeForeground(new Color(0,150,247));
                }
            }
        });

        //배치
        gbc.gridx = 0;gbc.gridy = 0; loginPanel.add(logoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 2; loginPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; loginPanel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; loginPanel.add(loginButton, gbc);
        gbc.gridx = 0; gbc.gridy = 6; loginPanel.add(registerButton, gbc);

        add(loginPanel);

        setVisible(true);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getText());
    }

    public void setEmail(String email) {
        emailField.setText(email);
    }

    public void setPassword(String pw) {
        passwordField.setText(pw);
    }

    public void setEchoChar(char ech) {
        passwordField.setEchoChar(ech);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public static void main(String[] args) {
        new LoginView();
    }
}