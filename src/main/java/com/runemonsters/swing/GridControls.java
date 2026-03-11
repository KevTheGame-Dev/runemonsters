package com.runemonsters.swing;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GridControls extends JPanel {
    private final BorderLayout layout;
    public final GridSearchBar searchBar;
    public final GridOptions options;
    public GridControls(Integer windowWidth, Integer borderPadding, ActionListener filtersActionListener) {
        layout = new BorderLayout();
        layout.setHgap((int) (windowWidth * 0.1));
        setLayout(layout);
        setBorder(new EmptyBorder(borderPadding, borderPadding, 10, borderPadding + 10));

        searchBar = new GridSearchBar(filtersActionListener);
        add(searchBar, BorderLayout.CENTER);

        options = new GridOptions(filtersActionListener);
        options.setVisible(false);
        JButton optionsButton = JGenerator.createIconButton(
                new ImageIcon(ImageUtil.loadImageResource(getClass(), "/FilterIcon.png")),
                "Filter cards",
                e -> {
                    options.setVisible(!options.isVisible());
                },
                new MouseAdapter() {}
        );
        optionsButton.setBackground(Color.DARK_GRAY);
        add(optionsButton, BorderLayout.EAST);
        add(options, BorderLayout.SOUTH);
    }

    public void onResize(Integer windowWidth) {
        layout.setHgap((int) (windowWidth * 0.1));
    }
}
