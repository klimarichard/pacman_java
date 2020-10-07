package cz.cuni.mff.java.klima.pacman.entities.characters.enemies;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;

import java.awt.*;

/**
 * <code>EnemyPink</code> class controls movements and rendering
 * of the pink enemy.
 */
public class EnemyPink extends Enemy {

    /**
     * Constructor of <code>EnemyPink</code> class only calls constructor
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
    public EnemyPink(Handler handler, float x, float y) {
        super(handler, x, y);
    }

    /**
     * Pink enemy's implementation of chase mode movement.
     * <p>
     * In the chase mode, the pink enemy's target tile is
     * PacMan's current position.
     */
    @Override
    void moveChase() {
        targetX = (int) handler.getMap().getEntityManager().getPacMan().getX() / Tile.SIZE;
        targetY = (int) handler.getMap().getEntityManager().getPacMan().getY() / Tile.SIZE;

        super.moveChase();
    }

    /**
     * Pink enemy's implementation of scatter mode movement.
     * <p>
     * In the scatter mode, the pink enemy's target tile is
     * located outside the maze in the upper left corner,
     * resulting in it circulating around obstacles as near
     * that corner as possible.
     */
    @Override
    void moveScatter() {
        targetX = -1;
        targetY = -1;

        super.moveScatter();
    }

    /**
     * Draws pink enemy's image if it wasn't rendered by
     * super method.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        if (!rendered) {
            g.drawImage(Assets.enemyPink, (int) x, (int) y, width, height, null);
        }
    }
}
