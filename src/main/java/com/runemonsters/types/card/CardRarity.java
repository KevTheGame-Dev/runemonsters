package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

public class CardRarity {
    public enum RARITY {
        UNKNOWN,
        COMMON,
        UNCOMMON,
        RARE,
        MEGARARE
    }

    private static final Map<RARITY, String> RARITY_TO_STRING_MAP = Map.ofEntries(
            Map.entry(RARITY.UNKNOWN, "Unknown"),
            Map.entry(RARITY.COMMON, "Common"),
            Map.entry(RARITY.UNCOMMON, "Uncommon"),
            Map.entry(RARITY.RARE, "Rare"),
            Map.entry(RARITY.MEGARARE, "MegaRare")
    );
    private static final Map<String, RARITY> STRING_TO_RARITY_MAP = MapUtils.invertMap(RARITY_TO_STRING_MAP);


    public static String convertToString(RARITY rarity) {
        return RARITY_TO_STRING_MAP.getOrDefault(rarity, "Unknown");
    }
    public static RARITY fromString(String rarityString) {
        return STRING_TO_RARITY_MAP.getOrDefault(rarityString, RARITY.UNKNOWN);
    }
}
