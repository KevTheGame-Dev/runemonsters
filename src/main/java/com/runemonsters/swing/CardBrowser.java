package com.runemonsters.swing;

import com.runemonsters.RuneMonstersPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.inject.Inject;
import javax.swing.JFrame;

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

        setVisible(!isVisible());
    }

    private void updateFilters() {
        cardGrid.rebuildGridWithFilters(
                gridControls.options.costOptionFilter,
                gridControls.options.guthixCostFilter,
                gridControls.options.saradominCostFilter,
                gridControls.options.bandosCostFilter,
                gridControls.options.zamorakCostFilter,
                gridControls.options.armadylCostFilter,
                gridControls.options.zarosCostFilter,
                gridControls.options.serenCostFilter,
                gridControls.options.attackTypeFilter,
                gridControls.options.attackCostFilter,
                gridControls.options.defenseTypeFilter,
                gridControls.options.defenseCostFilter,
                gridControls.options.healthCostFilter,
                gridControls.options.keywordFilter,
                gridControls.options.cardTypeFilter,
                gridControls.options.cardMonsterSubTypeFilter,
                gridControls.options.cardSpellSubTypeFilter,
                gridControls.options.cardEquipmentSubTypeFilter,
                gridControls.options.cardRarityFilter,
                gridControls.options.cardSetFilter
        );
    }
}
