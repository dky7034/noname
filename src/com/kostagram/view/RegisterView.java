package com.kostagram.view;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegisterView extends JFrame {
    private RoundJTextField emailField = new RoundJTextField(25,new Color(54,54,54), Color.white, "Enter your Email");
    private RoundJPasswordField passwordField = new RoundJPasswordField(25,new Color(54,54,54), Color.white,"Password");
    private FadeButton registerButton = new FadeButton(new Color(0,150,247), new Color(0,92,192),
            Color.white, "Register");
    private JButton backButton = new JButton(new ImageIcon("image/back-icon.png"));

    public RegisterView() {
        setTitle("Register");
        setSize(450, 920);
        setLocationRelativeTo(null);
        setResizable(false);
        // 회원가입 화면 구성
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBackground(new Color(0, 0, 0));
        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(0,0,0));
        backPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButton.setSize(20,20);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backPanel.add(backButton);

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
//        emailField.setBorder(new RoundedBorder(15, Color.white));
        emailField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Enter your Email");
                    emailField.changeForeground(Color.white);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Enter your Email")) {
                    emailField.setText("");
                    emailField.changeForeground(new Color(0,150,247));
                }
            }
        });

        //비밀번호 입력란
//        passwordField.setForeground(Color.white);
//        passwordField.setBackground(new Color(0,0,0));
        passwordField.setEchoChar('\u0000');
        passwordField.setPreferredSize(new Dimension(200, 50));
//        passwordField.setBorder(new RoundedBorder(15, Color.white));
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
        gbc.gridx = 0;gbc.gridy = 0; signUpPanel.add(logoLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 2; signUpPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; signUpPanel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; signUpPanel.add(registerButton, gbc);

        add(backPanel, BorderLayout.NORTH);
        add(signUpPanel, BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
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

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener){
        backButton.addActionListener(listener);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        new RegisterView();
    }


}
