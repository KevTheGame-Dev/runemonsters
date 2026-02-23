package com.runemonsters.swing;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class RuneMonstersPanel extends PluginPanel {

    final CardBrowser cardBrowser;

    @Inject
    public RuneMonstersPanel(CardBrowser cardBrowser) {
        this.cardBrowser = cardBrowser;

        setLayout(new GridLayout(6, 1, 0, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        final Font smallFont = FontManager.getRunescapeSmallFont();
        final Font regularFont = FontManager.getRunescapeFont();
        final Font boldFont = FontManager.getRunescapeBoldFont();
        final Font titleFont = boldFont.deriveFont(32f);

        // Title
        JLabel titleLabel = JGenerator.createLabel("RuneMonsters", titleFont);
        titleLabel.setForeground(Color.yellow);
        titleLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(titleLabel);


        // Icon Grid
        JPanel iconGridPanel = new JPanel();
        iconGridPanel.setLayout(new GridLayout(1, 4, 10, 0));

        // Discord Button
        JButton discordButton = JGenerator.createIconButton(
                new ImageIcon(ImageUtil.loadImageResource(getClass(), "/DiscordIcon.png")),
                "Discuss the TCG or request help on Discord",
                e -> {
                },
                new MouseAdapter() {}
        );
        iconGridPanel.add(discordButton);

        // Github Button
        JButton githubButton = JGenerator.createIconButton(
                new ImageIcon(ImageUtil.loadImageResource(getClass(), "/GithubIcon.png")),
                "Report an issue or contribute to development",
                e -> {
                },
                new MouseAdapter() {}
        );
        iconGridPanel.add(githubButton);

        // Patreon Button
        JButton patreonButton = JGenerator.createIconButton(
                new ImageIcon(ImageUtil.loadImageResource(getClass(), "/GithubIcon.png")),
                "Support development and card expansions",
                e -> {
                },
                new MouseAdapter() {}
        );
        iconGridPanel.add(patreonButton);

        // Help Button
        JButton helpButton = JGenerator.createIconButton(
                new ImageIcon(ImageUtil.loadImageResource(getClass(), "/HelpIcon.png")),
                "View the How-To page on the RuneMonsters website",
                e -> {
                },
                new MouseAdapter() {}
        );
        iconGridPanel.add(helpButton);

        add(iconGridPanel);

        // Card Browser
        JButton cardBrowserButton = new JButton("Card Browser");
        cardBrowserButton.setToolTipText("Opens a floating window that allows for browsing all cards");
        cardBrowserButton.setFont(regularFont);
        cardBrowserButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                cardBrowser.toggleVisible();
                revalidate();
                repaint();
            });
        });
        add(cardBrowserButton);

        // Card Browser
        JButton packsButton = new JButton("Packs");
        packsButton.setToolTipText("View and open your packs");
        packsButton.setFont(regularFont);
        add(packsButton);

        // Card Browser
        JButton deckBuilderButton = new JButton("Deck Builder");
        deckBuilderButton.setToolTipText("Opens a floating window that lets you build a deck");
        deckBuilderButton.setFont(regularFont);
        deckBuilderButton.setEnabled(false);
        add(deckBuilderButton);

        // Card Browser
        JPanel latestUnlock = new JPanel();
        JLabel latestUnlockLabel = JGenerator.createLabel("Latest Unlock", titleFont);
        latestUnlockLabel.setFont(regularFont);
        latestUnlock.add(latestUnlockLabel);
        add(latestUnlock);
    }

    private static String htmlLabel(String key, String value)
    {
        return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + value + "</span></body></html>";
    }
}

