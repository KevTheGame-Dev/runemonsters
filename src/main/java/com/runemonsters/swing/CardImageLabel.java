package com.runemonsters.swing;

import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class CardImageLabel extends JLabel {
    public ImageIcon imageIcon;
    public Image image;
    public Integer width;
    public Integer height;
    public boolean isGreyscale;

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

        if (startAsGreyscale) {
            enableGrayscale();
        }
    }

    public void enableGrayscale() {
        imageIcon.setImage(
                GrayFilter.createDisabledImage(
                        image.getScaledInstance(width, height, BufferedImage.SCALE_FAST)
                )
        );
        isGreyscale = true;
        repaint();
    }

    public void disableGrayscale() {
        imageIcon.setImage(image.getScaledInstance(width, height, BufferedImage.SCALE_FAST));
        isGreyscale = false;
        repaint();
    }

    public void updateSize() {

    }
}
