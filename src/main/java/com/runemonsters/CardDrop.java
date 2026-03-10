package com.runemonsters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import com.runemonsters.types.Card;
import com.runemonsters.types.CardPack;
import com.runemonsters.types.card.CardId;
import com.runemonsters.types.card.CardRarity;
import com.runemonsters.types.card.CardSet;
import net.runelite.api.Client;
import net.runelite.api.Actor;
import net.runelite.api.Hitsplat;
import net.runelite.api.NPC;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.eventbus.Subscribe;

@Slf4j
public class CardDrop {
    private static final float CARD_CHANCE = 0.01F;
    private static final float FOIL_CARD_CHANCE = 0.004F;
    private static final float PACK_CHANCE = 0.01F;
    private static final Map<Integer, CardSet.SET> CARD_PACK_DROPPERS = Map.ofEntries(
            Map.entry(15626, CardSet.SET.LBDV),// Brutus
            Map.entry(15627, CardSet.SET.LBDV)// Brutus, probably hard mode
    );

    private final Client client;
    private final RuneMonstersPlugin plugin;

    private final Map<Integer, Boolean> hasHitNPC = new HashMap<>();

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

            if (hasHitNPC.get(npcIndex) == null || !hasHitNPC.get(npcIndex)) {
                log.debug("NPC was not killed by player. You can't steal other's drops!");
                return;
            }

            if (!CardUtilities.existsForNpcId(npcId)) {
                log.debug("NPC id not in supported list. It is likely not currently a card. Id: {}", npcId);
                return;
            }

            float roll = new Random().nextFloat();

            // this effectively creates a drop table where there is:
            // - 1/(1/CARD_CHANCE) chance of rolling the card. With current values, this is 1/100
            // - 1/(1/FOIL_CARD_CHANCE) chance of rolling the foil. With current values, this is 1/250
            // - 1/(1/PACK_CHANCE) chance of rolling the pack. With current values, this is 1/100
            // - 1/(1 - the above chances) of rolling nothing. With current values, this is 97.6/100
            if (roll < (CARD_CHANCE + FOIL_CARD_CHANCE + PACK_CHANCE) || true) {
                String cardId = CardUtilities.getCardIdByNpcId(npcId);
                if (cardId.equals(CardId.UNKNOWN_CARD_ID)) {
                    log.error("Card not found by NPC id. Cards likely didn't load properly on startup");
                    return;
                }

                Card card = CardUtilities.getCardById(cardId);
                if (card == null) {
                    log.error("Card not found by id. Cards likely didn't load properly on startup");
                    return;
                }

                if (roll < CARD_CHANCE) {
                    log.debug("Regular card dropped!");
                    this.dropCard(card);
                } else if (roll < (CARD_CHANCE + FOIL_CARD_CHANCE)) {
                    log.debug("RuneMonsters: Foil card dropped!");
                    this.dropFoilCard(card);
                } else if (
                        (roll < (CARD_CHANCE + FOIL_CARD_CHANCE + PACK_CHANCE) || true)
                                && CARD_PACK_DROPPERS.containsKey(npcId)
                ){
                    log.debug("RuneMonsters: Pack dropped! NPC {}", npcId);

                    CardPack pack = new CardPack(
                            CARD_PACK_DROPPERS.get(npcId),
                            CardPack.generateCardPackRarity(),
                            CardPack.generateCardPackFoil()
                    );

                    this.dropPack(pack);
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

    private void dropPack(CardPack pack) {
        String dropMessage = this.buildCardPackDropMessage(pack);
        plugin.sendCardMessage(dropMessage);

        log.info(CardUtilities.convertCardListToString(pack.ripPack()));
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

    private String buildCardPackDropMessage(CardPack pack) {
        final ChatMessageBuilder message = new ChatMessageBuilder()
                .append(ChatColorType.NORMAL)
                .append("You have received a ")
                .append(ChatColorType.HIGHLIGHT);

        if (pack.isFoil) {
            message.append("FOIL ");
        }

        message.append(CardRarity.convertToString(pack.rarity) + " ")
               .append(CardSet.convertToString(pack.set) + " ")
               .append("card pack")
               .append(ChatColorType.NORMAL)
               .append(" - Check the RuneMonster side panel for more details!");

        return message.build();
    }
}
