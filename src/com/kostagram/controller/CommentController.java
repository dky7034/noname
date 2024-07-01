package com.kostagram.controller;

import com.kostagram.model.Comments;
import com.kostagram.model.CommentDao;
import com.kostagram.model.Users;
import com.kostagram.view.CommentView;
import java.util.Date;

public class CommentController {
    private CommentView commentView;
    private CommentDao commentDao;
    private String postId;
    private Users userInfo;

    public CommentController(CommentView commentView, CommentDao commentDao, String postId, Users userInfo) {
        this.commentView = commentView;
        this.commentDao = commentDao;
        this.postId = postId;
        this.userInfo = userInfo;
    }

    public CommentController() {};

    public void addComment(String content) {
        Comments comments = new Comments();
        comments.setPostId(postId);
        comments.setUserId(userInfo.getUserId());
        comments.setCommentContent(content);
        comments.setCreateDate(new Date());

        try {
            commentDao.addComment(comments);
            commentView.dispose();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
