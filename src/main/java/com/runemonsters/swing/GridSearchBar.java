package com.runemonsters.swing;

import com.formdev.flatlaf.FlatClientProperties;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GridSearchBar extends JPanel {
    public static final Integer PREFERRED_HEIGHT = 64;

    private final JTextField searchField;
    public GridSearchBar(ActionListener filtersActionListener) {
        super();
        setBackground(Color.DARK_GRAY);
        // we only want to limit height, so set width arbitrarily high
        setMaximumSize(new Dimension(1000, PREFERRED_HEIGHT));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));

        ImageIcon searchIcon = new ImageIcon(ImageUtil.loadImageResource(getClass(), "/SearchIcon.png"));
        JLabel iconLabel = new JLabel(searchIcon);
        iconLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
        add(iconLabel, BorderLayout.WEST);

        searchField = new JTextField();
        add(searchField, BorderLayout.CENTER);
    }
}
