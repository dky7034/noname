package com.kostagram.model;

import com.kostagram.db.ConnectionProvider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class SearchDao {
    private static SearchDao instance = new SearchDao();

    public SearchDao() {}

    public static SearchDao getInstance() {
        return instance;
    }

    public List<BufferedImage> searchImages(String query) {
        List<BufferedImage> images = new ArrayList<>();
        String sql = "SELECT post_id FROM posts WHERE post_content LIKE ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String imagePath = rs.getString("post_id");
                try {
                    BufferedImage img = ImageIO.read(new File(imagePath));
                    images.add(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }
}
