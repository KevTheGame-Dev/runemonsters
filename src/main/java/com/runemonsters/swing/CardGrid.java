package com.runemonsters.swing;

import com.runemonsters.CardUtilities;
import com.runemonsters.types.Card;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CardGrid extends JPanel {
    private final Integer LARGE_CARD_WIDTH = 300;
    private final Integer LARGE_CARD_HEIGHT = 400;
    private final Integer SMALL_CARD_WIDTH = 150;
    private final Integer SMALL_CARD_HEIGHT = 200;

    private final Map<String, CardImageLabel> CARD_LABELS = new HashMap<>();
    private final JPanel cardGridPanel;
    private final JScrollPane scrollPane;

    public CardGrid (Integer windowWidth, Integer windowHeight, Integer headerHeight, Integer borderPadding) {
        Integer columnCount = calculateColumnCount(windowWidth, borderPadding);
        log.debug("Column count " + columnCount + ", window width" + getSize());
        this.cardGridPanel = new JPanel();
        cardGridPanel.setLayout(new GridLayout(0, columnCount, 10, 10));
        cardGridPanel.setBorder(new EmptyBorder(10, borderPadding, 10, borderPadding));

        this.scrollPane = new JScrollPane(cardGridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
        add(scrollPane, BorderLayout.CENTER);

        setupCardGrid(cardGridPanel);

        log.debug("label count = {}", CARD_LABELS.size());
    }

    public void onResize(Integer windowWidth, Integer windowHeight, Integer headerHeight, Integer borderPadding) {
        Integer columnCount = calculateColumnCount(windowWidth, borderPadding);
        log.debug("Column count " + columnCount + ", window width" + getSize());
        cardGridPanel.setLayout(new GridLayout(0, columnCount, 10, 10));
        scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
    }

    public void refreshUnlocked() {
        for (String cardId : CardUtilities.CARDS_BY_ID.keySet()) {
            if (!CARD_LABELS.containsKey(cardId)) {
                continue;
            }
            if (CardUtilities.UNLOCKED_CARDS.get(cardId) == null && !CARD_LABELS.get(cardId).isGreyscale) {
                CARD_LABELS.get(cardId).enableGrayscale();
            } else if (CardUtilities.UNLOCKED_CARDS.get(cardId) != null && CARD_LABELS.get(cardId).isGreyscale) {
                CARD_LABELS.get(cardId).disableGrayscale();
            }
        }
    }

    private void setupCardGrid(JPanel cardGridPanel) {
        for (String cardId : CardUtilities.CARDS_BY_ID.keySet()) {
            try {
                String imagePath = CardUtilities.CARD_IMAGES_PATH + cardId + ".png";
                BufferedImage img = ImageIO.read(new File(imagePath));

                boolean startAsGreyscale = CardUtilities.UNLOCKED_CARDS.get(cardId) == null;

                CardImageLabel cardLabel = new CardImageLabel(
                        new ImageIcon(img.getScaledInstance(
                                LARGE_CARD_WIDTH, LARGE_CARD_HEIGHT, BufferedImage.SCALE_FAST)
                        ),
                        img,
                        LARGE_CARD_WIDTH,
                        LARGE_CARD_HEIGHT,
                        startAsGreyscale
                );
                CARD_LABELS.put(cardId, cardLabel);

                cardGridPanel.add(cardLabel);
            } catch (IOException e) {
                boolean startAsGreyscale = CardUtilities.UNLOCKED_CARDS.get(cardId) == null;

                CardImageLabel cardLabel = new CardImageLabel(
                        CardUtilities.getCardById(cardId).toFallbackText(),
                        LARGE_CARD_WIDTH,
                        LARGE_CARD_HEIGHT,
                        startAsGreyscale
                );
                CARD_LABELS.put(cardId, cardLabel);

                cardGridPanel.add(cardLabel);
            }
        }
    }

    private Integer calculateColumnCount (Integer windowWidth, Integer borderPadding) {
        //Dimension size = getSize();
        int calculatedColumns = (int) Math.floor((double) ((windowWidth - (borderPadding * 2)) / LARGE_CARD_WIDTH));
        return Math.max(1, calculatedColumns);
    }
}
