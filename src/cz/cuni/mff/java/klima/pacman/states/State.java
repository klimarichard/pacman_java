package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

import java.awt.*;

/**
 * <code>State</code> is an abstract class for all different types
 * of game states the game can be in.
 * <p>
 * Each type of state has different appearance as well as different
 * user interaction options. This class also contains the static
 * methods <code>getState</code> and <code>setState</code> which are
 * responsible for changing of the current state.
 */
public abstract class State {

    private static State currentState = null;

    final int BUTTON_WIDTH = 300;
    final int BUTTON_HEIGHT = 75;
    final int BUTTON_DIFFERENCE = 110;
    final int RIGHT_COLUMN_SIZE = 400;
    final int RIGHT_COLUMN_INDENT = 50;

    protected Handler handler;

    UserInterfaceManager manager;

    /**
     * Constructor of the abstract state class. It takes
     * the game handler, for having access to all elements
     * of the game as a parameter.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     */
    State(Handler handler) {
        this.handler = handler;
    }

    /**
     * Gets current state.
     *
     * @return current state
     */
    public static State getState() {
        return currentState;
    }

    /**
     * Sets current state.
     *
     * @param state new current state
     */
    public static void setState(State state) {
        // game should start exactly where we left off
        if (state instanceof GameState)
            ((GameState) state).getMap().setLastTime(System.currentTimeMillis());
        currentState = state;
    }

    //region Getters & Setters

    /**
     * Updates all user interface elements.
     */
    public void tick() {
        manager.tick();
    }

    /**
     * Draws all user interface elements.
     *
     * @param g Graphics instance on which the images are rendered
     */
    public void render(Graphics g) {
        manager.render(g);
    }

    /**
     * Gets user interface manager.
     *
     * @return user interface manager
     */
    public UserInterfaceManager getManager() {
        return manager;
    }
    //endregion
}
