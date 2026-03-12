package com.runemonsters.swing.options;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class CostOptionFilter extends JPanel {
    public enum COST_OPTION {
        INCLUSIVELY("Inclusively"),
        EXCLUSIVELY("Exclusively");

        private static final Map<String, COST_OPTION> STRING_FILTER_SYMBOL_MAP = Map.ofEntries(
                Map.entry("Inclusively", INCLUSIVELY),
                Map.entry("Exclusively", EXCLUSIVELY)
        );

        public static COST_OPTION get(String symbol) {
            return STRING_FILTER_SYMBOL_MAP.get(symbol);
        }

        public static String[] toStringArr() {
            return Arrays.stream(COST_OPTION.values()).map(COST_OPTION::getSymbol).toArray(String[]::new);
        }

        private final String symbol;

        COST_OPTION(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private final JComboBox<String> filterOptions;

    public CostOptionFilter(ActionListener filtersActionListener) {
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Cost is");
        add(label);

        filterOptions = new JComboBox<>(COST_OPTION.toStringArr());
        filterOptions.addItemListener(e -> {
            filtersActionListener.actionPerformed(
                    new ActionEvent(filterOptions, ActionEvent.ACTION_PERFORMED, "changed")
            );
        });
        add(filterOptions);
    }

    public COST_OPTION getFilterOption() {
        return COST_OPTION.get((String) filterOptions.getSelectedItem());
    }
}
