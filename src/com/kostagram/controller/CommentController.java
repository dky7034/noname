package com.kostagram.controller;

import com.kostagram.model.*;
import com.kostagram.view.CommentView;

import java.util.List;

public class CommentController {
    private CommentDao commentDao;
    private Posts posts;
    private String postId;
    private Users user;
    private CommentView view;

    public CommentController(CommentDao commentDao, CommentView view, String postId, Users user) {
        this.commentDao = commentDao;
        this.view = view;
        this.postId = postId;
        this.user = user;
    }

    public List<Comments> getComments() {
        return commentDao.getComments(postId);  // postId에 해당하는 댓글을 가져옵니다.
    }

    public void addComment(String content) {
        Comments comment = new Comments(0, content, user.getEmail(), postId);  // 생성자에 맞게 수정하세요.
        commentDao.addComment(comment);
        view.addCommentPanel(comment);
    }
}
