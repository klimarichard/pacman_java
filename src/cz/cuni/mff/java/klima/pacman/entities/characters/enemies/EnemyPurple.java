package cz.cuni.mff.java.klima.pacman.entities.characters.enemies;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.enums.Orientation;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;

import java.awt.*;

/**
 * <code>EnemyPurple</code> class controls movements and rendering
 * of the purple enemy.
 */
public class EnemyPurple extends Enemy {

    /**
     * Constructor of <code>EnemyPurple</code> class only calls constructor
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
    public EnemyPurple(Handler handler, float x, float y) {
        super(handler, x, y);
    }

    /**
     * Purple enemy's implementation of chase mode movement.
     * <p>
     * In the chase mode, the purple enemy's target tile is
     * 4 tiles in front of PacMan, but if the purple enemy's
     * euclidean distance from PacMan is less than 6, its
     * target tile is located outside the maze in the lower
     * right corner, resulting in it circulating around
     * obstacles as near that corner as possible.
     */
    @Override
    void moveChase() {
        int pacManX = (int) handler.getMap().getEntityManager().getPacMan().getX() / Tile.SIZE;
        int pacManY = (int) handler.getMap().getEntityManager().getPacMan().getY() / Tile.SIZE;

        if (distance(xTile, yTile, pacManX, pacManY) > 6) {
            targetX = pacManX +
                    dX[Orientation.toInt(handler.getMap().getEntityManager().getPacMan().getOrientation())] * 4;
            targetY = pacManY +
                    dY[Orientation.toInt(handler.getMap().getEntityManager().getPacMan().getOrientation())] * 4;
        } else {
            targetX = handler.getMap().getWidth();
            targetY = handler.getMap().getHeight();
        }

        super.moveChase();
    }

    /**
     * Purple enemy's implementation of scatter mode movement.
     * <p>
     * In the scatter mode, the purple enemy's target tile is
     * located outside the maze in the lower right corner,
     * resulting in it circulating around obstacles as near
     * that corner as possible.
     */
    @Override
    void moveScatter() {
        targetX = 18;
        targetY = 20;

        super.moveScatter();
    }

    /**
     * Draw purple enemy's image if it wasn't rendered by
     * super method.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        if (!rendered) {
            g.drawImage(Assets.enemyPurple, (int) x, (int) y, width, height, null);
        }
    }
}
