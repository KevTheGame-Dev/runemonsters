package com.runemonsters.types.card;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class CardCost {
    public enum COST_TYPE {
        GUTHIX,
        SARADOMIN,
        BANDOS,
        ZAMORAK,
        ARMADYL,
        ZAROS,
        SEREN
    }
    private static final Map<COST_TYPE, String> COST_TYPE_TO_STRING_MAP = Map.ofEntries(
            Map.entry(COST_TYPE.GUTHIX, "Guthix"),
            Map.entry(COST_TYPE.SARADOMIN, "Saradomin"),
            Map.entry(COST_TYPE.BANDOS, "Bandos"),
            Map.entry(COST_TYPE.ZAMORAK, "Zamorak"),
            Map.entry(COST_TYPE.ARMADYL, "Armadyl"),
            Map.entry(COST_TYPE.ZAROS, "Zaros"),
            Map.entry(COST_TYPE.SEREN, "Seren")
    );

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
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.GUTHIX))) {
                guthixCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.SARADOMIN))) {
                saradominCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.BANDOS))) {
                bandosCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.ZAMORAK))) {
                zamorakCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.ARMADYL))) {
                armadylCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.ZAROS))) {
                zarosCost = Integer.parseInt(trimmed.substring(0, 1));
            }
            if (cost.contains(COST_TYPE_TO_STRING_MAP.get(COST_TYPE.SEREN))) {
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
            cardCost += cardCost + guthixCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.GUTHIX);
        }
        if (saradominCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + saradominCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.SARADOMIN);
        }
        if (bandosCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + bandosCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.BANDOS);
        }
        if (zamorakCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + zamorakCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.ZAMORAK);
        }
        if (armadylCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + armadylCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.ARMADYL);
        }
        if (zarosCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + zarosCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.ZAROS);
        }
        if (serenCost > 0) {
            if (!cardCost.isEmpty()) {
                cardCost += ", ";
            }
            cardCost = cardCost + serenCost + " " + COST_TYPE_TO_STRING_MAP.get(COST_TYPE.SEREN);
        }

        if (cardCost.isEmpty()) {
            return "0 Guthix";
        } else {
            return cardCost;
        }
    }
}
