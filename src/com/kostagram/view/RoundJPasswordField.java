package com.kostagram.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class RoundJPasswordField extends JPasswordField {
    private Shape shape;
    private Color background;
    private Color foreground;
    private String text;
    public RoundJPasswordField(int size,Color background, Color foreground, String text) {
        super(size);
        this.background = background;
        this.foreground = foreground;
        this.text = text;
        setOpaque(false); // As suggested by @AVD in comment.
        setCaretColor(Color.white);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        setBackground(background);
        setForeground(foreground);
        setText(text);
    }
    protected void paintComponent(Graphics g) {
        g.setColor(background);
        g.fillRoundRect(0, 0, getWidth()-3, getHeight()-1, 20, 20);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(foreground);
        g.drawRoundRect(0, 0, getWidth()-3, getHeight()-1, 20, 20);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 20, 20);
        }
        return shape.contains(x, y);
    }

    public void changeForeground(Color color){
        foreground = color;
        setForeground(color);
    }
}