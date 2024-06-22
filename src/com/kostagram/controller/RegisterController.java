package com.kostagram.controller;

import com.kostagram.model.Users;
import com.kostagram.model.UserDao;
import com.kostagram.view.RegisterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class RegisterController {
    private RegisterView registerView;
    private UserDao userDao;

    public RegisterController(RegisterView registerView, UserDao userDao) {
        this.registerView = registerView;
        this.userDao = userDao.getInstance();

        this.registerView.addRegisterListener(new RegisterListener());
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = registerView.getEmail();
            String password = registerView.getPassword();

            Users users = new Users();
            users.setEmail(email);
            users.setPassword(password);
            users.setCreateDate(new Date());

            userDao.addUser(users);
            registerView.displayErrorMessage("회원 가입 성공!");
            registerView.dispose();
        }
    }
}
