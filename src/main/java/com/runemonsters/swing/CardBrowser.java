package com.runemonsters.swing;

import com.runemonsters.CardUtilities;
import com.runemonsters.RuneMonstersPlugin;
import net.runelite.client.ui.ColorScheme;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class CardBrowser extends JFrame {
    private final RuneMonstersPlugin plugin;


    private Integer windowWidth = 1200;
    private Integer windowHeight = 600;
    private Integer headerHeight = 300;
    private Integer borderPadding = 20;

    private final CardGrid cardGrid;

    @Inject
    public CardBrowser(RuneMonstersPlugin plugin) {
        this.plugin = plugin;

        setTitle("RuneMonsters Card Browser");
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setPreferredSize(new Dimension(windowWidth, windowHeight));

        cardGrid = new CardGrid(windowWidth, windowHeight, headerHeight, borderPadding);
        add(cardGrid, BorderLayout.CENTER);
        cardGrid.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component)evt.getSource();
                cardGrid.onResize(c.getWidth(), windowHeight, headerHeight, borderPadding);
            }
        });

        pack();
    }

    public void toggleVisible() {
//        Map<String, Integer> unlockedCardIds = CardUtilities.getUnlockedCards();
//        String stringifiedCardIds = unlockedCardIds.toString();//unlockedCardIds.keySet().stream().map(String::valueOf).collect(Collectors.joining(","));
//        System.out.println("RuneMonsters: " + stringifiedCardIds);
//        add(new JLabel("cards: " + stringifiedCardIds));
        //add(new JLabel("testing"));
        cardGrid.refreshUnlocked();
        //

        setVisible(!isVisible());
    }
}
