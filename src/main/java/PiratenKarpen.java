import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pk.*;


public class PiratenKarpen {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        Level level = Level.OFF;  // Set to desired logging level

        logger.log(level, "Starting program...");
        System.out.println("Welcome to Piraten Karpen Simulator!");

        logger.log(level, "creating deck...");
        Player p1 = null;
        Player p2 = null;
        Deck deck = new Deck(); // Initialize deck object
        logger.log(level, "deck created");


        logger.log(level, "creating players...");
        try { // Need to fix this
            if ("combo".equals(args[0])) p1 = new Player("p1", Strategy.COMBO);
            else if ("battle".equals(args[0])) p1 = new Player("p1", Strategy.BATTLE);
            if ("combo".equals(args[1])) p2 = new Player("p2", Strategy.COMBO);
            else if ("battle".equals(args[1])) p2 = new Player("p2", Strategy.BATTLE);
        } catch (ArrayIndexOutOfBoundsException e) {
            p1 = new Player("p1", Strategy.RANDOM);
            p2 = new Player("p2", Strategy.RANDOM);
        }
        logger.log(level, "players created");


        int draw_count = 0;
        int number_of_games = 42; // How many games are played per run of the sim

        logger.log(level, "starting sims...");
        System.out.println("Starting sims");
        for (int i = 0; i < number_of_games; i++) {
            logger.log(level, "setting scores...");
            p1.setScore(0);
            p2.setScore(0);
            logger.log(level, "scores set");

            logger.log(level, "taking turns...");
            while (p1.getScore() < 6000 && p2.getScore() < 6000) {
                p2.subScore(p1.takeTurn(deck.getCard()) * 100); // If player is on the island of skulls takeTurn will return the number of skulls
                p1.subScore(p2.takeTurn(deck.getCard()) * 100); // ||
            }
            if (p1.getScore() < 6000) p2.subScore(p1.takeTurn(deck.getCard()) * 100); // extra turn at end of the game
            else p1.subScore(p2.takeTurn(deck.getCard()) * 100);
            logger.log(level, "game over");

            logger.log(level, "determining winner...");
            if (p1.getScore() > p2.getScore()) {
                p1.won();
            }
            else if (p1.getScore() < p2.getScore()) {
                p2.won();
            }
            else { //If scores are the same count as a draw
                draw_count++;
            }
            logger.log(level, "wins recorded");
        }
        // calc win rates
        logger.log(level, "Calculating and displaying win rates...");
        System.out.printf("%s won %7.3f%% of the time\n", p1.getName(), ((float) p1.getWins() / (float) number_of_games) * 100);
        System.out.printf("%s won %7.3f%% of the time\n", p2.getName(), ((float) p2.getWins() / (float) number_of_games) * 100);
        System.out.printf("There were draws %.3f%% of the time\n", ((float) draw_count / (float) number_of_games) * 100);
        System.out.println("That's all folks!");
        logger.log(level, "program end");
    }
}
