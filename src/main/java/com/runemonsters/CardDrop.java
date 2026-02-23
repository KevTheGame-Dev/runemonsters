package com.runemonsters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;

import com.runemonsters.types.Card;
import com.runemonsters.types.card.CardId;
import net.runelite.api.Client;
import net.runelite.api.Actor;
import net.runelite.api.Hitsplat;
import net.runelite.api.NPC;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.eventbus.Subscribe;

public class CardDrop {
    private static final boolean DEBUG = true;

    private static final float CARD_CHANCE = 0.005F;
    private static final float FOIL_CARD_CHANCE = 0.001F;
    private static final float PACK_CHANCE = 0.01F;
    private static final Map<String, Integer> CARD_PACK_DROPPERS = Map.ofEntries(
            Map.entry("Kalphite Queen", 965)
    );

    private final Client client;
    private final RuneMonstersPlugin plugin;

    private final Map<Integer, Boolean> hasHitNPC = new HashMap<Integer, Boolean>();

    @Inject
    public CardDrop(Client client, RuneMonstersPlugin plugin) {
        this.client = client;
        this.plugin = plugin;
    }

    @Subscribe
    public void onHitsplatApplied(HitsplatApplied hitsplatAppliedEvent) {
        Actor actor = hitsplatAppliedEvent.getActor();
        Hitsplat hitsplat = hitsplatAppliedEvent.getHitsplat();

        if (actor instanceof NPC && hitsplat.isMine()) {
            NPC npc = (NPC) actor;
            int npcIndex = npc.getIndex();

            hasHitNPC.put(npcIndex, true);
        }
    }

    @Subscribe
    public void onActorDeath(ActorDeath actorDeathEvent) {
        Actor actor = actorDeathEvent.getActor();

        if (actor instanceof NPC) {
            NPC npc = (NPC) actor;
            int npcId = npc.getId();
            int npcIndex = npc.getIndex();

            if (!CardUtilities.existsForNpcId(npcId)) {
                return;
            }

            if (hasHitNPC.get(npcIndex) == null || !hasHitNPC.get(npcIndex)) {
                return;
            }

            float roll = new Random().nextFloat();

            // this effectively creates a drop table where there is:
            // - 1/(1/CARD_CHANCE) chance of rolling the card. With current values, this is 1/200
            // - 1/(1/FOIL_CARD_CHANCE) chance of rolling the foil. With current values, this is 1/1000
            // - 1/(1/PACK_CHANCE) chance of rolling the pack. With current values, this is 1/100
            // - 1/(1 - the above chances) of rolling nothing. With current values, this is 98.4/100
            if (roll < (CARD_CHANCE + FOIL_CARD_CHANCE + PACK_CHANCE) || DEBUG) {
                String cardId = CardUtilities.getCardIdByNpcId(npcId);
                if (cardId.equals(CardId.UNKNOWN_CARD_ID)) {
                    System.out.println("RuneMonsters: Card not found by NPC id. It is likely not currently a card.");
                    return;
                }

                Card card = CardUtilities.getCardById(cardId);
                if (card == null) {
                    System.out.println("RuneMonsters: Card not found by id. This is likely caused by cards not loading properly on startup.");
                    return;
                }

                if (roll < CARD_CHANCE) {
                    System.out.println("RuneMonsters: Regular card dropped!");
                    this.dropCard(card);
                } else if (roll < (CARD_CHANCE + FOIL_CARD_CHANCE) || DEBUG) {
                    System.out.println("RuneMonsters: Foil card dropped!");
                   this.dropFoilCard(card);
                } else if (CARD_PACK_DROPPERS.containsKey(npc.getName())){
                    System.out.println("RuneMonsters: Pack dropped!");
                    // Drop pack
                    return;
                }
            }
        }
    }

    private void dropCard(Card card) {
        String dropMessage = this.buildCardDropMessage(card.name);
        plugin.sendCardMessage(dropMessage);
        CardUtilities.gainCard(card.cardId.toCSVString());
    }

    private void dropFoilCard(Card card) {
        String dropMessage = this.buildFoilCardDropMessage(card.name);
        plugin.sendCardMessage(dropMessage);

        Card altCard = CardUtilities.getRandomAltCardById(card.cardId.toCSVString());

        CardUtilities.gainCard(altCard.cardId.toCSVString());
    }

    private String buildCardDropMessage(String cardId) {
        final ChatMessageBuilder message = new ChatMessageBuilder()
                .append(ChatColorType.NORMAL)
                .append("You have received a ")
                .append(ChatColorType.HIGHLIGHT)
                .append(cardId)
                .append(ChatColorType.NORMAL)
                .append(" - Check the RuneMonster side panel for more details!");

        return message.build();
    }

    private String buildFoilCardDropMessage(String cardId) {
        final ChatMessageBuilder message = new ChatMessageBuilder()
                .append(ChatColorType.NORMAL)
                .append("You have received a ")
                .append(ChatColorType.HIGHLIGHT)
                .append("FOIL ")
                .append(cardId)
                .append(ChatColorType.NORMAL)
                .append(" - Check the RuneMonster side panel for more details!");

        return message.build();
    }
}
