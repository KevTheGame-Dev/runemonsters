package com.runemonsters.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CardImageLabel extends JLabel {
    public ImageIcon imageIcon;
    public Image image;
    public Integer width;
    public Integer height;
    public boolean isGreyscale;
    public boolean hasImage;

    public CardImageLabel (
            ImageIcon imageIcon,
            Image image,
            Integer width,
            Integer height,
            boolean startAsGreyscale
    ) {
        super(imageIcon);

        this.imageIcon = imageIcon;
        this.image = image;
        this.width = width;
        this.height = height;
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        this.hasImage = true;

        if (startAsGreyscale) {
            enableGrayscale();
        }
    }
    public CardImageLabel (
            String text,
            Integer width,
            Integer height,
            boolean startAsGreyscale
    ) {
        super(text);

        this.width = width;
        this.height = height;
        this.setMaximumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        this.hasImage = false;
        setOpaque(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 5, true);
        Border padding = new EmptyBorder(10, 10, 10, 10);
        setBorder(new CompoundBorder(border, padding));

        if (startAsGreyscale) {
            enableGrayscale();
        }
    }

    public void enableGrayscale() {
        if (hasImage) {
            imageIcon.setImage(
                    GrayFilter.createDisabledImage(
                            image.getScaledInstance(width, height, BufferedImage.SCALE_FAST)
                    )
            );
        } else {
           setBackground(Color.decode("#858585"));
        }
        isGreyscale = true;
        repaint();
    }

    public void disableGrayscale() {
        if (hasImage) {
            imageIcon.setImage(image.getScaledInstance(width, height, BufferedImage.SCALE_FAST));
        } else {
            setBackground(Color.decode("#e0e0e0"));
        }
        isGreyscale = false;
        repaint();
    }

    public void updateSize() {

    }
}
