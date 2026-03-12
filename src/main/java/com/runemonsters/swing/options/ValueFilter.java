package com.runemonsters.swing.options;

import com.runemonsters.types.card.CardCost;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class ValueFilter extends JPanel {
    public enum FILTER_SYMBOL {
        EQUALS("="),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        LESS_THAN_OR_EQUAL_TO("<="),
        GREATER_THAN_OR_EQUAL_TO(">=");

        private static final Map<String, FILTER_SYMBOL> STRING_FILTER_SYMBOL_MAP = Map.ofEntries(
                Map.entry("=", EQUALS),
                Map.entry("<", LESS_THAN),
                Map.entry(">", GREATER_THAN),
                Map.entry("<=", LESS_THAN_OR_EQUAL_TO),
                Map.entry(">=", GREATER_THAN_OR_EQUAL_TO)
        );

        public static FILTER_SYMBOL get(String symbol) {
            return STRING_FILTER_SYMBOL_MAP.get(symbol);
        }

        public static String[] toStringArr() {
            return Arrays.stream(FILTER_SYMBOL.values()).map(FILTER_SYMBOL::getSymbol).toArray(String[]::new);
        }

        private final String symbol;

        FILTER_SYMBOL(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private final JCheckBox enable;
    private final JLabel label;
    private final JComboBox<String> filterOptions;
    private final JSpinner numberValue;

    public ValueFilter(String name, ActionListener filtersActionListener) {
        setLayout(new FlowLayout());

        enable = new JCheckBox();
        enable.addItemListener(e -> {
            filtersActionListener.actionPerformed(
                    new ActionEvent(enable, ActionEvent.ACTION_PERFORMED, "changed")
            );
        });
        add(enable);

        label = new JLabel(name);
        add(label);

        filterOptions = new JComboBox<>(FILTER_SYMBOL.toStringArr());
        filterOptions.addItemListener(e -> {
            filtersActionListener.actionPerformed(
                    new ActionEvent(filterOptions, ActionEvent.ACTION_PERFORMED, "changed")
            );
        });
        add(filterOptions);

        numberValue = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor) numberValue.getEditor();
        DefaultFormatter formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        numberValue.addChangeListener(e -> {
            filtersActionListener.actionPerformed(
                    new ActionEvent(numberValue, ActionEvent.ACTION_PERFORMED, "changed")
            );
        });

        add(numberValue);
    }

    @Override
    public boolean isEnabled() {
        return enable.isSelected();
    }

    public FILTER_SYMBOL getFilterSymbol() {
        return FILTER_SYMBOL.get((String) filterOptions.getSelectedItem());
    }

    public Integer getFilterValue() {
        return (Integer) numberValue.getValue();
    }
}
