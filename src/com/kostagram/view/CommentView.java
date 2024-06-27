package com.kostagram.view;

import com.kostagram.model.Comments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CommentView extends JFrame {

    public ArrayList<JPanel> loadComment () {
        ArrayList<JPanel> commentList = new ArrayList<JPanel>();

        /*
        1.리스트 생성
        2. 테이블 조회
        3. 조회한 데이터를 바탕으로 comment 객체 생성
        4. 생성한 객체를 넣을 패널 생성
        5. 패널을 뷰에 배치
        6. 다음 데이터 조회 후 반복
         */
        return commentList;
    }
    // 댓글 화면의 타이틀
    private JLabel commentTitle = new JLabel("Comment");
    // 댓글들을 출력할 TextArea
    private JTextArea commentArea = new JTextArea(5, 30);
    // 하단 패널에 들어갈 프로필 이미지, 버튼
    private JLabel profileImage = new JLabel("image");
    private JTextField commentText = new JTextField();
    private JButton commentAddButton = new JButton("ADD");



    public CommentView() {
        setTitle("Comment");
        setSize(450, 920);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


    }

    public String getComment() {
        return commentArea.getText();
    }

    public void addCommentListener(ActionListener listener) {
        commentAddButton.addActionListener(listener);
    }

}