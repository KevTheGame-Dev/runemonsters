package com.runemonsters;

import com.runemonsters.types.card.AttackType;
import com.runemonsters.types.Card;
import com.runemonsters.types.card.CardCost;
import com.runemonsters.types.card.CardId;
import com.runemonsters.types.card.CardKeywords;
import com.runemonsters.types.card.CardRarity;
import com.runemonsters.types.card.CardSet;
import com.runemonsters.types.card.CardSubTypes;
import com.runemonsters.types.card.CardType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CardUtilities {
    private static final Random RANDOM = new Random();
    private static final String CARDS_FILE_PATH = "src/main/resources/cards/cards.csv";
    public static final String CARD_IMAGES_PATH = "src/main/resources/cards/images/";
    public static final ArrayList<Card> ALL_CARDS = new ArrayList<>();
    public static final Map<String, Card> CARDS_BY_ID = new HashMap<String, Card>();
    private static final Map<String, List<String>> CARD_IDS_TO_CARD_ALTERNATES = new HashMap<String, List<String>>();
    private static final Map<Integer, String> SUPPORTED_NPC_IDS_TO_CARD_ID = new HashMap<Integer, String>();
    private static final ArrayList<Card> COMMON_RARITY_CARDS = new ArrayList<>();
    private static final ArrayList<Card> UNCOMMON_RARITY_CARDS = new ArrayList<>();
    private static final ArrayList<Card> RARE_RARITY_CARDS = new ArrayList<>();
    private static final ArrayList<Card> MEGARARE_RARITY_CARDS = new ArrayList<>();

    private static final String UNLOCKED_CARDS_PATH = RuneMonstersPlugin.DATA_DIRECTORY + "unlocked_cards.csv";
    public static final Map<String, Integer> UNLOCKED_CARDS = new HashMap<>();

    private static final ExecutorService unlockedCardsWriteQueue = Executors.newSingleThreadExecutor();

    public static void loadCardsFromFile() {
        try {
            Reader fileReader = new FileReader(CARDS_FILE_PATH);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader(
                            "id",
                            "Name",
                            "Set",
                            "Rarity",
                            "Cost",
                            "Type",
                            "SubTypes",
                            "Keywords",
                            "Effect",
                            "AttackType",
                            "AttackValue",
                            "DefensiveWeakness",
                            "DefensiveValue",
                            "HP",
                            "NPCIds",
                            "FlavorText"
                    )
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(fileReader);
            for (CSVRecord record : records) {
                String cardIdString = record.get("id");
                CardId cardId = CardId.fromCSVString(cardIdString);

                // Populate a mapping of CardIds strings to Card objects
                Card card = new Card(
                        cardId,
                        record.get("Name"),
                        CardSet.fromString(record.get("Set")),
                        CardRarity.fromString(record.get("Rarity")),
                        CardCost.fromString(record.get("Cost")),
                        CardType.fromString(record.get("Type")),
                        CardSubTypes.fromString(record.get("SubTypes")),
                        CardKeywords.fromString(record.get("Keywords")),
                        record.get("Effect"),
                        AttackType.fromString(record.get("AttackType")),
                        safeParseInt(record.get("AttackValue")),
                        AttackType.fromString(record.get("DefensiveWeakness")),
                        safeParseInt(record.get("DefensiveValue")),
                        safeParseInt(record.get("HP")),
                        record.get("NPCIds"),
                        record.get("FlavorText")
                );
                ALL_CARDS.add(card);
                CARDS_BY_ID.put(cardIdString, card);

                // Populate a mapping of NPC ids to CardIds strings. Used to lookup what card to drop when
                // an NPC is killed and a drop is received.
                for (String npcStringId : record.get("NPCIds").split(",")) {
                    if (npcStringId.isEmpty()) {
                        continue;
                    }
                    Integer npcId = Integer.parseInt(npcStringId);
                    SUPPORTED_NPC_IDS_TO_CARD_ID.put(npcId, cardIdString);
                }

                // Populate a mapping of base CardId strings to alt CardId strings. Used for dropping foils, and maybe
                // alternate art someday too.
                if (Objects.equals(cardId.altId, CardId.BASE_CARD_ALT_ID)) {
                    CARD_IDS_TO_CARD_ALTERNATES.put(cardIdString, new ArrayList<>());
                } else {
                    String baseCardIdString = cardId.getBaseCardCSVString();
                    if (CARD_IDS_TO_CARD_ALTERNATES.containsKey(baseCardIdString)) {
                        CARD_IDS_TO_CARD_ALTERNATES.get(baseCardIdString).add(cardIdString);
                    } else {
                        CARD_IDS_TO_CARD_ALTERNATES.put(baseCardIdString, new ArrayList<>());
                        CARD_IDS_TO_CARD_ALTERNATES.get(baseCardIdString).add(cardIdString);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("RuneMonsters: Failed to load cards. Plugin will not work");
        }
    }

    public static void loadUnlockedCardsFromFile() {
        try {
            Reader fileReader = new FileReader(UNLOCKED_CARDS_PATH);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "count")
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(fileReader);
            for (CSVRecord record : records) {
                String cardIdString = record.get("id");
                System.out.println("RuneMonsters: " + record.get("count"));
                UNLOCKED_CARDS.put(
                        cardIdString,
                        Integer.parseInt(record.get("count"))
                );
            }
        } catch (IOException e) {
            System.out.println("RuneMonsters: Failed to load unlocked cards.");
        }
    }

    private static void writeUnlockedCardsToFile() {
        synchronized(UNLOCKED_CARDS) {
            File file = new File(UNLOCKED_CARDS_PATH);
            System.out.println("RuneMonsters: " + UNLOCKED_CARDS_PATH);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO
                    System.out.println("RuneMonsters: Could not create unlocked card file" + e);
                }
            }

            try {
                FileWriter fileWriter = new FileWriter(UNLOCKED_CARDS_PATH);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
                csvPrinter.printRecord("id","count"); // Headers
                for (String cardIdString : UNLOCKED_CARDS.keySet()) {
                    String[] arr = new String[]{
                            cardIdString,
                            String.valueOf(UNLOCKED_CARDS.get(cardIdString))
                    };
                    String unlockedCardsString = String.join(",", arr);
                    csvPrinter.printRecord(cardIdString, String.valueOf(UNLOCKED_CARDS.get(cardIdString)));
                }
                csvPrinter.close();
                fileWriter.close();
            } catch (IOException e) {
                // TODO
                System.out.println("RuneMonsters: Could not write to unlocked card file");
            }
        }
    }

    public static void asyncWriteUnlockedCardsToFile() {
        unlockedCardsWriteQueue.submit(CardUtilities::writeUnlockedCardsToFile);
    }

    public static boolean existsForNpcId(Integer npcId) {
        return SUPPORTED_NPC_IDS_TO_CARD_ID.containsKey(npcId);
    }

    public static String getCardIdByNpcId(Integer npcId) {
        return SUPPORTED_NPC_IDS_TO_CARD_ID.getOrDefault(npcId, CardId.UNKNOWN_CARD_ID);
    }

    public static Card getCardById(String cardId) {
        return CARDS_BY_ID.getOrDefault(cardId, null);
    }

    public static Card getRandomAltCardById(String cardId) {
        if (!CARD_IDS_TO_CARD_ALTERNATES.containsKey(cardId)) {
            return getCardById(cardId);
        } else {
            List<String> altCardIds = CARD_IDS_TO_CARD_ALTERNATES.get(cardId);
            int randomIndex = RandomUtils.nextInt(0, altCardIds.toArray().length);
            return getCardById(altCardIds.get(randomIndex));
        }
    }


    public static Card getRandomCard() {
        int index = RANDOM.nextInt(ALL_CARDS.size());
        Card card = ALL_CARDS.get(index);

        // TODO - handle random foil cards
        return card;
    }
    public static ArrayList<Card> getNumberOfRandomCard(Integer numberOfCards) {
        ArrayList<Card> randomCards = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) {
            Card card = getRandomCard();
            randomCards.add(card);
        }

        // TODO - handle random foil cards
        return randomCards;
    }
    public static Card getRandomCardOfRarity(CardRarity.RARITY rarity) {
        ArrayList<Card> validCards;
        switch (rarity) {
            case COMMON: validCards = COMMON_RARITY_CARDS; break;
            case UNCOMMON: validCards = UNCOMMON_RARITY_CARDS; break;
            case RARE: validCards = RARE_RARITY_CARDS; break;
            case MEGARARE: validCards = MEGARARE_RARITY_CARDS; break;
            default:
                System.out.println("Tried to get random card of an unknown rarity. Defaulted to Common");
                validCards = COMMON_RARITY_CARDS;
        }

        int index = RANDOM.nextInt(validCards.size());
        Card card = validCards.get(index);

        // TODO - handle random foil cards
        return card;
    }
    public static ArrayList<Card> getNumberOfRandomCardOfRarity(CardRarity.RARITY rarity, Integer numberOfCards) {
        ArrayList<Card> randomCards = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) {
            Card card = getRandomCardOfRarity(rarity);
            randomCards.add(card);
        }

        // TODO - handle random foil cards
        return randomCards;
    }


    public static Integer getNumberOfUnlockedCards() {
        return UNLOCKED_CARDS.size();
    }

    public static Integer getNumberOfUnlockedBaseCards() {
        return UNLOCKED_CARDS.size(); // TODO
    }

    public static void gainCard(String cardId) {
        Integer count = UNLOCKED_CARDS.getOrDefault(cardId, 0);
        count += 1;
        UNLOCKED_CARDS.put(cardId, count);
        System.out.println("RuneMonsters: in gainCard " + UNLOCKED_CARDS.toString());
        asyncWriteUnlockedCardsToFile();
    }



    // HELPERS
    private static Integer safeParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
