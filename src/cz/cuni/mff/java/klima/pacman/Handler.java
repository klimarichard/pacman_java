package cz.cuni.mff.java.klima.pacman;

import cz.cuni.mff.java.klima.pacman.map.Map;
import cz.cuni.mff.java.klima.pacman.userinput.KeyManager;
import cz.cuni.mff.java.klima.pacman.userinput.MouseManager;

/**
 * The <code>Handler</code> class enables different
 * game objects and entities to access each other
 * when necessary.
 */
public class Handler {

    private Game game;
    private Map map;

    /**
     * Constructor of the <code>Handler</code> class.
     * It takes the game itself as a parameter.
     *
     * @param game the game
     */
    public Handler(Game game) {
        this.game = game;
    }

    /**
     * Resets game to its initial state.
     */
    public void resetGame() {
        game.resetGame();
    }

    //region Getters & Setters

    /**
     * Gets the game pixel width.
     *
     * @return the game width in pixels
     */
    public int getWidth() {
        return game.getWidth();
    }

    /**
     * Gets the game pixel height.
     *
     * @return the game height in pixels
     */
    public int getHeight() {
        return game.getHeight();
    }

    /**
     * Gets the game's key manager.
     *
     * @return key manager of the game
     */
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    /**
     * Gets the game's mouse manager.
     *
     * @return mouse manager of the game
     */
    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    /**
     * Gets the game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets the game map.
     *
     * @return the game map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Sets the game map.
     *
     * @param map new game map
     */
    public void setMap(Map map) {
        this.map = map;
    }
    //endregion
}
