package com.runemonsters.swing;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.SwingUtil;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class JGenerator {
    public static JLabel createLabel(String labelText, Font font) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setOpaque(false);
        label.setFocusable(false);
        label.setFont(font);

        return label;
    }

    public static JButton createIconButton(
            Icon icon,
            String tooltipText,
            ActionListener actionListener,
            MouseListener mouseListener
    ) {
        JButton iconButton = new JButton();
        SwingUtil.removeButtonDecorations(iconButton);
        iconButton.setIcon(icon);
        iconButton.setToolTipText(tooltipText);
        iconButton.setBackground(ColorScheme.DARK_GRAY_COLOR);
        iconButton.setUI(new BasicButtonUI());
        iconButton.addActionListener(actionListener);
        iconButton.addMouseListener(mouseListener);
        return iconButton;
    }

    public static CardImageLabel createImageLabel(
            Image image,
            Integer width,
            Integer height,
            Boolean startAsGreyscale
    ) {
        return new CardImageLabel(
                new ImageIcon(image.getScaledInstance(width, height, BufferedImage.SCALE_FAST)),
                image,
                width,
                height,
                startAsGreyscale
        );
    }
}
