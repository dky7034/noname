package com.kostagram.controller;

import com.kostagram.model.CommentDao;
import com.kostagram.model.Comments;
import com.kostagram.model.Users;
import com.kostagram.view.CommentView;

import java.util.List;

public class CommentController {
    private CommentView commentView;
    private CommentDao commentDao;
    private String postId;
    private Users user;

    public CommentController(CommentView commentView, CommentDao commentDao, String postId, Users user) {
        this.commentView = commentView;
        this.commentDao = commentDao;
        this.postId = postId;
        this.user = user;
    }

    public List<Comments> getComments() {
        return commentDao.getCommentsByPostId(postId);
    }

    public void addComment(String content) {
        Comments comment = new Comments();
        comment.setUserId(user.getUserId());
        comment.setUserEmail(user.getEmail());
        comment.setCommentContent(content);
        comment.setPostId(postId);
        comment.setCreateDate(new java.util.Date());
        commentDao.addComment(comment);
    }
}