package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;

import java.util.Arrays;
import java.util.Map;

public class CardType {
    public enum TYPE {
        UNKNOWN("Unknown"),
        MONSTER("Monster"),
        MONSTER_TOKEN("Monster Token"),
        LEGENDARY_MONSTER("Legendary Monster"),
        SPELL("Spell"),
        SPELL_TOKEN("Spell Token"),
        EQUIPMENT("Equipment"),
        EQUIPMENT_TOKEN("Equipment Token"),
        DEVOTION("Devotion");

        private final String stringVal;

        private static final Map<String, TYPE> STRING_TYPE_MAP = Map.ofEntries(
                Map.entry("Unknown", UNKNOWN),
                Map.entry("Monster", MONSTER),
                Map.entry("Monster Token", MONSTER_TOKEN),
                Map.entry("Legendary Monster", LEGENDARY_MONSTER),
                Map.entry("Spell", SPELL),
                Map.entry("Spell Token", SPELL_TOKEN),
                Map.entry("Equipment", EQUIPMENT),
                Map.entry("Equipment Token", EQUIPMENT_TOKEN),
                Map.entry("Devotion", DEVOTION)
        );

        TYPE(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }

        public static TYPE get(String stringVal) {
            return STRING_TYPE_MAP.getOrDefault(stringVal, UNKNOWN);
        }

        public static String[] toStringArr() {
            return Arrays.stream(TYPE.values()).map(TYPE::toString).toArray(String[]::new);
        }
    }
}
