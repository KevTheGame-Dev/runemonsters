package com.runemonsters.types.card;

import com.runemonsters.swing.options.CostFilter;
import org.apache.commons.collections4.MapUtils;

import java.util.Arrays;
import java.util.Map;

public class CardType {
    public enum TYPE {
        ANY("Any"),
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
                Map.entry("Any", ANY),
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
            return STRING_TYPE_MAP.get(stringVal);
        }

        public static String[] toStringArr() {
            return Arrays.stream(TYPE.values()).map(TYPE::toString).toArray(String[]::new);
        }
    }

    private static final Map<TYPE, String> TYPE_TO_STRING_MAP = Map.ofEntries(
            Map.entry(TYPE.UNKNOWN, "Unknown"),
            Map.entry(TYPE.MONSTER, "Monster"),
            Map.entry(TYPE.MONSTER_TOKEN, "Monster Token"),
            Map.entry(TYPE.LEGENDARY_MONSTER, "Legendary Monster"),
            Map.entry(TYPE.SPELL, "Spell"),
            Map.entry(TYPE.SPELL_TOKEN, "Spell Token"),
            Map.entry(TYPE.EQUIPMENT, "Equipment"),
            Map.entry(TYPE.EQUIPMENT_TOKEN, "Equipment Token"),
            Map.entry(TYPE.DEVOTION, "Devotion")
    );
    private static final Map<String, TYPE> STRING_TO_TYPE_MAP = MapUtils.invertMap(TYPE_TO_STRING_MAP);


    public static String convertToString(TYPE type) {
        return TYPE_TO_STRING_MAP.getOrDefault(type, "Unknown");
    }
    public static TYPE fromString(String typeString) {
        return STRING_TO_TYPE_MAP.getOrDefault(typeString, TYPE.UNKNOWN);
    }
}
