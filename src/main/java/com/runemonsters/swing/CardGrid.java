package com.runemonsters.swing;

import com.runemonsters.CardUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CardGrid extends JPanel {
    private final Integer LARGE_CARD_WIDTH = 300;
    private final Integer LARGE_CARD_HEIGHT = 400;
    private final Integer SMALL_CARD_WIDTH = 150;
    private final Integer SMALL_CARD_HEIGHT = 200;

    private final Map<String, CardImageLabel> CARD_LABELS = new HashMap<>();

    public CardGrid (Integer windowWidth, Integer windowHeight, Integer headerHeight, Integer borderPadding) {
        Integer columnCount = calculateColumnCount();
        Integer rowCount = calculateRowCount(columnCount);
        JPanel cardGridPanel = new JPanel();
        cardGridPanel.setLayout(new GridLayout(rowCount, columnCount, 10, 0));

        JScrollPane scrollPane = new JScrollPane(cardGridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(windowWidth - (borderPadding * 2), windowHeight));
        add(scrollPane);

        setupCardGrid(cardGridPanel);

        System.out.println("RuneMonsters: label count = " + CARD_LABELS.size());
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
            System.out.println("Runemonsters: cardId image " + cardId);
            if (!Objects.equals(cardId, "GIEL-EN000101")) {
                continue;
            }
            try {
                String imagePath = CardUtilities.CARD_IMAGES_PATH + cardId + ".png";
                BufferedImage img = ImageIO.read(new File(imagePath));

                Boolean startAsGreyscale = CardUtilities.UNLOCKED_CARDS.get(cardId) == null;

                CardImageLabel cardLabel = JGenerator.createImageLabel(
                    img,
                    LARGE_CARD_WIDTH,
                    LARGE_CARD_HEIGHT,
                    startAsGreyscale
                );
                CARD_LABELS.put(cardId, cardLabel);

                cardGridPanel.add(cardLabel);
            } catch (IOException e) {
//                throw new RuntimeException(e);
            }
        }
    }

    private Integer calculateColumnCount () {
        Dimension size = getSize();
        int calculatedColumns = (int) Math.floor((double) (size.width - 200) / LARGE_CARD_WIDTH);
        return Math.max(1, calculatedColumns);
    }

    private Integer calculateRowCount (Integer columnCount) {
        int calculatedRows = (int) Math.floor((double) CardUtilities.getNumberOfUnlockedCards() / columnCount);
        return Math.max(1, calculatedRows);
    }
}
