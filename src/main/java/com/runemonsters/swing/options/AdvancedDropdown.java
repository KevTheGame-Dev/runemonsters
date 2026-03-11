package com.runemonsters.swing.options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class AdvancedDropdown extends JPanel {
    public enum FILTER {
        IS("is"),
        IS_NOT("is not");

        private static final Map<String, FILTER> STRING_FILTER_MAP = Map.ofEntries(
                Map.entry("is", IS),
                Map.entry("is not", IS_NOT)
        );

        public static FILTER get(String symbol) {
            return STRING_FILTER_MAP.get(symbol);
        }

        public static String[] toStringArr() {
            return Arrays.stream(FILTER.values()).map(FILTER::getStringVal).toArray(String[]::new);
        }

        private final String stringVal;

        FILTER(String stringVal) {
            this.stringVal = stringVal;
        }

        public String getStringVal() {
            return stringVal;
        }
    }

    private final JLabel label;
    private final JComboBox<String> filterOptions;
    private final JComboBox<String> filterValue;

    public AdvancedDropdown(String filterName, String[] filterValues, ActionListener filtersActionListener) {
        setLayout(new FlowLayout());

        label = new JLabel(filterName);
        add(label);

        filterOptions = new JComboBox<>(FILTER.toStringArr());
        filterOptions.addItemListener(e -> {
            filtersActionListener.actionPerformed(
                    new ActionEvent(filterOptions, ActionEvent.ACTION_PERFORMED, "changed")
            );
        });
        add(filterOptions);

        filterValue = new JComboBox<>(filterValues);
        filterValue.addItemListener(e -> {
            filtersActionListener.actionPerformed(
                    new ActionEvent(filterValue, ActionEvent.ACTION_PERFORMED, "changed")
            );
        });
        add(filterValue);
    }

    public FILTER getFilterOption() {
        return FILTER.get((String) filterOptions.getSelectedItem());
    }

    public String getFilterValue() {
        return (String) filterValue.getSelectedItem();
    }
}
