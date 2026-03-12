package com.runemonsters.swing;

import com.runemonsters.CardUtilities;
import com.runemonsters.swing.options.CostOptionFilter;
import com.runemonsters.swing.options.MatchFilter;
import com.runemonsters.swing.options.CostFilter;
import com.runemonsters.swing.options.ValueFilter;
import com.runemonsters.types.Card;
import com.runemonsters.types.card.CardCost;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CardGrid extends JPanel {
    private final Integer LARGE_CARD_WIDTH = 300;
    private final Integer LARGE_CARD_HEIGHT = 400;
    private final Integer SMALL_CARD_WIDTH = 150;
    private final Integer SMALL_CARD_HEIGHT = 200;
    private final Integer GRID_SPACING = 10;

    private final Map<String, CardImageLabel> CARD_LABELS = new HashMap<>();
    private final JPanel cardGridPanel;
    private final JScrollPane scrollPane;

    public CardGrid (Integer windowWidth, Integer windowHeight, Integer borderPadding) {
        setLayout(new BorderLayout());
        Integer columnCount = calculateColumnCount(windowWidth, borderPadding);
        this.cardGridPanel = new JPanel();
        cardGridPanel.setLayout(new GridLayout(0, columnCount, GRID_SPACING, GRID_SPACING));
        cardGridPanel.setBorder(new EmptyBorder(10, borderPadding, 10, borderPadding));

        this.scrollPane = new JScrollPane(cardGridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
        add(scrollPane, BorderLayout.CENTER);

        setupCardGrid();

        log.debug("label count = {}", CARD_LABELS.size());
    }

    public void onResize(Integer windowWidth, Integer windowHeight, Integer borderPadding) {
        Integer columnCount = calculateColumnCount(windowWidth, borderPadding);
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

    public void rebuildGridWithFilters(
            CostOptionFilter costOptionFilter,
            CostFilter guthixFilter,
            CostFilter saradominFilter,
            CostFilter bandosFilter,
            CostFilter zamorakFilter,
            CostFilter armadylFilter,
            CostFilter zarosFilter,
            CostFilter serenFilter,
            MatchFilter attackTypeFilter,
            ValueFilter attackCostFilter,
            MatchFilter defenseTypeFilter,
            ValueFilter defenseCostFilter,
            ValueFilter healthCostFilter,
            MatchFilter keywordFilter,
            MatchFilter cardTypeFilter,
            MatchFilter cardMonsterSubTypeFilter,
            MatchFilter cardSpellSubTypeFilter,
            MatchFilter cardEquipmentSubTypeFilter,
            MatchFilter cardRarityFilter,
            MatchFilter cardSetFilter
    ) {
        CARD_LABELS.clear();
        cardGridPanel.removeAll();
        for (String cardId : CardUtilities.CARDS_BY_ID.keySet()) {
            Card card = CardUtilities.CARDS_BY_ID.get(cardId);

            boolean exclusiveCost = costOptionFilter.getFilterOption() == CostOptionFilter.COST_OPTION.EXCLUSIVELY;
            CostFilter[] filters = new CostFilter[]{
                    guthixFilter, saradominFilter, bandosFilter,
                    zamorakFilter, armadylFilter, zarosFilter, serenFilter
            };
            List<CardCost.COST_TYPE> enabledTypes = new ArrayList<>();
            for (CostFilter filter : filters) {
                if (filter.isEnabled()) {
                    enabledTypes.add(filter.costType);
                }
            }
            log.debug("{} : {} = {}", cardId, enabledTypes, card.cost.isExclusively(enabledTypes));
            if (exclusiveCost && !card.cost.isExclusively(enabledTypes)) continue;

            if (guthixFilter.isEnabled() && isNotValidCost(card.cost.guthixCost, guthixFilter)) continue;
            if (saradominFilter.isEnabled() && isNotValidCost(card.cost.saradominCost, saradominFilter)) continue;
            if (bandosFilter.isEnabled() && isNotValidCost(card.cost.bandosCost, bandosFilter)) continue;
            if (zamorakFilter.isEnabled() && isNotValidCost(card.cost.zamorakCost, zamorakFilter)) continue;
            if (armadylFilter.isEnabled() && isNotValidCost(card.cost.zamorakCost, armadylFilter)) continue;
            if (zarosFilter.isEnabled() && isNotValidCost(card.cost.zarosCost, zarosFilter)) continue;
            if (serenFilter.isEnabled() && isNotValidCost(card.cost.serenCost, serenFilter)) continue;

            if (isNotFilterMatch(card.attackType.toString(), attackTypeFilter.getFilterValue())) continue;
            if (attackCostFilter.isEnabled() && isNotValidCost(card.attackValue, attackCostFilter)) continue;
            if (isNotFilterMatch(card.defensiveWeakness.toString(), defenseTypeFilter.getFilterValue())) continue;
            if (defenseCostFilter.isEnabled() && isNotValidCost(card.defensiveValue, defenseCostFilter)) continue;
            if (healthCostFilter.isEnabled() && isNotValidCost(card.health, healthCostFilter)) continue;

            if (doesListNotContainFilter(card.keywords.getStringList(), keywordFilter.getFilterValue())) continue;

            if (isNotFilterMatch(card.type.toString(), cardTypeFilter.getFilterValue())) continue;
            if (doesListNotContainFilter(card.subTypes.getMonsterStringList(), cardMonsterSubTypeFilter.getFilterValue())) continue;
            if (doesListNotContainFilter(card.subTypes.getSpellStringList(), cardSpellSubTypeFilter.getFilterValue())) continue;
            if (doesListNotContainFilter(card.subTypes.getEquipmentStringList(), cardEquipmentSubTypeFilter.getFilterValue())) continue;

            if (isNotFilterMatch(card.rarity.toString(), cardRarityFilter.getFilterValue())) continue;
            if (isNotFilterMatch(card.set.toString(), cardSetFilter.getFilterValue())) continue;

            buildCardTile(cardId);
        }
        cardGridPanel.revalidate();
        cardGridPanel.repaint();
    }

    private void setupCardGrid() {
        for (String cardId : CardUtilities.CARDS_BY_ID.keySet()) {
            buildCardTile(cardId);
        }
    }

    private void buildCardTile (String cardId) {
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


    private Integer calculateColumnCount (Integer windowWidth, Integer borderPadding) {
        int calculatedColumns = (int) Math.floor((double) (windowWidth - (borderPadding * 2)) / (LARGE_CARD_WIDTH + GRID_SPACING));
        return Math.max(1, calculatedColumns);
    }

    private boolean isValidCost(Integer cardCost, ValueFilter filter) {
        ValueFilter.FILTER_SYMBOL filterSymbol = filter.getFilterSymbol();
        Integer filterValue = filter.getFilterValue();

        switch (filterSymbol) {
            case LESS_THAN:
                return cardCost < filterValue;
            case GREATER_THAN:
                return cardCost > filterValue;
            case LESS_THAN_OR_EQUAL_TO:
                return cardCost <= filterValue;
            case GREATER_THAN_OR_EQUAL_TO:
                return cardCost >= filterValue;
            default:
                return Objects.equals(cardCost, filterValue);
        }
    }

    private boolean isNotValidCost(Integer cardCost, ValueFilter filter) {
        return !isValidCost(cardCost, filter);
    }

    private boolean isFilterMatch(String cardValueString, String filterValueString) {
        return Objects.equals(cardValueString, filterValueString);
    }

    private boolean isNotFilterMatch(String cardValueString, String filterValueString) {
       return !isFilterMatch(cardValueString, filterValueString) &&
                !Objects.equals(filterValueString, GridOptions.ANY_OPTION);
    }

    private boolean doesListContainFilter(List<String> value, String filterValueString) {
        return value.contains(filterValueString) || Objects.equals(filterValueString, GridOptions.ANY_OPTION);
    }

    private boolean doesListNotContainFilter(List<String> value, String filterValueString) {
        return !doesListContainFilter(value, filterValueString);
    }
}
