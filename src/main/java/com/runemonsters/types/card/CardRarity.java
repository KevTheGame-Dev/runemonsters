package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;

import java.util.Arrays;
import java.util.Map;

public class CardRarity {
    public enum RARITY {
        UNKNOWN("Unknown"),
        COMMON("Common"),
        UNCOMMON("Uncommon"),
        RARE("Rare"),
        MEGARARE("MegaRare");

        private static final Map<String, RARITY> STRING_RARITY_MAP = Map.ofEntries(
                Map.entry("Unknown", UNKNOWN),
                Map.entry("Common", COMMON),
                Map.entry("Uncommon", UNCOMMON),
                Map.entry("Rare", RARE),
                Map.entry("MegaRare", MEGARARE)
        );

        public static RARITY get(String stringVal) {
            return STRING_RARITY_MAP.getOrDefault(stringVal, UNKNOWN);
        }

        public static String[] toStringArr() {
            return Arrays.stream(RARITY.values()).map(RARITY::toString).toArray(String[]::new);
        }


        private final String stringVal;

        RARITY(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }
}
