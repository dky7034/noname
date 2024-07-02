package com.kostagram.controller;

import com.kostagram.model.Users;
import com.kostagram.model.UserDao;
import com.kostagram.view.LoginView;
import com.kostagram.view.MainView;
import com.kostagram.view.RegisterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;


/*
    로그인과 관련된 기능을 담당하는 컨트롤러
 */
public class LoginController {
    private LoginView loginView;
    private UserDao userDao;
    public static Users users;
    public LoginController(LoginView loginView, UserDao userDao) {
        this.loginView = loginView;
        this.userDao = userDao;
        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addRegisterListener(new RegisterListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String email = loginView.getEmail();
            String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

            String password = new String(loginView.getPassword());

            users = userDao.getUserByEmail(email);
            System.out.println("users in LoginController: " + users);

            if(Pattern.matches(pattern, email)) {
                if (users != null && users.getPassword().equals(password)) {
                    MainView mainView = new MainView();
                    MainController mainController = new MainController(mainView, users);
                    loginView.dispose();
                    mainView.setVisible(true);
                    System.out.println("로그인 성공!!");
                } else {
                    loginView.displayErrorMessage("이메일이나 비밀번호가 일치하지 않습니다.");
                }
            }else {
                loginView.displayErrorMessage("올바른 이메일 형식이 아닙니다.\n이메일과 비밀번호를 확인하세요");
                loginView.setEmail("Enter your Email");
                loginView.setPassword("Password");
                loginView.setEchoChar('\u0000');
            }



        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            RegisterView registerView = new RegisterView();
            RegisterController registerController = new RegisterController(registerView, userDao);
            loginView.dispose();
            registerView.setVisible(true);
        }
    }
}
