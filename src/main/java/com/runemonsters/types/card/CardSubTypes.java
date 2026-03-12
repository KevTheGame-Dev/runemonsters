package com.runemonsters.types.card;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardSubTypes {
    public enum MONSTER_SUBTYPE {
        NONE("None"),
        HUMAN("Human"),
        WIZARD("Wizard"),
        CULINARY("Culinary"),
        ABERRATION("Aberration"),
        GOBLIN("Goblin"),
        FROG("Frog"),
        TUTOR("Tutor"),
        KNIGHT("Knight"),
        MARKSMEN("Marksmen"),
        WARRIOR("Warrior"),
        WITCH("Witch"),
        MONK("Monk"),
        SLAYER("Slayer"),
        HAM("H.A.M."),
        SKELETON("Skeleton"),
        HELLHOUND("Hellhound"),
        FARMER("Farmer"),
        ZOMBIE("Zombie"),
        FELINE("Feline"),
        DEMON("Demon"),
        SWINE("Swine"),
        BIRD("Bird"),
        TROLL("Troll"),
        RAT("Rat"),
        VAMPYRE("Vampyre"),
        SERPENT("Serpent"),
        LIVESTOCK("Livestock"),
        GHOST("Ghost"),
        BRIGAND("Brigand"),
        UNICORN("Unicorn"),
        DWARF("Dwarf"),
        ARACHNID("Arachdid"),
        PIRATE("Pirate"),
        PENGUIN("Penguin"),
        SLIME("Slime"),
        GNOME("Gnome");

        private static final Map<String, MONSTER_SUBTYPE> STRING_MONSTER_SUBTYPE_MAP = Map.ofEntries(
                Map.entry("None", NONE),
                Map.entry("Human", HUMAN),
                Map.entry("Wizard", WIZARD),
                Map.entry("Culinary", CULINARY),
                Map.entry("Aberration", ABERRATION),
                Map.entry("Goblin", GOBLIN),
                Map.entry("Frog", FROG),
                Map.entry("Tutor", TUTOR),
                Map.entry("Knight", KNIGHT),
                Map.entry("Marksmen", MARKSMEN),
                Map.entry("Warrior", WARRIOR),
                Map.entry("Witch", WITCH),
                Map.entry("Monk", MONK),
                Map.entry("Slayer", SLAYER),
                Map.entry("H.A.M.", HAM),
                Map.entry("Skeleton", SKELETON),
                Map.entry("Hellhound", HELLHOUND),
                Map.entry("Farmer", FARMER),
                Map.entry("Zombie", ZOMBIE),
                Map.entry("Feline", FELINE),
                Map.entry("Demon", DEMON),
                Map.entry("Swine", SWINE),
                Map.entry("Bird", BIRD),
                Map.entry("Troll", TROLL),
                Map.entry("Rat", RAT),
                Map.entry("Vampyre", VAMPYRE),
                Map.entry("Serpent", SERPENT),
                Map.entry("Livestock", LIVESTOCK),
                Map.entry("Ghost", GHOST),
                Map.entry("Brigand", BRIGAND),
                Map.entry("Unicorn", UNICORN),
                Map.entry("Dwarf", DWARF),
                Map.entry("Arachdid", ARACHNID),
                Map.entry("Pirate", PIRATE),
                Map.entry("Penguin", PENGUIN),
                Map.entry("Slime", SLIME),
                Map.entry("Gnome", GNOME)
        );

        public static MONSTER_SUBTYPE get(String stringVal) {
            return STRING_MONSTER_SUBTYPE_MAP.getOrDefault(stringVal, NONE);
        }

        public static String[] toStringArr() {
            return Arrays.stream(MONSTER_SUBTYPE.values()).map(MONSTER_SUBTYPE::toString).toArray(String[]::new);
        }

        public static Boolean hasStringVal(String stringVal) {
            return STRING_MONSTER_SUBTYPE_MAP.containsKey(stringVal);
        }

        private final String stringVal;

        MONSTER_SUBTYPE(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }

    public enum SPELL_SUBTYPE {
        NONE("None"),
        FOOD("Food"),
        STANDARD("Standard"),
        ANCIENT("Ancient"),
        LUNAR("Lunar"),
        ARCEUUS("Arceuus"),
        POTION("Potion");

        private static final Map<String, SPELL_SUBTYPE> STRING_SPELL_SUBTYPE_MAP = Map.ofEntries(
                Map.entry("None", NONE),
                Map.entry("Food", FOOD),
                Map.entry("Standard", STANDARD),
                Map.entry("Ancient", ANCIENT),
                Map.entry("Lunar", LUNAR),
                Map.entry("Arceuus", ARCEUUS),
                Map.entry("Potion", POTION)
        );

        public static SPELL_SUBTYPE get(String stringVal) {
            return STRING_SPELL_SUBTYPE_MAP.getOrDefault(stringVal, NONE);
        }

        public static String[] toStringArr() {
            return Arrays.stream(SPELL_SUBTYPE.values()).map(SPELL_SUBTYPE::toString).toArray(String[]::new);
        }

        public static Boolean hasStringVal(String stringVal) {
            return STRING_SPELL_SUBTYPE_MAP.containsKey(stringVal);
        }

        private final String stringVal;

        SPELL_SUBTYPE(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }

    public enum EQUIPMENT_SUBTYPE {
        NONE("None"),
        ARMOUR("Armour");

        private static final Map<String, EQUIPMENT_SUBTYPE> STRING_EQUIPMENT_SUBTYPE_MAP = Map.ofEntries(
                Map.entry("None", NONE),
                Map.entry("Armour", ARMOUR)
        );

        public static EQUIPMENT_SUBTYPE get(String stringVal) {
            return STRING_EQUIPMENT_SUBTYPE_MAP.getOrDefault(stringVal, NONE);
        }

        public static String[] toStringArr() {
            return Arrays.stream(EQUIPMENT_SUBTYPE.values()).map(EQUIPMENT_SUBTYPE::toString).toArray(String[]::new);
        }

        public static Boolean hasStringVal(String stringVal) {
            return STRING_EQUIPMENT_SUBTYPE_MAP.containsKey(stringVal);
        }

        private final String stringVal;

        EQUIPMENT_SUBTYPE(String stringVal) {
            this.stringVal = stringVal;
        }

        public String toString() {
            return stringVal;
        }
    }

    public static CardSubTypes fromString(String subtypesString) {
        String[] subtypes = StringUtils.split(subtypesString, ",");

        List<MONSTER_SUBTYPE> monsterSubtypes = new ArrayList<>();
        List<SPELL_SUBTYPE> spellSubtypes = new ArrayList<>();
        List<EQUIPMENT_SUBTYPE> equipmentSubtypes = new ArrayList<>();

        for(String subtypeString : subtypes) {
            String trimmed = subtypeString.trim();
            if (MONSTER_SUBTYPE.hasStringVal(trimmed)) {
                monsterSubtypes.add(MONSTER_SUBTYPE.get(trimmed));
            }
            if (SPELL_SUBTYPE.hasStringVal(trimmed)) {
                spellSubtypes.add(SPELL_SUBTYPE.get(trimmed));
            }
            if (EQUIPMENT_SUBTYPE.hasStringVal(trimmed)) {
                equipmentSubtypes.add(EQUIPMENT_SUBTYPE.get(trimmed));
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

    public String toString() {
        if (!monsterSubtypes.isEmpty()) {
            return monsterSubtypes.stream()
                    .map(MONSTER_SUBTYPE::toString)
                    .collect(Collectors.joining(", "));
        } else if (!spellSubtypes.isEmpty()) {
            return spellSubtypes.stream()
                    .map(SPELL_SUBTYPE::toString)
                    .collect(Collectors.joining(", "));
        } else if (!equipmentSubtypes.isEmpty()) {
            return equipmentSubtypes.stream()
                    .map(EQUIPMENT_SUBTYPE::toString)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    public List<String> getMonsterStringList() {
        return monsterSubtypes.stream().map(MONSTER_SUBTYPE::toString).collect(Collectors.toList());
    }

    public List<String> getSpellStringList() {
        return spellSubtypes.stream().map(SPELL_SUBTYPE::toString).collect(Collectors.toList());
    }

    public List<String> getEquipmentStringList() {
        return equipmentSubtypes.stream().map(EQUIPMENT_SUBTYPE::toString).collect(Collectors.toList());
    }
}
