package Default;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import arkanoid.Game;

/**
 * The Main class serves as the entry point for the game.
 */
public class Ass5Game {
    /**
     * The main method creates a new arkanoid.Game object, initializes it, and starts the game loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}




