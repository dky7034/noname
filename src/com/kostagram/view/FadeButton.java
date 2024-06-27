package com.kostagram.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FadeButton extends JButton {
    private final Color unClickBackground;
    private final Color clickBackground;
    private final Color foreground;
    private final String text;

    int paddingWidth = 50, paddingHeight = 3;

    public FadeButton(Color unClickBackground, Color clickBackground, Color foreground, String text) {
        this.unClickBackground = unClickBackground;
        this.clickBackground = clickBackground;
        this.foreground = foreground;
        this.text = text;

        setText(text);

        Dimension dimension = getPreferredSize();
//        int w = (int) dimension.getWidth() + paddingWidth * 3;
//        int h = (int) dimension.getHeight() + paddingHeight * 3;

        setPreferredSize(new Dimension(240, 37));
        setOpaque(false);
        setBorder(null);
        setBackground(unClickBackground);
        setForeground(foreground);
        addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(unClickBackground);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(clickBackground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension dimension = getPreferredSize();
        int w = (int) dimension.getWidth();
        int h = (int) dimension.getHeight();

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w, h, 25, 25);

        g2.setColor(getForeground());
        g2.setFont(new Font("Arial Rounded MT", Font.PLAIN, 18));

        FontMetrics fontMetrics = g2.getFontMetrics();
        Rectangle rectangle = fontMetrics.getStringBounds(getText(), g2).getBounds();

        g2.drawString(getText(), (w - rectangle.width) / 2, (h - rectangle.height) / 2 + fontMetrics.getAscent());
    }


}