package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

public class AttackType {
    public enum ATTACK_TYPE {
        CRUSH,
        SLASH,
        STAB,
        AIR,
        WATER,
        EARTH,
        FIRE,
        BOLT,
        ARROW,
        DART,
        NONE
    }
    private static final Map<ATTACK_TYPE, String> ATTACK_TYPE_TO_STRING_MAP = Map.ofEntries(
            Map.entry(ATTACK_TYPE.CRUSH, "Crush"),
            Map.entry(ATTACK_TYPE.SLASH, "Slash"),
            Map.entry(ATTACK_TYPE.STAB, "Stab"),
            Map.entry(ATTACK_TYPE.AIR, "Air"),
            Map.entry(ATTACK_TYPE.WATER, "Water"),
            Map.entry(ATTACK_TYPE.EARTH, "Earth"),
            Map.entry(ATTACK_TYPE.FIRE, "Fire"),
            Map.entry(ATTACK_TYPE.BOLT, "Bolt"),
            Map.entry(ATTACK_TYPE.ARROW, "Arrow"),
            Map.entry(ATTACK_TYPE.DART, "Dart"),
            Map.entry(ATTACK_TYPE.NONE, "None")
    );
    private static final Map<String, ATTACK_TYPE> STRING_TO_ATTACK_TYPE_MAP = MapUtils.invertMap(ATTACK_TYPE_TO_STRING_MAP);


    public static String convertToString(ATTACK_TYPE attackType) {
       return ATTACK_TYPE_TO_STRING_MAP.getOrDefault(attackType, "None");
    }
    public static ATTACK_TYPE fromString(String attackTypeString) {
        return STRING_TO_ATTACK_TYPE_MAP.getOrDefault(attackTypeString, ATTACK_TYPE.NONE);
    }
}
