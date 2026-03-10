package com.runemonsters.util;

import com.runemonsters.types.card.CardRarity;

import java.util.Map;
import java.util.Random;

public class Util {


    public static CardRarity.RARITY randomRarityByWeights (
            Integer commonWeight,
            Integer uncommonWeight,
            Integer rareWeight,
            Integer megaRareWeight
    ) {
        // Weights are treated as percentages. 50, 30, 15, 5
        float roll = new Random().nextFloat() * 100;
        if (roll < commonWeight) {
            return CardRarity.RARITY.COMMON;
        } else if (roll < commonWeight + uncommonWeight) {
            return CardRarity.RARITY.UNCOMMON;
        } else if (roll < commonWeight + uncommonWeight + rareWeight) {
            return CardRarity.RARITY.RARE;
        } else if (roll < commonWeight + uncommonWeight + rareWeight + megaRareWeight) {
            return CardRarity.RARITY.MEGARARE;
        }
        // Default to common if our weights don't add to 100, and we don't meet other conditions
        return CardRarity.RARITY.COMMON;
    }

    public static String convertIntStringMapToString(Map<Integer, String> map) {
        StringBuilder message = new StringBuilder("{ ");
        for (Integer intKey : map.keySet()) {
            message.append(intKey).append(": ").append(map.get(intKey)).append(", ");
        }
        message.append("}");
        return message.toString();
    }
}
