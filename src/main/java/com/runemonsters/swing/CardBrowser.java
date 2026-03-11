package com.runemonsters.swing;

import com.runemonsters.CardUtilities;
import com.runemonsters.RuneMonstersPlugin;
import com.runemonsters.swing.options.AdvancedDropdown;
import com.runemonsters.swing.options.CostFilter;
import com.runemonsters.types.card.CardType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
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

@Slf4j
public class CardBrowser extends JFrame {
    private final RuneMonstersPlugin plugin;


    private Integer windowWidth = 1200;
    private Integer windowHeight = 600;
    private Integer headerHeight = 300;
    private Integer borderPadding = 20;

    private final CardGrid cardGrid;
    private final GridControls gridControls;

    @Inject
    public CardBrowser(RuneMonstersPlugin plugin) {
        this.plugin = plugin;

        setTitle("RuneMonsters Card Browser");
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setLayout(new BorderLayout());

        gridControls = new GridControls(
                windowWidth,
                borderPadding,
                e -> {
                    updateFilters();
                }
        );
        add(gridControls, BorderLayout.NORTH);

        cardGrid = new CardGrid(windowWidth, windowHeight, borderPadding);
        add(cardGrid, BorderLayout.CENTER);
        cardGrid.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                cardGrid.onResize(c.getWidth(), c.getHeight(), borderPadding);
                gridControls.onResize(c.getWidth());
            }
        });
        addWindowStateListener(new WindowStateListener()
        {
            public void windowStateChanged(WindowEvent e) {
                Component c = (Component) e.getSource();
                cardGrid.onResize(c.getWidth(), c.getHeight(), borderPadding);
                gridControls.onResize(c.getWidth());
            }
        });

        pack();
    }

    public void toggleVisible() {
        cardGrid.refreshUnlocked();
        //

        setVisible(!isVisible());
    }

    private void updateFilters() {
        Boolean guthixCostEnabled = gridControls.options.guthixCostFilter.isEnabled();
        CostFilter.FILTER_SYMBOL guthixCostFilterFilterSymbol = gridControls.options.guthixCostFilter.getFilterSymbol();
        Integer guthixCostFilterValue = gridControls.options.guthixCostFilter.getFilterValue();

        AdvancedDropdown.FILTER cardTypeFilterOption = gridControls.options.cardTypeSelector.getFilterOption();
        CardType.TYPE cardTypeFilterValue = CardType.TYPE.get(gridControls.options.cardTypeSelector.getFilterValue());

        cardGrid.rebuildGridWithFilters(cardTypeFilterValue);
    }
}
