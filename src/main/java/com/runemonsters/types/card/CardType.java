package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;
import java.util.Map;

public class CardType {
    public enum TYPE {
        UNKNOWN,
        MONSTER,
        MONSTER_TOKEN,
        LEGENDARY_MONSTER,
        SPELL,
        SPELL_TOKEN,
        EQUIPMENT,
        EQUIPMENT_TOKEN,
        DEVOTION
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
