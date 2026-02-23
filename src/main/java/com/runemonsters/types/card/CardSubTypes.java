package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSubTypes {
    public enum MONSTER_SUBTYPE {
        NONE,
        HUMAN,
        WIZARD,
        CULINARY,
        ABERRATION,
        GOBLIN,
        FROG,
        TUTOR,
        KNIGHT,
        MARKSMEN,
        WARRIOR,
        WITCH,
        MONK,
        SLAYER,
        HAM,
        SKELETON,
        HELLHOUND,
        FARMER,
        ZOMBIE,
        FELINE,
        DEMON,
        SWINE,
        BIRD,
        TROLL,
        RAT,
        VAMPYRE,
        SERPENT,
        LIVESTOCK,
        GHOST,
        BRIGAND,
        UNICORN,
        DWARF,
        ARACHNID,
        PIRATE,
        PENGUIN,
        SLIME,
        GNOME
    }
    private static final Map<MONSTER_SUBTYPE, String> MONSTER_SUBTYPE_TO_STRING_MAP = Map.ofEntries(
            Map.entry(MONSTER_SUBTYPE.NONE, "None"),
            Map.entry(MONSTER_SUBTYPE.HUMAN, "Human"),
            Map.entry(MONSTER_SUBTYPE.WIZARD, "Wizard"),
            Map.entry(MONSTER_SUBTYPE.CULINARY, "Culinary"),
            Map.entry(MONSTER_SUBTYPE.ABERRATION, "Aberration"),
            Map.entry(MONSTER_SUBTYPE.GOBLIN, "Goblin"),
            Map.entry(MONSTER_SUBTYPE.FROG, "Frog"),
            Map.entry(MONSTER_SUBTYPE.TUTOR, "Tutor"),
            Map.entry(MONSTER_SUBTYPE.KNIGHT, "Knight"),
            Map.entry(MONSTER_SUBTYPE.MARKSMEN, "Marksmen"),
            Map.entry(MONSTER_SUBTYPE.WARRIOR, "Warrior"),
            Map.entry(MONSTER_SUBTYPE.WITCH, "Witch"),
            Map.entry(MONSTER_SUBTYPE.MONK, "Monk"),
            Map.entry(MONSTER_SUBTYPE.SLAYER, "Slayer"),
            Map.entry(MONSTER_SUBTYPE.HAM, "H.A.M."),
            Map.entry(MONSTER_SUBTYPE.SKELETON, "Skeleton"),
            Map.entry(MONSTER_SUBTYPE.HELLHOUND, "Hellhound"),
            Map.entry(MONSTER_SUBTYPE.FARMER, "Farmer"),
            Map.entry(MONSTER_SUBTYPE.ZOMBIE, "Zombie"),
            Map.entry(MONSTER_SUBTYPE.FELINE, "Feline"),
            Map.entry(MONSTER_SUBTYPE.DEMON, "Demon"),
            Map.entry(MONSTER_SUBTYPE.SWINE, "Swine"),
            Map.entry(MONSTER_SUBTYPE.BIRD, "Bird"),
            Map.entry(MONSTER_SUBTYPE.TROLL, "Troll"),
            Map.entry(MONSTER_SUBTYPE.RAT, "Rat"),
            Map.entry(MONSTER_SUBTYPE.VAMPYRE, "Vampyre"),
            Map.entry(MONSTER_SUBTYPE.SERPENT, "Serpent"),
            Map.entry(MONSTER_SUBTYPE.LIVESTOCK, "Livestock"),
            Map.entry(MONSTER_SUBTYPE.GHOST, "Ghost"),
            Map.entry(MONSTER_SUBTYPE.BRIGAND, "Brigand"),
            Map.entry(MONSTER_SUBTYPE.UNICORN, "Unicorn"),
            Map.entry(MONSTER_SUBTYPE.DWARF, "Dwarf"),
            Map.entry(MONSTER_SUBTYPE.ARACHNID, "Arachdid"),
            Map.entry(MONSTER_SUBTYPE.PIRATE, "Pirate"),
            Map.entry(MONSTER_SUBTYPE.PENGUIN, "Penguin"),
            Map.entry(MONSTER_SUBTYPE.SLIME, "Slime"),
            Map.entry(MONSTER_SUBTYPE.GNOME, "Gnome")
    );
    private final static Map<String, MONSTER_SUBTYPE> STRING_TO_MONSTER_SUBTYPE_MAP = MapUtils
            .invertMap(MONSTER_SUBTYPE_TO_STRING_MAP);

    public enum SPELL_SUBTYPE {
        NONE,
        FOOD,
        STANDARD,
        ANCIENT,
        LUNAR,
        ARCEUUS,
        POTION,
    }
    private static final Map<SPELL_SUBTYPE, String> SPELL_SUBTYPE_TO_STRING_MAP = Map.ofEntries(
            Map.entry(SPELL_SUBTYPE.NONE, "None"),
            Map.entry(SPELL_SUBTYPE.FOOD, "Food"),
            Map.entry(SPELL_SUBTYPE.STANDARD, "Standard"),
            Map.entry(SPELL_SUBTYPE.ANCIENT, "Ancient"),
            Map.entry(SPELL_SUBTYPE.LUNAR, "Lunar"),
            Map.entry(SPELL_SUBTYPE.ARCEUUS, "Arceuus"),
            Map.entry(SPELL_SUBTYPE.POTION, "Potion")
    );
    private static final Map<String, SPELL_SUBTYPE> STRING_TO_SPELL_SUBTYPE_MAP = MapUtils
            .invertMap(SPELL_SUBTYPE_TO_STRING_MAP);

    public enum EQUIPMENT_SUBTYPE {
        NONE,
        ARMOUR
    }
    private static final Map<EQUIPMENT_SUBTYPE, String> EQUIPMENT_SUBTYPE_TO_STRING_MAP = Map.ofEntries(
            Map.entry(EQUIPMENT_SUBTYPE.NONE, "None"),
            Map.entry(EQUIPMENT_SUBTYPE.ARMOUR, "Armour")
    );
    private static final Map<String, EQUIPMENT_SUBTYPE> STRING_TO_EQUIPMENT_SUBTYPE_MAP = MapUtils
            .invertMap(EQUIPMENT_SUBTYPE_TO_STRING_MAP);

    public static String convertToString(MONSTER_SUBTYPE subtype) {
        return MONSTER_SUBTYPE_TO_STRING_MAP.getOrDefault(subtype, "None");
    }
    public static String convertToString(SPELL_SUBTYPE subtype) {
        return SPELL_SUBTYPE_TO_STRING_MAP.getOrDefault(subtype, "None");
    }
    public static String convertToString(EQUIPMENT_SUBTYPE subtype) {
        return EQUIPMENT_SUBTYPE_TO_STRING_MAP.getOrDefault(subtype, "None");
    }

    public static MONSTER_SUBTYPE convertMonsterSubTypeFromString(String subtypeString) {
        return STRING_TO_MONSTER_SUBTYPE_MAP.getOrDefault(subtypeString, MONSTER_SUBTYPE.NONE);
    }
    public static SPELL_SUBTYPE convertSpellSubTypeFromString(String subtypeString) {
        return STRING_TO_SPELL_SUBTYPE_MAP.getOrDefault(subtypeString, SPELL_SUBTYPE.NONE);
    }
    public static EQUIPMENT_SUBTYPE convertEquipmentSubTypeFromString(String subtypeString) {
        return STRING_TO_EQUIPMENT_SUBTYPE_MAP.getOrDefault(subtypeString, EQUIPMENT_SUBTYPE.NONE);
    }


    public static CardSubTypes fromString(String subtypesString) {
        String[] subtypes = StringUtils.split(subtypesString, ",");

        List<MONSTER_SUBTYPE> monsterSubtypes = new ArrayList<>();
        List<SPELL_SUBTYPE> spellSubtypes = new ArrayList<>();
        List<EQUIPMENT_SUBTYPE> equipmentSubtypes = new ArrayList<>();

        for(String subtypeString : subtypes) {
            String trimmed = subtypeString.trim();
            if (STRING_TO_MONSTER_SUBTYPE_MAP.containsKey(trimmed)) {
                monsterSubtypes.add(STRING_TO_MONSTER_SUBTYPE_MAP.get(trimmed));
            }
            if (STRING_TO_SPELL_SUBTYPE_MAP.containsKey(trimmed)) {
                spellSubtypes.add(STRING_TO_SPELL_SUBTYPE_MAP.get(trimmed));
            }
            if (STRING_TO_EQUIPMENT_SUBTYPE_MAP.containsKey(trimmed)) {
                equipmentSubtypes.add(STRING_TO_EQUIPMENT_SUBTYPE_MAP.get(trimmed));
            }
        }

        return new CardSubTypes(monsterSubtypes, spellSubtypes, equipmentSubtypes);
    }


    public List<MONSTER_SUBTYPE> monsterSubtypes;
    public List<SPELL_SUBTYPE> spellSubtypes;
    public List<EQUIPMENT_SUBTYPE> equipmentSubtypes;

    public CardSubTypes(
            List<MONSTER_SUBTYPE> monsterSubtypes,
            List<SPELL_SUBTYPE> spellSubtypes,
            List<EQUIPMENT_SUBTYPE> equipmentSubtypes
    ) {
        this.monsterSubtypes = monsterSubtypes;
        this.spellSubtypes = spellSubtypes;
        this.equipmentSubtypes = equipmentSubtypes;
    }
}
