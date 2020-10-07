package cz.cuni.mff.java.klima.pacman.entities.characters.enemies;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;

import java.awt.*;

/**
 * <code>EnemyYellow</code> class controls movements and rendering
 * of the yellow enemy.
 */
public class EnemyYellow extends Enemy {

    /**
     * Constructor of <code>EnemyYellow</code> class only calls constructor
     * of the abstract <code>Enemy</code> class. It takes the game handler
     * for having access to the game map and its elements,
     * an x-coordinate and a y-coordinate, which are both
     * absolute in pixels, rather than in map tiles.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     */
    public EnemyYellow(Handler handler, float x, float y) {
        super(handler, x, y);
    }

    /**
     * Yellow enemy's implementation of chase mode movement.
     * <p>
     * In the chase mode, the yellow enemy's target tile is
     * completely random on every crossroads, resulting in
     * its chaotic movement in the maze.
     */
    @Override
    void moveChase() {
        targetX = random.nextInt(20);
        targetY = random.nextInt(20);

        super.moveChase();
    }

    /**
     * Yellow enemy's implementation of scatter mode movement.
     * <p>
     * In the scatter mode, the yellow enemy's target tile is
     * located outside the maze in the lower left corner,
     * resulting in it circulating around obstacles as near
     * that corner as possible.
     */
    @Override
    void moveScatter() {
        targetX = -1;
        targetY = handler.getMap().getHeight();

        super.moveScatter();
    }

    /**
     * Draws yellow enemy's image if it wasn't rendered by
     * super method.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        if (!rendered) {
            g.drawImage(Assets.enemyYellow, (int) x, (int) y, width, height, null);
        }
    }
}
