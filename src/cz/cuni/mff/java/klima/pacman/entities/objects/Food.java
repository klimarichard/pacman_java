package cz.cuni.mff.java.klima.pacman.entities.objects;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.entities.Entity;

/**
 * <code>Food</code> is an abstract class for all different types
 * of food in the game.
 */
public abstract class Food extends Entity {

    private final int SCORE;

    /**
     * Constructor of abstract food class always called
     * by all food present in game. It takes the game handler
     * for having access to the game map and its elements,
     * an x-coordinate, a y-coordinate, which are both
     * absolute in pixels, rather than in map tiles, and
     * a score value of the food.
     * <p>
     * It also sets local variables, and the bounding
     * rectangle.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     * @param score   score value of the food
     */
    Food(Handler handler, float x, float y, int score) {
        super(handler, x, y);
        SCORE = score;
        isCollidable = false;
        bounds.x = 19;
        bounds.y = 19;
        bounds.width = 2;
        bounds.height = 2;
    }

    @Override
    public void tick() {

    }

    /**
     * Sets active status of the food.
     * If the food becomes inactive (it is
     * eaten by PacMan), its score is
     * added to player's score.
     *
     * @param active new active status
     */
    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            handler.getGame().setScore(handler.getGame().getScore() + SCORE);
        }
    }
}
