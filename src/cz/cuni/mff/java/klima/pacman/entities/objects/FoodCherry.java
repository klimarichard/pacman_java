package cz.cuni.mff.java.klima.pacman.entities.objects;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;

import java.awt.*;

/**
 * <code>FoodCherry</code> class controls rendering of the
 * cherry food.
 */
public class FoodCherry extends Food {

    /**
     * Constructor of <code>FoodCherry</code> class only calls
     * constructor of the abstract <code>Food</code> class with score
     * set to 100. It takes the game handler for having
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
    public FoodCherry(Handler handler, float x, float y) {
        super(handler, x, y, 100);
    }

    /**
     * Draws cherry food's image.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.foodCherry, (int) x, (int) y, width, height, null);
    }
}
