package pk;

import java.util.Random;

public class Deck {
    /* Odds of getting a card
     * BATTLE_TWO   = 2/35
     * BATTLE_THREE = 2/35
     * BATTLE_FOUR  = 2/35
     * NOP          = 29/35
     */
    Cards[] deck;
    public Deck() {
        deck = new Cards[]{Cards.BATTLE_TWO, Cards.BATTLE_TWO,
                           Cards.BATTLE_THREE, Cards.BATTLE_THREE,
                           Cards.BATTLE_FOUR, Cards.BATTLE_FOUR,
                           Cards.MONKEY_BUSINESS, Cards.MONKEY_BUSINESS, Cards.MONKEY_BUSINESS, Cards.MONKEY_BUSINESS,
                           Cards.GOLD, Cards.GOLD, Cards.GOLD, Cards.GOLD,
                           Cards.DIAMOND, Cards.DIAMOND, Cards.DIAMOND, Cards.DIAMOND,
                           Cards.CAPTAIN, Cards.CAPTAIN, Cards.CAPTAIN, Cards.CAPTAIN,
                           Cards.SORCERESS, Cards.SORCERESS, Cards.SORCERESS, Cards.SORCERESS,
                           Cards.TREASURE_CHEST, Cards.TREASURE_CHEST, Cards.TREASURE_CHEST, Cards.TREASURE_CHEST,
                           Cards.SKULL_ONE, Cards.SKULL_ONE, Cards.SKULL_ONE,
                           Cards.SKULL_TWO, Cards.SKULL_TWO,
                           }; // 35 cards in total
    }
    public Cards getCard() { // Return a random card from the deck
        Random random = new Random();
        return deck[random.nextInt(35)];
    }
}
