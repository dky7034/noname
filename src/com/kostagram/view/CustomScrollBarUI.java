package com.kostagram.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(127, 127, 127); // 스크롤바의 thumb 색상
        this.trackColor = new Color(0, 0, 0); // 스크롤바의 track 색상
    }
    @Override protected JButton createDecreaseButton(int orientation) {return createZeroButton();}

    @Override
    protected JButton createIncreaseButton(int orientation) {return createZeroButton();}

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = thumbColor != null ? thumbColor : UIManager.getColor("ScrollBar.thumb");
        g2.setPaint(color);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 5, 5);
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = trackColor != null ? trackColor : UIManager.getColor("ScrollBar.track");
        g2.setPaint(color);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(8, super.getPreferredSize(c).height); // 넓이를 10으로 설정
        } else {
            return new Dimension(super.getPreferredSize(c).width, 8); // 높이를 10으로 설정
        }
    }
}