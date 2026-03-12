package com.runemonsters.swing;


import com.runemonsters.swing.options.CostOptionFilter;
import com.runemonsters.swing.options.MatchFilter;
import com.runemonsters.swing.options.CostFilter;
import com.runemonsters.swing.options.ValueFilter;
import com.runemonsters.types.card.AttackType;
import com.runemonsters.types.card.CardCost;
import com.runemonsters.types.card.CardKeywords;
import com.runemonsters.types.card.CardRarity;
import com.runemonsters.types.card.CardSet;
import com.runemonsters.types.card.CardSubTypes;
import com.runemonsters.types.card.CardType;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GridOptions extends JPanel {
    public static final String ANY_OPTION = "Any";

    private final int COLUMN_WIDTH = 200;
    private final int GRID_SPACING = 5;

    public final CostOptionFilter costOptionFilter;
    public final CostFilter guthixCostFilter;
    public final CostFilter saradominCostFilter;
    public final CostFilter bandosCostFilter;
    public final CostFilter zamorakCostFilter;
    public final CostFilter armadylCostFilter;
    public final CostFilter zarosCostFilter;
    public final CostFilter serenCostFilter;

    public final MatchFilter attackTypeFilter;
    public final ValueFilter attackCostFilter;
    public final MatchFilter defenseTypeFilter;
    public final ValueFilter defenseCostFilter;
    public final ValueFilter healthCostFilter;

    public final MatchFilter keywordFilter;

    public final MatchFilter cardTypeFilter;
    public final MatchFilter cardMonsterSubTypeFilter;
    public final MatchFilter cardSpellSubTypeFilter;
    public final MatchFilter cardEquipmentSubTypeFilter;

    public final MatchFilter cardRarityFilter;
    public final MatchFilter cardSetFilter;

    public GridOptions (Integer windowWidth, Integer borderPadding, ActionListener filtersActionListener) {
        int columnCount = calculateColumnCount(windowWidth, borderPadding);
        setLayout(new FlowLayout());


        JPanel costsSection = new JPanel();
        costsSection.setLayout(new GridLayout(0, 2));
        costOptionFilter = new CostOptionFilter(filtersActionListener);
        guthixCostFilter = new CostFilter(CardCost.COST_TYPE.GUTHIX, filtersActionListener);
        saradominCostFilter = new CostFilter(CardCost.COST_TYPE.SARADOMIN, filtersActionListener);
        bandosCostFilter = new CostFilter(CardCost.COST_TYPE.BANDOS, filtersActionListener);
        zamorakCostFilter = new CostFilter(CardCost.COST_TYPE.ZAMORAK, filtersActionListener);
        armadylCostFilter = new CostFilter(CardCost.COST_TYPE.ARMADYL, filtersActionListener);
        zarosCostFilter = new CostFilter(CardCost.COST_TYPE.ZAROS, filtersActionListener);
        serenCostFilter = new CostFilter(CardCost.COST_TYPE.SEREN, filtersActionListener);
        costsSection.add(costOptionFilter);
        costsSection.add(guthixCostFilter);
        costsSection.add(saradominCostFilter);
        costsSection.add(bandosCostFilter);
        costsSection.add(zamorakCostFilter);
        costsSection.add(armadylCostFilter);
        costsSection.add(zarosCostFilter);
        costsSection.add(serenCostFilter);
        add(costsSection);

        JPanel combatSection = new JPanel();
        combatSection.setLayout(new GridLayout(0, 2));
        attackTypeFilter = new MatchFilter(
                "Attack Type",
                injectAnyOption(AttackType.ATTACK_TYPE.toStringArr()),
                filtersActionListener
        );
        attackCostFilter = new ValueFilter("Attack Value", filtersActionListener);
        defenseTypeFilter = new MatchFilter(
                "Defense Type",
                injectAnyOption(AttackType.ATTACK_TYPE.toStringArr()),
                filtersActionListener
        );
        defenseCostFilter = new ValueFilter("Defense Value", filtersActionListener);
        healthCostFilter = new ValueFilter("Health Value", filtersActionListener);
        combatSection.add(attackTypeFilter);
        combatSection.add(attackCostFilter);
        combatSection.add(defenseTypeFilter);
        combatSection.add(defenseCostFilter);
        combatSection.add(healthCostFilter);
        add(combatSection);

        JPanel miscSection = new JPanel();
        miscSection.setLayout(new GridLayout(0, 2));
        keywordFilter = new MatchFilter(
                "Keyword",
                injectAnyOption(CardKeywords.KEYWORD.toStringArr()),
                filtersActionListener
        );
        miscSection.add(keywordFilter);

        cardTypeFilter = new MatchFilter(
                "Type",
                injectAnyOption(CardType.TYPE.toStringArr()),
                filtersActionListener
        );
        miscSection.add(cardTypeFilter);
        cardMonsterSubTypeFilter = new MatchFilter(
                "Monster SubType",
                injectAnyOption(CardSubTypes.MONSTER_SUBTYPE.toStringArr()),
                filtersActionListener
        );
        miscSection.add(cardMonsterSubTypeFilter);
        cardSpellSubTypeFilter = new MatchFilter(
                "Monster SubType",
                injectAnyOption(CardSubTypes.SPELL_SUBTYPE.toStringArr()),
                filtersActionListener
        );
        miscSection.add(cardSpellSubTypeFilter);
        cardEquipmentSubTypeFilter = new MatchFilter(
                "Monster SubType",
                injectAnyOption(CardSubTypes.EQUIPMENT_SUBTYPE.toStringArr()),
                filtersActionListener
        );
        miscSection.add(cardEquipmentSubTypeFilter);

        cardRarityFilter = new MatchFilter(
                "Rarity",
                injectAnyOption(CardRarity.RARITY.toStringArr()),
                filtersActionListener
        );
        miscSection.add(cardRarityFilter);
        cardSetFilter = new MatchFilter(
                "Set",
                injectAnyOption(CardSet.SET.toStringArr()),
                filtersActionListener
        );
        miscSection.add(cardSetFilter);
        add(miscSection);
    }


    private Integer calculateColumnCount (Integer windowWidth, Integer borderPadding) {
        int calculatedColumns = (int) Math.floor((double) (windowWidth - (borderPadding * 2)) / (COLUMN_WIDTH + GRID_SPACING));
        return Math.max(1, calculatedColumns);
    }

    private String[] injectAnyOption(String[] options) {
        return ArrayUtils.addAll(new String[]{ ANY_OPTION }, options);
    }
}
