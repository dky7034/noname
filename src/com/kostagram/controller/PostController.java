package com.kostagram.controller;

import com.kostagram.model.Posts;
import com.kostagram.model.PostDao;
import com.kostagram.model.Users;
import com.kostagram.view.PostView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class PostController {
    private PostView postView;
    private Users userInfo;
    private PostDao postDao;

    public PostController(PostView postView, Users userInfo, PostDao postDao) {
        this.postView = postView;
        this.userInfo = userInfo;
        this.postDao = postDao;

        this.postView.addPostListener(new PostListener());
    }

    class PostListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String content = postView.getContent();
            File imageFile = postView.getSelectedFile();
            Posts posts = new Posts();
            posts.setUserId(userInfo.getUserId());
            posts.setContent(content);
            posts.setCreateDate(new Date());

            try {
                if (imageFile != null) {
                    // 이미지 파일을 지정된 디렉토리에 저장
                    String imagePath = saveImageFile(imageFile);
                    posts.setImagePath(imagePath);
                }
                postDao.addPost(posts);
                postView.dispose();
            } catch (Exception ex) {
                // postView.displayErrorMessage("Failed to add post: " + ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }

        private String saveImageFile(File imageFile) throws IOException {
            // images라는 이름의 디렉토리 경로를 Path 객체로 만듭니다. 이 디렉토리는 프로젝트의 루트 디렉토리에 생성됩니다.
            Path destinationDirectory = Paths.get("images");  // 이미지 파일을 저장할 디렉토리
            // Files.exists(destinationDirectory): 지정된 경로에 디렉토리가 있는지 확인합니다.
            if (!Files.exists(destinationDirectory)) {
                // Files.createDirectories(destinationDirectory): 디렉토리가 존재하지 않으면 images 디렉토리를 생성합니다. 필요한 상위 디렉토리도 함께 생성합니다.
                Files.createDirectories(destinationDirectory);
            }

            // destinationDirectory.resolve(imageFile.getName()): images 디렉토리 내에 원본 파일 이름과 동일한 파일 경로를 설정합니다.
            Path destinationPath = destinationDirectory.resolve(imageFile.getName());
            // imageFile.toPath(): 원본 파일의 경로를 Path 객체로 변환합니다.
            // Files.copy(...): 원본 파일 경로에서 지정된 디렉토리 경로로 파일을 복사합니다. 이미 파일이 존재할 경우 예외를 발생시킵니다.
            Files.copy(imageFile.toPath(), destinationPath);
            // 복사된 파일의 경로를 문자열로 반환합니다. 이 경로는 데이터베이스에 저장되어 나중에 파일을 다시 찾는 데 사용됩니다.
            return destinationPath.toString();  // 이미지 파일 경로 반환
        }
    }
}
