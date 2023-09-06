package pk;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {

    Logger logger = LogManager.getLogger();
    Level level = Level.OFF; // Control level of loggin in this file

    int score;
    int wins; // Totals players wins over all games run in the simulation
    String name;
    Strategy strategy;

    public int getWins() {
        logger.log(level, "getting number of wins...");
        return wins;
    }

    public void won() {
        logger.log(level, "increasing wins");
        wins++;
    }

    public String getName() {
        logger.log(level, "getting name...");
        return name;
    }

    public void addScore(int points, boolean has_capt_card ) {
        logger.log(level, "adding score...");
        if (has_capt_card) score += points*2; // double points added if the player has the captain card
        else score += points;
    }

    public void subScore(int points) {
        logger.log(level, "removing score...");
        score -= points;
    }

    public int getScore() {
        logger.log(level, "getting score...");
        return score;
    }

    public void setScore(int points) {
        logger.log(level, "setting score...");
        score = points;
    }

    public int takeTurn(Cards card) { // If on island of skulls it will return the number of skulls

        Random random = new Random();
        Dice myDice = new Dice();
        ArrayList<Faces> dice_keep = new ArrayList<>(); // For storing dice in hand
        ArrayList<Faces> dice_reroll = new ArrayList<>(); // For temp holding of dice we want to reroll unitl we know we have atleast 2 dice to reroll.


        int skulls;
        switch (card) { // If the card is a skulls card start off with a number of skulls
            case SKULL_ONE -> skulls = 1;
            case SKULL_TWO -> skulls = 2;
            default -> skulls = 0;
        }

        int number_of_free_dice = 8; // Tracks number of dice to be rolled again
        Faces myRoll;
        int number_of_rolls; // Same as number of free dice but will not be effected by for loop logic

        boolean sorceress_card_used = card != Cards.SORCERESS; // Tracks if the player has the sorceress card and if so if it has been used

        switch (strategy) { // players turn is based on their strategy
            case RANDOM -> {
                logger.log(level, "Rolling dice for random strategy");
                while (skulls < 3 && number_of_free_dice >= 2) { // While the player has not ended their turn
                    number_of_rolls = number_of_free_dice;
                    for (int i = 0; i < number_of_rolls; i++) { //Roll all free dice
                        myRoll = myDice.roll();
                        if (myRoll == Faces.SKULL) {
                            skulls++;
                            if (!sorceress_card_used) { // if we have it
                                skulls--;   // Remove skull
                                i--;        // Reset roll
                                sorceress_card_used = true; // Remove ability
                                continue;
                            }
                        } else {
                            dice_keep.add(myRoll);
                        }
                        number_of_free_dice--;
                    }
                    if (random.nextBoolean()) { //Random chance to roll all dice again or keep all
                        break;
                    } else { // All none skull dice are rolled again
                        number_of_free_dice = dice_keep.size();
                        dice_keep.clear();
                    }
                }
            }
            case COMBO -> { // Maximises the number of combos
                logger.log(level, "Rolling dice for combo strategy");
                while (skulls < 3 && number_of_free_dice >= 2) {
                    number_of_rolls = number_of_free_dice;
                    for (int i = 0; i < number_of_rolls; i++) {
                        myRoll = myDice.roll();
                        if (myRoll == Faces.SKULL) {
                            skulls++;
                        } else {
                            dice_keep.add(myRoll);
                        }
                        number_of_free_dice--;
                    }
                    for (int i = 0; i < dice_keep.size(); i++) {
                        // If the card is money business and the current dice is a monkey or parrot then count the number of them together.
                        if (card == Cards.MONKEY_BUSINESS && Collections.frequency(dice_keep, Faces.PARROT) + Collections.frequency(dice_keep, Faces.MONKEY) == 2) {
                            dice_keep.remove(i);
                            i--;
                            number_of_free_dice++;
                        }
                        else if (Collections.frequency(dice_keep, dice_keep.get(i)) == 1) { // If the player only has one of the dice type then they re-roll it
                            dice_reroll.add(dice_keep.get(i));
                            dice_keep.remove(i);
                            i--;
                            number_of_free_dice++;
                        }
                    }
                    if (number_of_free_dice < 2) { // force all dice to be kept if we do not meet the threshold
                        number_of_free_dice = 0;
                        dice_keep.addAll(dice_reroll);
                        dice_reroll.clear();
                        break;
                    }
                }
            }
            case BATTLE -> { // Leverages use of battle cards
                int sabers_needed;
                switch (card) { // If we have a bettle card we need a number of sabers
                    case BATTLE_TWO -> sabers_needed = 2;
                    case BATTLE_THREE -> sabers_needed = 3;
                    case BATTLE_FOUR -> sabers_needed = 4;
                    default -> sabers_needed = 0;
                }
                logger.log(level, "Rolling dice for battle strategy");
                // This behaves the same as the combo strategy if the player doesn't have a battle card.
                while (skulls < 3 && number_of_free_dice > 0) {
                    number_of_rolls = number_of_free_dice;
                    for (int i = 0; i < number_of_rolls; i++) {
                        myRoll = myDice.roll();
                        if (myRoll == Faces.SKULL) {
                            skulls++;
                        } else {
                            dice_keep.add(myRoll);
                        }
                        number_of_free_dice--;
                    }
                    if (Collections.frequency(dice_keep, Faces.SABER) < sabers_needed) { // If the player needs more sabers for the battle card then they re-roll everything that is not a saber.
                        for (int i = 0; i < dice_keep.size(); i++) {
                            if (dice_keep.get(i) != Faces.SABER) {
                                dice_keep.remove(i);
                                i--;
                                number_of_free_dice++;
                            }
                        }
                    } else { // If we have enough swords for battle do the combo strategy
                        for (int i = 0; i < dice_keep.size(); i++) {
                            if (Collections.frequency(dice_keep, dice_keep.get(i)) == 1 && dice_keep.get(i) != Faces.SABER) {
                                dice_reroll.add(dice_keep.get(i));
                                dice_keep.remove(i);
                                i--;
                                number_of_free_dice++;
                            }
                        }
                    }
                    if (number_of_free_dice < 2) { // force all dice to be kept if we do not meet the threshold
                        number_of_free_dice = 0;
                        dice_keep.addAll(dice_reroll);
                        dice_reroll.clear();
                        break;
                    }
                }
            }
        }

        boolean skip_scoring = false; // For skipping the rest of the scoring if the player loses the battle
        boolean has_capt_card = card == Cards.CAPTAIN;
        int dice_scored = 0; // For the full chest mechanic

        // Island of the skulls
        if (skulls > 3) {
            logger.log(level, "going to the island of skulls...");
            boolean keep_rolling = true;
            while (keep_rolling && number_of_free_dice > 0){ // Keep rolling if a skull was rolled
                keep_rolling = false;
                number_of_free_dice += dice_keep.size();
                dice_keep.clear();
                number_of_rolls = number_of_free_dice;
                for (int i = 0; i < number_of_rolls; i++) {
                    myRoll = myDice.roll();
                    if (myRoll == Faces.SKULL) {
                        skulls++;
                        number_of_free_dice--;
                        keep_rolling = true;
                    }
                }
            }
            logger.log(level, "exiting island of skulls...");
            if (has_capt_card) return skulls * 2;
            return skulls; // Returns the number of skulls to control how many points the other player loses.
        }
        logger.log(level, "evaluating card...");
        switch (card) { // Cards that effect scoring or cards in hand
            case BATTLE_TWO -> {
                if (Collections.frequency(dice_keep, Faces.SABER) >= 2) {
                    if (Collections.frequency(dice_keep, Faces.SABER) == 2) dice_scored += 2; // Since they wont count for 3 of a kind we count them as scoring here
                    addScore(300, has_capt_card);
                } else {
                    if (getScore() < 300) setScore(0);
                    else subScore(300);
                    skip_scoring = true;
                }
            }
            case BATTLE_THREE -> {
                if (Collections.frequency(dice_keep, Faces.SABER) >= 3) {
                    addScore(500, has_capt_card);
                } else {
                    if (getScore() < 500) setScore(0);
                    else subScore(500);
                    skip_scoring = true;
                }
            }
            case BATTLE_FOUR -> {
                if (Collections.frequency(dice_keep, Faces.SABER) >= 4) {
                    addScore(1000, has_capt_card);
                } else {
                    if (getScore() < 1000) setScore(0);
                    else subScore(1000);
                    skip_scoring = true;
                }
            }
            case GOLD -> dice_keep.add(Faces.GOLD); // Card is treated like die
            case DIAMOND -> dice_keep.add(Faces.DIAMOND); // ||
        }

        if (!skip_scoring && skulls < 3) { // Checks if scoring should be skipped from battle card or from skulls
            logger.log(level, "scoring...");
            logger.log(level, "scoring for gold and diamonds...");
            addScore((Collections.frequency(dice_keep, Faces.DIAMOND) + Collections.frequency(dice_keep, Faces.GOLD) * 100), card == Cards.CAPTAIN);
            dice_scored += Collections.frequency(dice_keep, Faces.DIAMOND) + Collections.frequency(dice_keep, Faces.GOLD);
            int[] faces_count;
            if (card == Cards.MONKEY_BUSINESS) {
                logger.log(level, "scoring for monkey card...");
                faces_count = new int[4]; //count number of each face
                for (Faces faces : dice_keep) {
                    switch (faces) {
                        case MONKEY, PARROT -> faces_count[0] += 1;
                        case GOLD -> faces_count[1] += 1;
                        case DIAMOND -> faces_count[2] += 1;
                        case SABER -> faces_count[3] += 1;
                    }
                }
            } else {
                logger.log(level, "scoring for combos...");
                faces_count = new int[5]; //count number of each type of roll for combo scoring
                for (Faces faces : dice_keep) {
                    switch (faces) {
                        case MONKEY -> faces_count[0] += 1;
                        case PARROT -> faces_count[1] += 1;
                        case GOLD -> faces_count[2] += 1;
                        case DIAMOND -> faces_count[3] += 1;
                        case SABER -> faces_count[4] += 1;
                    }
                }
            }
            int pos = 0; // Track which dice we are looking at
            for (int count : faces_count) { //score for multi-kinds
                switch (count) {
                    case 3 -> {
                        if (pos != 2 && pos != 3) dice_scored += 3; // Diamond and gold cards were already counted in this metric
                        addScore(100, has_capt_card);
                    }
                    case 4 -> {
                        if (pos != 2 && pos != 3) dice_scored += 4;
                        addScore(200, has_capt_card);
                    }
                    case 5 -> {
                        if (pos != 2 && pos != 3) dice_scored += 5;
                        addScore(500, has_capt_card);
                    }
                    case 6 -> {
                        if (pos != 2 && pos != 3) dice_scored += 6;
                        addScore(1000, has_capt_card);
                    }
                    case 7 -> {
                        if (pos != 2 && pos != 3) dice_scored += 7;
                        addScore(2000, has_capt_card);
                    }
                    case 8 -> {
                        if (pos != 2 && pos != 3) dice_scored += 8;
                        addScore(4000, has_capt_card);
                    }
                }
                pos++;
            }
        }
        if (dice_scored == 8) addScore(500, has_capt_card); // Full chest bonus
        return 0;
    }

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
        score = 0;
        wins = 0;
    }
}
