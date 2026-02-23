package com.runemonsters.types;

import com.runemonsters.CardUtilities;
import com.runemonsters.types.card.CardRarity;
import com.runemonsters.types.card.CardSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardPack {
    public final String id;
    public final CardSet.SET set;
    public final CardRarity.RARITY rarity;
    public final Boolean isFoil;
    public final String displayName;
    // Rarity breakdown
    // Common   - 1 guaranteed Rare, 1 uncommon, 5 common, and 3 random cards from the set
    // Uncommon - 1 guaranteed Rare, 5 uncommon, 1 common, and 3 random cards from the set
    // Rare     - 5 guaranteed Rare, 1 uncommon, 1 common, and 3 random cards from the set
    // MegaRare - 5 guaranteed MegaRare, 1 uncommon, 1 common, and 3 random cards from the set

    public CardPack (CardSet.SET set, CardRarity.RARITY rarity, Boolean isFoil) {
        this.set = set;
        this.rarity = rarity;
        this.isFoil = isFoil;
        this.id = generateId();
        this.displayName = generateDisplayName();
    }

    public ArrayList<Card> ripPack () {
        Integer guaranteedMegaRares = 0;
        Integer guaranteedRares = 0;
        Integer guaranteedUncommons = 0;
        Integer guaranteedCommons = 0;
        Integer randomCards = 3;



        if (isFoil) {
            // TODO
        }

        if (rarity == CardRarity.RARITY.COMMON) {
            guaranteedMegaRares = 0;
            guaranteedRares = 1;
            guaranteedUncommons = 1;
            guaranteedCommons = 5;
        } else if (rarity == CardRarity.RARITY.UNCOMMON) {
            guaranteedMegaRares = 0;
            guaranteedRares = 1;
            guaranteedUncommons = 5;
            guaranteedCommons = 1;
        } else if (rarity == CardRarity.RARITY.RARE) {
            guaranteedMegaRares = 0;
            guaranteedRares = 5;
            guaranteedUncommons = 1;
            guaranteedCommons = 1;
        } else if (rarity == CardRarity.RARITY.MEGARARE) {
            guaranteedMegaRares = 5;
            guaranteedRares = 0;
            guaranteedUncommons = 1;
            guaranteedCommons = 1;
        } else {
            randomCards = 10;
        }

        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(CardUtilities.getNumberOfRandomCardOfRarity(CardRarity.RARITY.COMMON, guaranteedCommons));
        cards.addAll(CardUtilities.getNumberOfRandomCardOfRarity(CardRarity.RARITY.UNCOMMON, guaranteedUncommons));
        cards.addAll(CardUtilities.getNumberOfRandomCardOfRarity(CardRarity.RARITY.RARE, guaranteedRares));
        cards.addAll(CardUtilities.getNumberOfRandomCardOfRarity(CardRarity.RARITY.MEGARARE, guaranteedMegaRares));
        cards.addAll(CardUtilities.getNumberOfRandomCard(randomCards));

        return cards;
    }

    private String generateId() {
        if (isFoil) {
            return "Foil-" + CardSet.convertToString(set) + "-" + CardRarity.convertToString(rarity);
        } else {
            return "Norm-" + CardSet.convertToString(set) + "-" + CardRarity.convertToString(rarity);
        }
    }

    private String generateDisplayName() {
        String name = "";
        if (isFoil) {
            name += "Foil ";
        }

        name += CardRarity.convertToString(rarity) + " ";
        name += CardSet.convertToName(set) + " ";
        name += "Pack";

        return name;
    }
}
