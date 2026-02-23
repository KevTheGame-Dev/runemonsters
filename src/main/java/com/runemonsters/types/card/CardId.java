package com.runemonsters.types.card;

import org.apache.commons.lang3.StringUtils;

public class CardId {
    public static final String ENGLISH = "EN";

    public static final String UNKNOWN_CARD_ID = "UNKN-EN000000";
    public static final Integer BASE_CARD_ALT_ID = 0;

    private static final Integer SET_LENGTH = 4;
    private static final Integer I18N_LENGTH = 2;
    private static final Integer ID_LENGTH = 4;
    private static final Integer ALT_ID_LENGTH = 2;

    public final String set;
    public final String i18n;
    public final Integer id;
    public final Integer altId;

    public CardId (String set, String i18n, Integer id, Integer altId) {
        this.set = set;
        this.i18n = i18n;
        this.id = id;
        this.altId = altId;
    }

    public String toString() {
        return "{ " + set + ", " + i18n + ", " + id + ", " + altId + "}";
    }

    public String toCSVString() {
        return set +
                "-" +
                i18n +
                padTo(Integer.toString(id), ID_LENGTH) +
                padTo(Integer.toString(altId), ALT_ID_LENGTH);
    }

    public String getBaseCardCSVString() {
        return set +
                "-" +
                i18n +
                padTo(Integer.toString(id), ID_LENGTH) +
                padTo(Integer.toString(BASE_CARD_ALT_ID), ALT_ID_LENGTH);
    }

    public Boolean isFoilCard() {
        // ALT ID usage is odd numbers are original or alternate art, and the next increment is foil
        // So all foils are even
        return (altId % 2) == 0;
    }

    public static CardId fromCSVString(String csvString) {
        String[] split = StringUtils.split(csvString, "-");
        // validate
        if (split.length != 2) {
            throw new IllegalArgumentException("Could not properly parse card id: " + csvString);
        }
        if (split[0].length() != SET_LENGTH) {
            throw new IllegalArgumentException("Card set is not required length: " + split[0]);
        }
        if (split[1].length() != (I18N_LENGTH + ID_LENGTH + ALT_ID_LENGTH)) {
            throw new IllegalArgumentException("Card set is not required length: " + split[1]);
        }

        String i18nString = StringUtils.substring(split[1], 0, I18N_LENGTH);
        String idString = StringUtils.substring(split[1], I18N_LENGTH, I18N_LENGTH + ID_LENGTH);
        String altIdString = StringUtils.substring(split[1], I18N_LENGTH + ID_LENGTH, I18N_LENGTH + ID_LENGTH + ALT_ID_LENGTH);

        return new CardId(split[0], i18nString, stripAndParseInt(idString), stripAndParseInt(altIdString));
    }

    private static String padTo (String str, Integer length) {
        if (str.length() > length) {
            return str;
        }
        return StringUtils.leftPad(str, length, "0");
    }

    private static Integer stripAndParseInt (String str) {
        String stripped = StringUtils.stripStart(str, "0");
        if (stripped.isEmpty()) {
            return BASE_CARD_ALT_ID;
        }
        return Integer.parseInt(stripped);
    }
}
