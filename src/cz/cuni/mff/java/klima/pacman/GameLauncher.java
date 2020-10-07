package cz.cuni.mff.java.klima.pacman;

/**
 * The <code>GameLauncher</code> class contains
 * the <code>main</code> method of the Hungry PacMan
 * game.
 */
public class GameLauncher {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    /**
     * Main method of Hungry PacMan program, creates Game class
     * with game itself and starts the game.
     *
     * @param args command line arguments
     *             (not used, any command line arguments will be ignored)
     */
    public static void main(String[] args) {
        Game game = new Game("Hungry PacMan", WIDTH, HEIGHT);
        game.start();
    }
}
