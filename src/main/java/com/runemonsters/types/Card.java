package com.runemonsters.types;

import com.runemonsters.types.card.AttackType;
import com.runemonsters.types.card.CardCost;
import com.runemonsters.types.card.CardId;
import com.runemonsters.types.card.CardKeywords;
import com.runemonsters.types.card.CardRarity;
import com.runemonsters.types.card.CardSet;
import com.runemonsters.types.card.CardSubTypes;
import com.runemonsters.types.card.CardType;

public class Card {
    public final CardId cardId;
    public final String name;
    public final CardSet.SET set;
    public final CardRarity.RARITY rarity;
    public final CardCost cost;
    public final CardType.TYPE type;
    public final CardSubTypes subTypes;
    public final CardKeywords keywords;
    public final String effect;
    public final AttackType.ATTACK_TYPE attackType;
    public final Integer attackValue;
    public final AttackType.ATTACK_TYPE defensiveWeakness;
    public final Integer defensiveValue;
    public final Integer health;
    public final String npcIds;
    public final String flavorText;

    public Card(
            CardId cardId,
            String name,
            CardSet.SET set,
            CardRarity.RARITY rarity,
            CardCost cost,
            CardType.TYPE type,
            CardSubTypes subTypes,
            CardKeywords keywords,
            String effect,
            AttackType.ATTACK_TYPE attackType,
            Integer attackValue,
            AttackType.ATTACK_TYPE defensiveWeakness,
            Integer defensiveValue,
            Integer health,
            String npcIds,
            String flavorText
    ) {
        this.cardId = cardId;
        this.name = name;
        this.set = set;
        this.rarity = rarity;
        this.cost = cost;
        this.type = type;
        this.subTypes = subTypes;
        this.keywords = keywords;
        this.effect = effect;
        this.attackType = attackType;
        this.attackValue = attackValue;
        this.defensiveWeakness = defensiveWeakness;
        this.defensiveValue = defensiveValue;
        this.health = health;
        this.npcIds = npcIds;
        this.flavorText = flavorText;
    }

    public String toCSVString() {
        String[] arr = new String[] {
                cardId.toCSVString(),
                name,
                npcIds,
                CardSet.convertToString(set),
                CardRarity.convertToString(rarity),
                cost.toString(),
                CardType.convertToString(type),
                subTypes.toString(),
                keywords.toString(),
                effect,
                AttackType.convertToString(attackType),
                defensiveWeakness.toString()
        };
        return String.join(",", arr);
    }
}
