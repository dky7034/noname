package com.kostagram;

import com.kostagram.controller.LoginController;
import com.kostagram.model.UserDao;
import com.kostagram.view.LoginView;

public class Main {

    public static void main(String[] args) {
        // UserDao 인스턴스를 얻기 위해 getInstance() 메서드를 호출
        UserDao userDao = UserDao.getInstance();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView, userDao);
        loginView.setVisible(true);
    }
}
///src
// ├── model
// │    ├── User.java
// │    ├── UserDao.java
// │    ├── Post.java
// │    ├── PostDao.java
// │    ├── Comment.java
// │    ├── CommentDao.java
// │    ├── Like.java
// │    ├── LikeDao.java
// │    └── Hashtag.java
// │    └── HashtagDao.java
// ├── view
// │    ├── LoginView.java
// │    ├── RegisterView.java
// │    ├── MainView.java
// │    ├── PostView.java
// │    └── CommentView.java
// ├── controller
// │    ├── LoginController.java
// │    ├── RegisterController.java
// │    ├── MainController.java
// │    ├── PostController.java
// │    └── CommentController.java
// └── server
//      └── Server.java
// └── Main.java
