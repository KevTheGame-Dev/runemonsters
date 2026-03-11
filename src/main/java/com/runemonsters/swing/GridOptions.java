package com.runemonsters.swing;


import com.runemonsters.swing.options.AdvancedDropdown;
import com.runemonsters.swing.options.CostFilter;
import com.runemonsters.types.card.CardType;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GridOptions extends JPanel {
    public final CostFilter guthixCostFilter;
    public final AdvancedDropdown cardTypeSelector;

    public GridOptions (ActionListener filtersActionListener) {
        guthixCostFilter = new CostFilter("Guthix Cost", filtersActionListener);
        add(guthixCostFilter);

        cardTypeSelector = new AdvancedDropdown("Type", CardType.TYPE.toStringArr(), filtersActionListener);
        add(cardTypeSelector);
    }
}
