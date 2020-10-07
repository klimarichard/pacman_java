package cz.cuni.mff.java.klima.pacman.entities;

import cz.cuni.mff.java.klima.pacman.Handler;

import java.awt.*;

/**
 * <code>Entity</code> is an abstract class for all different types
 * of entities in the game.
 */
public abstract class Entity {

    private static final int DEFAULT_SIZE = 40;
    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected boolean isCollidable;
    protected Rectangle bounds;
    private boolean active = true;

    /**
     * Constructor of abstract entity class always called
     * by all entities present in game. It takes the game handler
     * for having access to the game map and its elements,
     * an x-coordinate and a y-coordinate, which are both
     * absolute in pixels, rather than in map tiles.
     * <p>
     * It also sets local variables, x and y-coordinates in map
     * tiles and the bounding rectangle.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     */
    public Entity(Handler handler, float x, float y) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = DEFAULT_SIZE;
        this.height = DEFAULT_SIZE;

        bounds = new Rectangle(0, 0, width, height);
    }

    /**
     * Checks if this entity collides with any other entity.
     *
     * @param xOff x-coordinate to be checked
     * @param yOff y-coordinate to be checked
     * @return entity colliding with this entity, or <code>null</code>, if there aren't any collisions
     */
    protected Entity checkCollisions(float xOff, float yOff) {
        for (Entity e : handler.getMap().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getBounds(0f, 0f).intersects(getBounds(xOff, yOff)))
                return e;
        }

        return null;
    }

    /**
     * Calculates bounds of this entity with offsets in
     * both directions.
     *
     * @param xOff x axis offset
     * @param yOff y axis offset
     * @return bounding rectangle of this offset entity
     */
    private Rectangle getBounds(float xOff, float yOff) {
        return new Rectangle((int) (x + bounds.x + xOff), (int) (y + bounds.y + yOff), bounds.width, bounds.height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    //region Getters & Setters

    /**
     * Gets x-coordinate of this entity.
     *
     * @return x-coordinate of this entity
     */
    public float getX() {
        return x;
    }

    /**
     * Sets x-coordinate of this entity.
     *
     * @param x new x-coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets y-coordinate of this entity.
     *
     * @return y-coordinate of this entity
     */
    public float getY() {
        return y;
    }

    /**
     * Sets y-coordinate of this entity.
     *
     * @param y new y-coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets entity's active status.
     *
     * @return <code>true</code>, if entity is active, <code>false</code> otherwise
     */
    boolean isActive() {
        return active;
    }

    /**
     * Sets entity active status.
     *
     * @param active new active status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets entity's collidable status.
     *
     * @return <code>false</code>, if entity is collidable, <code>true</code> otherwise
     */
    public boolean notCollidable() {
        return !isCollidable;
    }
    //endregion
}
