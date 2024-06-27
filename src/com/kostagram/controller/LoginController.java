package com.kostagram.controller;

import com.kostagram.model.Users;
import com.kostagram.model.UserDao;
import com.kostagram.view.LoginView;
import com.kostagram.view.MainView;
import com.kostagram.view.RegisterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    로그인과 관련된 기능을 담당하는 컨트롤러
 */
public class LoginController {
    private LoginView loginView;
    private UserDao userDao;

    public LoginController(LoginView loginView, UserDao userDao) {
        this.loginView = loginView;
        this.userDao = userDao;

        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addRegisterListener(new RegisterListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmail();
            String password = loginView.getPassword();

            Users users = userDao.getUserByEmail(email);
            if (users != null && users.getPassword().equals(password)) {
                MainView mainView = new MainView();
                MainController mainController = new MainController(mainView, users);
                loginView.dispose();
                mainView.setVisible(true);
            } else {
                loginView.displayErrorMessage("이메일이나 비밀번호가 일치하지 않습니다.");
            }
        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            RegisterView registerView = new RegisterView();
            RegisterController registerController = new RegisterController(registerView, userDao);
            registerView.setVisible(true);
        }
    }
}
