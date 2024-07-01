package com.kostagram.controller;

import com.kostagram.model.Users;
import com.kostagram.Main;
import com.kostagram.model.UserDao;
import com.kostagram.view.LoginView;
import com.kostagram.view.MainView;
import com.kostagram.view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.regex.Pattern;

public class RegisterController {
    private RegisterView registerView;
    private UserDao userDao;

    public RegisterController(RegisterView registerView, UserDao userDao) {
        this.registerView = registerView;
        this.userDao = userDao.getInstance();
        this.registerView.addRegisterListener(new RegisterListener());
        this.registerView.addBackListener(new BackListener());
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = registerView.getEmail();
            String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
            String password = registerView.getPassword();
            if(Pattern.matches(pattern,email)) {
                System.out.println("올바른 이메일 형식");
            }else {
                System.out.println("이메일 형식 틀림!!");
                registerView.displayErrorMessage("올바른 이메일 형식이 아닙다.\n다시 입력해주세요.");
                registerView.setEmail("Enter your Email");
                registerView.setPassword("Password");
                registerView.setEchoChar('\u0000');
                return;
            }

            //이메일 중복 점검
            Users isUser = userDao.getUserByEmail(email);
            if(isUser==null){
                System.out.println("이메일 중복 아님");
            }else{
                System.out.println("이메일 중복..!");
                registerView.displayErrorMessage("이미 존재하는 이메일입니다.\n다른 이메일을 입력하세요.");
                registerView.setEmail("Enter your Email");
                registerView.setPassword("Password");
                registerView.setEchoChar('\u0000');
                return;
            }

            Users users = new Users();
            users.setEmail(email);
            users.setPassword(password);

            try {
                int re = userDao.addUser(users); //db에 user 추가
                if(re>0) {
                    registerView.displayErrorMessage("회원 가입 성공!");
                    LoginView loginView = new LoginView();
                    LoginController loginController = new LoginController(loginView, userDao);
                    registerView.dispose();
                    loginView.setVisible(true);

                }else {
                    registerView.displayErrorMessage("회원 가입 실패!");
                    System.out.println("회원 가입 실패");
                }
            }catch(Exception e1) {
                System.out.println("RegisterController 예외 -> "+e1);
            }

        }
    }
    class BackListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println("뒤로가기");
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView, userDao);
            registerView.dispose();
            loginView.setVisible(true);
        }
    }
}
