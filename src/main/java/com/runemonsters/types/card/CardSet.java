package com.runemonsters.types.card;

import java.util.Arrays;
import java.util.Map;

public class CardSet {
    public enum SET {
        UNKNOWN("Unknown"),
        GIEL("GIEL"),
        LBDV("LBDV");

        private final String stringVal;

        private static final Map<String, SET> STRING_SET_MAP = Map.ofEntries(
                Map.entry("Unknown", UNKNOWN),
                Map.entry("GIEL", GIEL),
                Map.entry("LBDV", LBDV)
        );

        SET(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }

        public static SET get(String stringVal) {
            return STRING_SET_MAP.getOrDefault(stringVal, UNKNOWN);
        }

        public static String[] toStringArr() {
            return Arrays.stream(SET.values()).map(SET::toString).toArray(String[]::new);
        }
    }

    public static String convertToName(SET set) {
        switch (set) {
            case GIEL: return "Gielinor Set";
            case LBDV: return "Lumbridge & Draynor Set";
            default:
                return "Unknown set";
        }
    }
    public static String convertToLongDescription(SET set) {
        switch (set) {
            case GIEL: return "The Gielinor set. Contains monsters & NPCs that appear around Gielinor and aren't limited or associated with a specific region";
            case LBDV: return "The Lumbridge & Draynor Village set. Contains monsters & NPCs that are local to the region";
            default:
                return "Unknown set. If you are seeing this, an error has occurred.";
        }
    }
}
