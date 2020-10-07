package cz.cuni.mff.java.klima.pacman.entities.characters.enemies;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;

import java.awt.*;

/**
 * <code>EnemyRed</code> class controls movements and rendering
 * of the red enemy.
 */
public class EnemyRed extends Enemy {

    /**
     * Constructor of <code>EnemyRed</code> class only calls constructor
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
    public EnemyRed(Handler handler, float x, float y) {
        super(handler, x, y);
    }

    /**
     * Red enemy's implementation of chase mode movement.
     * <p>
     * In the chase mode, the red enemy's target tile is
     * calculated from positions of PacMan and the pink enemy.
     * It computes a vector from PacMan to the pink enemy,
     * subtracts it from PacMan's position and that is its
     * target tile.
     * <p>
     * <b>Example:</b>
     * PacMan's position is (3, 3), the pink enemy's position
     * is (5, 7). The vector from PacMan to the pink enemy is
     * (2, 4), the target tile is (3, 3) - (2, 4) = (1, -1).
     */
    @Override
    void moveChase() {
        int pacManX = (int) handler.getMap().getEntityManager().getPacMan().getX() / Tile.SIZE;
        int pacManY = (int) handler.getMap().getEntityManager().getPacMan().getY() / Tile.SIZE;

        int pinkieX = (int) handler.getMap().getEntityManager().getPinkie().getX() / Tile.SIZE;
        int pinkieY = (int) handler.getMap().getEntityManager().getPinkie().getY() / Tile.SIZE;

        targetX = pacManX - (pinkieX - pacManX);
        targetY = pacManY - (pinkieY - pacManY);

        super.moveChase();
    }

    /**
     * Red enemy's implementation of scatter mode movement.
     * <p>
     * In the scatter mode, the red enemy's target tile is
     * located outside the maze in the upper right corner,
     * resulting in it circulating around obstacles as near
     * that corner as possible.
     */
    @Override
    void moveScatter() {
        targetX = handler.getMap().getWidth();
        targetY = -1;

        super.moveScatter();
    }

    /**
     * Draws red enemy's image if it wasn't rendered by
     * super method.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        if (!rendered) {
            g.drawImage(Assets.enemyRed, (int) x, (int) y, width, height, null);
        }
    }
}
