package cz.cuni.mff.java.klima.pacman.entities.characters;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.entities.Entity;
import cz.cuni.mff.java.klima.pacman.enums.Orientation;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;

/**
 * <code>Moving</code> is an abstract class for all different types
 * of moving entities in the game.
 */
public abstract class Moving extends Entity {

    private static final float DEFAULT_SPEED = 4.0f;

    protected float xSpawn, ySpawn;
    protected boolean alive;
    protected float speed;
    protected float xMove, yMove;
    protected Orientation orientation;

    /**
     * Constructor of abstract moving class always called
     * by all moving entities present in game. It takes the
     * game handler for having access to the game map and
     * its elements, an x-coordinate and a y-coordinate,
     * which are both absolute in pixels, rather than in map tiles.
     * <p>
     * It also sets local variables, such as alive status, speed,
     * x and y-coordinates in map tiles and the bounding rectangle.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     */
    public Moving(Handler handler, float x, float y) {
        super(handler, x, y);
        alive = true;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        xSpawn = x;
        ySpawn = y;
        isCollidable = true;

        bounds.x = 1;
        bounds.y = 1;
        bounds.width = 38;
        bounds.height = 38;
    }

    /**
     * General moving method for all moving entities
     * in the game. It has two different implementations,
     * the PacMan's and the enemies'.
     */
    public abstract void move();

    /**
     * Sets the x-coordinate for moved entity.
     * It makes no changes, when <code>xMove</code>
     * is 0.
     */
    public abstract void moveX();

    /**
     * Sets the y-coordinate for moved entity.
     * It makes no changes, when <code>yMove</code>
     * is 0.
     */
    public abstract void moveY();

    /**
     * Moves the entity to the right if it is possible
     * and sets orientation to <code>RIGHT</code>.
     * When it is not possible to move right, it makes
     * no changes.
     */
    protected void moveRight() {
        int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.SIZE;
        if (canGoRight(tx)) {
            x += xMove;
        } else {
            x = tx * Tile.SIZE - 2 * bounds.x - bounds.width;
        }
        orientation = Orientation.RIGHT;
    }

    /**
     * Moves the entity to the left if it is possible
     * and sets orientation to <code>LEFT</code>.
     * When it is not possible to move left, it makes
     * no changes.
     */
    protected void moveLeft() {
        int tx = (int) (x + xMove + bounds.x) / Tile.SIZE;
        if (canGoLeft(tx)) {
            x += xMove;
        } else {
            x = tx * Tile.SIZE + Tile.SIZE;
        }
        orientation = Orientation.LEFT;
    }

    /**
     * Moves the entity up if it is possible
     * and sets orientation to <code>UP</code>.
     * When it is not possible to move up, it makes
     * no changes.
     */
    protected void moveUp() {
        int ty = (int) (y + yMove + bounds.y) / Tile.SIZE;
        if (canGoUp(ty)) {
            y += yMove;
        } else {
            y = ty * Tile.SIZE + Tile.SIZE;
        }
        orientation = Orientation.UP;
    }

    /**
     * Moves the entity down if it is possible
     * and sets orientation to <code>DOWN</code>.
     * When it is not possible to move down, it makes
     * no changes.
     */
    protected void moveDown() {
        int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.SIZE;
        if (canGoDown(ty)) {
            y += yMove;
        } else {
            y = ty * Tile.SIZE - 2 * bounds.y - bounds.height;
        }
        orientation = Orientation.DOWN;
    }

    /**
     * Checks the upper and lower right corner of
     * the entity for collisions with walls.
     *
     * @param tx x-coordinate of target tile
     * @return <code>true</code>, if there is no collision, <code>false</code> otherwise
     */
    boolean canGoRight(int tx) {
        return noCollisionWithTile(tx, (int) (y + bounds.y) / Tile.SIZE) &&
                noCollisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.SIZE);
    }

    /**
     * Checks the upper and lower left corner of
     * the entity for collisions with walls.
     *
     * @param tx x-coordinate of target tile
     * @return <code>true</code>, if there is no collision, <code>false</code> otherwise
     */
    boolean canGoLeft(int tx) {
        return noCollisionWithTile(tx, (int) (y + bounds.y) / Tile.SIZE) &&
                noCollisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.SIZE);
    }

    /**
     * Checks the left and right upper corner of
     * the entity for collisions with walls.
     *
     * @param ty y-coordinate of target tile
     * @return <code>true</code>, if there is no collision, <code>false</code> otherwise
     */
    boolean canGoUp(int ty) {
        return noCollisionWithTile((int) (x + bounds.x) / Tile.SIZE, ty) &&
                noCollisionWithTile((int) (x + bounds.x + bounds.width) / Tile.SIZE, ty);
    }

    /**
     * Checks the left and right lower corner of
     * the entity for collisions with walls.
     *
     * @param ty y-coordinate of target tile
     * @return <code>true</code>, if there is no collision, <code>false</code> otherwise
     */
    boolean canGoDown(int ty) {
        return noCollisionWithTile((int) (x + bounds.x) / Tile.SIZE, ty) &&
                noCollisionWithTile((int) (x + bounds.x + bounds.width) / Tile.SIZE, ty);
    }

    /**
     * Checks if tile on position <code>(x, y)</code>
     * is free.
     *
     * @param x x-coordinate of examined tile
     * @param y y-coordinate of examined tile
     * @return <code>true</code>, if tile is not a wall, <code>false</code> otherwise
     */
    private boolean noCollisionWithTile(int x, int y) {
        return !handler.getMap().getTile(x, y).isObstacle();
    }


    //region Getters & Setters

    /**
     * Gets alive status of entity.
     *
     * @return <code>false</code>, if entity is alive, <code>true</code> otherwise
     */
    protected boolean notAlive() {
        return !alive;
    }

    /**
     * Sets alive status of entity.
     *
     * @param alive new alive status of entity
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Gets x-coordinate of entity's spawn point.
     *
     * @return x-coordinate of entity's spawn point
     */
    public float getxSpawn() {
        return xSpawn;
    }

    /**
     * Sets x-coordinate of entity's spawn point.
     *
     * @param xSpawn new x-coordinate of entity's spawn point
     */
    public void setxSpawn(float xSpawn) {
        this.xSpawn = xSpawn;
    }

    /**
     * Gets y-coordinate of entity's spawn point.
     *
     * @return y-coordinate of entity's spawn point
     */
    public float getySpawn() {
        return ySpawn;
    }

    /**
     * Sets y-coordinate of entity's spawn point.
     *
     * @param ySpawn new y-coordinate of entity's spawn point
     */
    public void setySpawn(float ySpawn) {
        this.ySpawn = ySpawn;
    }

    /**
     * Gets entity's orientation.
     *
     * @return entity's orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets entity orientation.
     *
     * @param orientation new entity orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
    //endregion
}
