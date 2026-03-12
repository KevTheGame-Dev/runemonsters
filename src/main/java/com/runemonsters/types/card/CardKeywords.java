package com.runemonsters.types.card;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardKeywords {
    public enum KEYWORD {
        NONE("None"),
        DEFENDER("Defender"),
        AGILE("Agile"),
        GUARDIAN("Guardian"),
        LEAP("Leap");

        private static final Map<String, KEYWORD> STRING_KEYWORD_MAP = Map.ofEntries(
                Map.entry("None", NONE),
                Map.entry("Defender", DEFENDER),
                Map.entry("Agile", AGILE),
                Map.entry("Guardian", GUARDIAN),
                Map.entry("Leap", LEAP)
        );

        public static KEYWORD get(String stringVal) {
            return STRING_KEYWORD_MAP.getOrDefault(stringVal, KEYWORD.NONE);
        }

        public static String[] toStringArr() {
            return Arrays.stream(KEYWORD.values()).map(KEYWORD::toString).toArray(String[]::new);
        }


        private final String stringVal;

        KEYWORD(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }

    public static CardKeywords fromString(String keywordsString) {
        String[] keywordsArr = StringUtils.split(keywordsString, ",");

        List<KEYWORD> keywords = new ArrayList<>();

        for(String keywordString : keywordsArr) {
            String trimmed = keywordString.trim();
            KEYWORD keyword = KEYWORD.get(trimmed);

            if (!keywords.contains(keyword)) {
               keywords.add(keyword);
            }
        }

        return new CardKeywords(keywords);
    }

    public List<KEYWORD> keywordList;

    public CardKeywords(List<KEYWORD> keywordList) {
        this.keywordList = keywordList;
    }

    public String toString() {
        if (keywordList.isEmpty()) {
            return "";
        }
        return keywordList.stream().map(KEYWORD::toString).collect(Collectors.joining(", "));
    }

    public List<String> getStringList() {
        return keywordList.stream().map(KEYWORD::toString).collect(Collectors.toList());
    }
}
