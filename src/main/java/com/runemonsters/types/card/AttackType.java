package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;

import java.util.Arrays;
import java.util.Map;

public class AttackType {
    public enum ATTACK_TYPE {
        CRUSH("Crush"),
        SLASH("Slash"),
        STAB("Stab"),
        AIR("Air"),
        WATER("Water"),
        EARTH("Earth"),
        FIRE("Fire"),
        BOLT("Bolt"),
        ARROW("Arrow"),
        DART("Dart"),
        NONE("None");

        private static final Map<String, ATTACK_TYPE> STRING_ATTACK_TYPE_MAP = Map.ofEntries(
                Map.entry("Crush", CRUSH),
                Map.entry("Slash", SLASH),
                Map.entry("Stab", STAB),
                Map.entry("Air", AIR),
                Map.entry("Water", WATER),
                Map.entry("Earth", EARTH),
                Map.entry("Fire", FIRE),
                Map.entry("Bolt", BOLT),
                Map.entry("Arrow", ARROW),
                Map.entry("Dart", DART),
                Map.entry("None", NONE)
        );

        public static ATTACK_TYPE get(String stringVal) {
            return STRING_ATTACK_TYPE_MAP.getOrDefault(stringVal, NONE);
        }

        public static String[] toStringArr() {
            return Arrays.stream(ATTACK_TYPE.values()).map(ATTACK_TYPE::toString).toArray(String[]::new);
        }


        private final String stringVal;

        ATTACK_TYPE(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }
}
