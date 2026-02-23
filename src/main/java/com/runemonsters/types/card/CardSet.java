package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

public class CardSet {
    public enum SET {
        UNKNOWN,
        GIEL,
        LBDV
    }

    private static final Map<SET, String> SET_TO_STRING_MAP = Map.ofEntries(
            Map.entry(SET.UNKNOWN, "Unknown"),
            Map.entry(SET.GIEL, "GIEL"),
            Map.entry(SET.LBDV, "LBDV")
    );
    private static final Map<String, SET> STRING_TO_SET_MAP = MapUtils.invertMap(SET_TO_STRING_MAP);


    public static String convertToString(SET set) {
        return SET_TO_STRING_MAP.getOrDefault(set, "Unknown");
    }
    public static SET fromString(String setString) {
        return STRING_TO_SET_MAP.getOrDefault(setString, SET.UNKNOWN);
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
