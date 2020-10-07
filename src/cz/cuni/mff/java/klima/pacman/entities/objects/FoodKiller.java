package cz.cuni.mff.java.klima.pacman.entities.objects;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;

import java.awt.*;

/**
 * <code>FoodKiller</code> class controls rendering of the
 * killer food.
 */
public class FoodKiller extends Food {

    /**
     * Constructor of <code>FoodKiller</code> class only calls
     * constructor of the abstract <code>Food</code> class with score
     * set to 0. It takes the game handler for having
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
    public FoodKiller(Handler handler, float x, float y) {
        super(handler, x, y, 0);
    }

    /**
     * Sets active status of the food.
     * If the killer food becomes inactive
     * (it is eaten by PacMan), the enemies
     * turn blue and can be temporarily eaten
     * by PacMan.
     *
     * @param active new active status
     */
    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            handler.getMap().setBlueGhosts(true);
        }
    }

    /**
     * Draws killer food's image.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.foodKiller, (int) x, (int) y, width, height, null);
    }
}
