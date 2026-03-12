package com.runemonsters.swing.options;

import com.runemonsters.types.card.CardCost;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class CostFilter extends ValueFilter {
    public final CardCost.COST_TYPE costType;

    public CostFilter(CardCost.COST_TYPE costType, ActionListener filtersActionListener) {
        super(costType + " Cost", filtersActionListener);

        this.costType = costType;
    }
}
