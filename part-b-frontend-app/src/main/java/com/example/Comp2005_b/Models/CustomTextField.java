package com.example.Comp2005_b.Models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomTextField extends JTextField implements FocusListener {
    private final String hint;
    private boolean showingHint;

    public CustomTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        this.addFocusListener(this);
        this.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            this.setForeground(Color.BLACK);
            this.showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            this.setForeground(Color.GRAY);
            this.showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showingHint && !hasFocus()) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            int padding = (this.getHeight() - this.getFontMetrics(getFont()).getHeight()) / 2;
        }
    }
}
