package com.kostagram.controller;

import com.kostagram.model.Comments;
import com.kostagram.model.CommentDao;
import com.kostagram.model.Users;
import com.kostagram.view.CommentView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/*
    댓글과 관련된 기능을 담당하는 컨트롤러
 */
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
        this.commentView.addCommentListener(new CommentListener());
    }

    class CommentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String content = commentView.getComment();
            Comments comments = new Comments();
            comments.setPostId(postId);
            comments.setUserId(userInfo.getUserId());
            comments.setContent(content);
            comments.setCreateDate(new Date());

            try {
                commentDao.addComment(comments);
                commentView.dispose();
            } catch (Exception ex) {
                //commentView.displayErrorMessage("Failed to add comment: " + ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }
    }
}
