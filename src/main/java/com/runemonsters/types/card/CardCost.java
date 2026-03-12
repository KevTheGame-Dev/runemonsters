package com.runemonsters.types.card;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CardCost {
    public enum COST_TYPE {
        GUTHIX("Guthix"),
        SARADOMIN("Saradomin"),
        BANDOS("Bandos"),
        ZAMORAK("Zamorak"),
        ARMADYL("Armadyl"),
        ZAROS("Zaros"),
        SEREN("Seren");

        private static final Map<String, COST_TYPE> STRING_COST_TYPE_MAP = Map.ofEntries(
                Map.entry("Guthix", GUTHIX),
                Map.entry("Saradomin", SARADOMIN),
                Map.entry("Bandos", BANDOS),
                Map.entry("Zamorak", ZAMORAK),
                Map.entry("Armadyl", ARMADYL),
                Map.entry("Zaros", ZAROS),
                Map.entry("Seren", SEREN)
        );

        public static COST_TYPE get(String stringVal) {
            return STRING_COST_TYPE_MAP.getOrDefault(stringVal, GUTHIX);
        }

        public static String[] toStringArr() {
            return Arrays.stream(COST_TYPE.values()).map(COST_TYPE::toString).toArray(String[]::new);
        }

        public static List<COST_TYPE> toList() {
            return Arrays.stream(COST_TYPE.values()).collect(Collectors.toList());
        }

        private final String stringVal;

        COST_TYPE(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }

    public static CardCost fromString(String costString) {
        String[] costs = StringUtils.split(costString, ",");

        int guthixCost = 0;
        int saradominCost = 0;
        int bandosCost = 0;
        int zamorakCost = 0;
        int armadylCost = 0;
        int zarosCost = 0;
        int serenCost = 0;

        for (String cost : costs) {
            String trimmed = cost.trim();
            if (cost.contains(COST_TYPE.GUTHIX.toString())) {
                guthixCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE.SARADOMIN.toString())) {
                saradominCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE.BANDOS.toString())) {
                bandosCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE.ZAMORAK.toString())) {
                zamorakCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE.ARMADYL.toString())) {
                armadylCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE.ZAROS.toString())) {
                zarosCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE.SEREN.toString())) {
                serenCost = Integer.parseInt(trimmed.substring(0, 1));
            }
        }

        return new CardCost(
                guthixCost,
                saradominCost,
                bandosCost,
                zamorakCost,
                armadylCost,
                zarosCost,
                serenCost
        );
    }

    public final Integer guthixCost;
    public final Integer saradominCost;
    public final Integer bandosCost;
    public final Integer zamorakCost;
    public final Integer armadylCost;
    public final Integer zarosCost;
    public final Integer serenCost;

    public CardCost (
            Integer guthixCost,
            Integer saradominCost,
            Integer bandosCost,
            Integer zamorakCost,
            Integer armadylCost,
            Integer zarosCost,
            Integer serenCost
    ) {
        this.guthixCost = guthixCost;
        this.saradominCost = saradominCost;
        this.bandosCost = bandosCost;
        this.zamorakCost = zamorakCost;
        this.armadylCost = armadylCost;
        this.zarosCost = zarosCost;
        this.serenCost = serenCost;
    }

    public String toString() {
        String cardCost = "";
        if (guthixCost > 0) {
            cardCost += cardCost + guthixCost + " " + COST_TYPE.GUTHIX;
        }
        if (saradominCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + saradominCost + " " + COST_TYPE.SARADOMIN;
        }
        if (bandosCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + bandosCost + " " + COST_TYPE.BANDOS;
        }
        if (zamorakCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + zamorakCost + " " + COST_TYPE.ZAMORAK;
        }
        if (armadylCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + armadylCost + " " + COST_TYPE.ARMADYL;
        }
        if (zarosCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + zarosCost + " " + COST_TYPE.ZAROS;
        }
        if (serenCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + serenCost + " " + COST_TYPE.SEREN;
        }

        if (cardCost.isEmpty()) {
            return "0 Guthix";
        } else {
            return cardCost;
        }
    }

    public boolean isExclusively(List<COST_TYPE> validCostTypes) {
        List<COST_TYPE> invalidCostTypes = COST_TYPE.toList();
        invalidCostTypes.removeAll(validCostTypes);
        log.debug(invalidCostTypes.toString());
        log.debug(Arrays.toString(new Integer[]{guthixCost, saradominCost, bandosCost, zamorakCost, armadylCost, zarosCost, serenCost}));
        for (COST_TYPE type : invalidCostTypes) {
            switch (type) {
                case GUTHIX:
                    if (guthixCost > 0) return false;
                    break;
                case SARADOMIN:
                    if (saradominCost > 0) return false;
                    break;
                case BANDOS:
                    if (bandosCost > 0) return false;
                    break;
                case ZAMORAK:
                    if (zamorakCost > 0) return false;
                    break;
                case ARMADYL:
                    if (armadylCost > 0) return false;
                    break;
                case ZAROS:
                    if (zarosCost > 0) return false;
                    break;
                case SEREN:
                    if (serenCost > 0) return false;
                    break;
                default:
                    return true;
            }
        }
        return true;
    }
}
