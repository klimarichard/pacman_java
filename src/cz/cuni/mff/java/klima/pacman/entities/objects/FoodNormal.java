package cz.cuni.mff.java.klima.pacman.entities.objects;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;

import java.awt.*;

/**
 * <code>FoodNormal</code> class controls rendering of the
 * normal food.
 */
public class FoodNormal extends Food {

    /**
     * Constructor of <code>FoodNormal</code> class only calls
     * constructor of the abstract <code>Food</code> class with score
     * set to 1. It takes the game handler for having
     * access to the game map and its elements,
     * an x-coordinate and a y-coordinate, which are both
     * absolute in pixels, rather than in map tiles.
     * <p>
     * It also sets local variables, and the bounding
     * rectangle.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     */
    public FoodNormal(Handler handler, float x, float y) {
        super(handler, x, y, 1);
    }

    /**
     * Sets active status of the food.
     * If the food becomes inactive (it is
     * eaten by PacMan), its score is
     * added to player's score. If this was
     * the last remaining normal food in
     * the maze, level up.
     *
     * @param active new active status
     */
    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        handler.getMap().setFoodCount(handler.getMap().getFoodCount() - 1);
        if (handler.getMap().getFoodCount() == 0) {
            handler.getGame().getGameState().levelUp();
        }
    }

    /**
     * Draws normal food's image.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.foodNormal, (int) x, (int) y, width, height, null);
    }
}
