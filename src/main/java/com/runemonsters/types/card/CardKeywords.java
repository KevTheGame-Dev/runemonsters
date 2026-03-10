package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardKeywords {
    public enum KEYWORD {
        NONE,
        DEFENDER,
        AGILE,
        GUARDIAN,
        LEAP
    }

    private static final Map<KEYWORD, String> KEYWORD_TO_STRING_MAP = Map.ofEntries(
            Map.entry(KEYWORD.NONE, "None"),
            Map.entry(KEYWORD.DEFENDER, "Crush"),
            Map.entry(KEYWORD.AGILE, "Slash"),
            Map.entry(KEYWORD.GUARDIAN, "Stab"),
            Map.entry(KEYWORD.LEAP, "Air")
    );
    private static final Map<String, KEYWORD> STRING_TO_KEYWORD_MAP = MapUtils.invertMap(KEYWORD_TO_STRING_MAP);


    public static String convertToString(KEYWORD keyword) {
        return KEYWORD_TO_STRING_MAP.getOrDefault(keyword, "None");
    }
    public static KEYWORD convertFromString(String keywordString) {
        return STRING_TO_KEYWORD_MAP.getOrDefault(keywordString, KEYWORD.NONE);
    }

    public static CardKeywords fromString(String keywordsString) {
        String[] keywordsArr = StringUtils.split(keywordsString, ",");

        List<KEYWORD> keywords = new ArrayList<>();

        for(String keywordString : keywordsArr) {
            String trimmed = keywordString.trim();
            KEYWORD keyword = convertFromString(trimmed);

            if (!keywords.contains(keyword)) {
               keywords.add(keyword);
            }
        }

        return new CardKeywords(keywords);
    }

    public List<KEYWORD> keywords;

    public CardKeywords(List<KEYWORD> keywords) {
        this.keywords = keywords;
    }

    public String toString() {
        if (keywords.isEmpty()) {
            return "";
        }
        return keywords.stream().map(CardKeywords::convertToString).collect(Collectors.joining(", "));
    }
}
