package cz.cuni.mff.java.klima.pacman.entities.characters;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.entities.Entity;
import cz.cuni.mff.java.klima.pacman.entities.characters.enemies.Enemy;
import cz.cuni.mff.java.klima.pacman.enums.Orientation;
import cz.cuni.mff.java.klima.pacman.graphics.Animation;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * PacMan class controls PacMan's movements and rendering.
 * It has specific movement behaviour, because its
 * dependent on user keyboard input.
 */
public class PacMan extends Moving {

    private static final int PACMAN_SIZE = 38;
    private static final int ANIMATION_SPEED = 50;

    private Animation aDown, aUp, aLeft, aRight;

    /**
     * Constructor of <code>PacMan</code> class. It takes
     * the game handler, for having access to the game map
     * and its elements, an x-coordinate and a y-coordinate,
     * which are both absolute in pixels, rather than in map tiles.
     * <p>
     * It also sets local variables, such as orientation,
     * the bounding rectangle and different types of
     * animations.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     */
    public PacMan(Handler handler, float x, float y) {
        super(handler, x, y);
        width = PACMAN_SIZE;
        height = PACMAN_SIZE;
        orientation = Orientation.RIGHT;

        bounds.x = 1;
        bounds.y = 1;
        bounds.width = 38;
        bounds.height = 38;

        aDown = new Animation(ANIMATION_SPEED, Assets.pacManDown);
        aUp = new Animation(ANIMATION_SPEED, Assets.pacManUp);
        aLeft = new Animation(ANIMATION_SPEED, Assets.pacManLeft);
        aRight = new Animation(ANIMATION_SPEED, Assets.pacManRight);
    }

    /**
     * PacMan's implementation of general move method
     * which is abstract in <code>Moving</code> class.
     * Checks for collisions and handles their
     * consequences, if there are any.
     */
    @Override
    public void move() {
        if ((xMove > 0) || (xMove < 0)) {
            Entity e = checkCollisions(xMove, 0f);
            // not colliding with any entity
            if (e == null) {
                moveX();
                // colliding with food
            } else if (e.notCollidable()) {
                e.setActive(false);
                moveX();
                // colliding with an enemy
            } else if (e instanceof Enemy) {
                if (((Enemy) e).notAlive()) {
                    moveX();
                } else {
                    handleCollision((Enemy) e);
                }
            }
        } else if ((yMove > 0) || (yMove < 0)) {
            Entity e = checkCollisions(0f, yMove);
            // not colliding with any entity
            if (e == null)
                moveY();
                // colliding with food
            else if (e.notCollidable()) {
                e.setActive(false);
                moveY();
                // colliding with an enemy
            } else if (e instanceof Enemy) {
                if (((Enemy) e).notAlive()) {
                    moveY();
                } else {
                    handleCollision((Enemy) e);
                }
            }
        }

    }

    /**
     * Handles PacMan's movement on the x axis.
     * It is dependent on PacMan's current orientation,
     * current alignment with the tile grid and user input.
     * It makes no changes, when <code>xMove</code>
     * is 0.
     */
    @Override
    public void moveX() {
        //moving right
        if (xMove > 0) {
            switch (orientation) {
                // if already moving in x-direction, change move immediately
                case RIGHT:
                case LEFT:
                    moveRight();
                    break;
                // if moving up, check if aligned with tile grid, then try to move right,
                // if not aligned with tile grid, continue moving up
                case UP:
                    if ((int) (y) % 40 == 0) {
                        int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.SIZE;
                        if (canGoRight(tx)) {
                            moveRight();
                        } else {
                            xMove = 0;
                            yMove -= speed;
                            moveUp();
                        }
                    } else {
                        xMove = 0;
                        yMove -= speed;
                        moveUp();
                    }
                    break;
                // if moving down, check if aligned with tile grid, then try to move right,
                // if not aligned with tile grid, continue moving down
                case DOWN:
                    if ((int) (y) % 40 == 0) {
                        int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.SIZE;
                        if (canGoRight(tx)) {
                            moveRight();
                        } else {
                            xMove = 0;
                            yMove = speed;
                            moveDown();
                        }
                    } else {
                        xMove = 0;
                        yMove = speed;
                        moveDown();
                    }
                    break;
                default:
                    break;
            }

            //moving left
        } else if (xMove < 0) {
            switch (orientation) {
                // if already moving in x-direction, change move immediately
                case LEFT:
                case RIGHT:
                    moveLeft();
                    break;
                // if moving up, check if aligned with tile grid, then try to move left,
                // if not aligned with tile grid, continue moving up
                case UP:
                    if ((int) (y) % 40 == 0) {
                        int tx = (int) (x + xMove + bounds.x) / Tile.SIZE;
                        if (canGoLeft(tx)) {
                            moveLeft();
                        } else {
                            xMove = 0;
                            yMove -= speed;
                            moveUp();
                        }
                    } else {
                        xMove = 0;
                        yMove -= speed;
                        moveUp();
                    }
                    break;
                // if moving down, check if aligned with tile grid, then try to move left,
                // if not aligned with tile grid, continue moving down
                case DOWN:
                    if ((int) (y) % 40 == 0) {
                        int tx = (int) (x + xMove + bounds.x) / Tile.SIZE;
                        if (canGoLeft(tx)) {
                            moveLeft();
                        } else {
                            xMove = 0;
                            yMove = speed;
                            moveDown();
                        }
                    } else {
                        xMove = 0;
                        yMove = speed;
                        moveDown();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles PacMan's movement on the y axis.
     * It is dependent on PacMan's current orientation,
     * current alignment with the tile grid and user input.
     * It makes no changes, when <code>yMove</code>
     * is 0.
     */
    @Override
    public void moveY() {
        //moving up
        if (yMove < 0) {
            switch (orientation) {
                // if already moving in y-direction, change move immediately
                case UP:
                case DOWN:
                    moveUp();
                    break;
                // if moving left, check if aligned with tile grid, then try to move up,
                // if not aligned with tile grid, continue moving left
                case LEFT:
                    if ((int) (x) % 40 == 0) {
                        int ty = (int) (y + yMove + bounds.y) / Tile.SIZE;
                        if (canGoUp(ty)) {
                            moveUp();
                        } else {
                            yMove = 0;
                            xMove -= speed;
                            moveLeft();
                        }
                    } else {
                        yMove = 0;
                        xMove -= speed;
                        moveLeft();
                    }
                    break;
                // if moving right, check if aligned with tile grid, then try to move up,
                // if not aligned with tile grid, continue moving right
                case RIGHT:
                    if ((int) (x) % 40 == 0) {
                        int ty = (int) (y + yMove + bounds.y) / Tile.SIZE;
                        if (canGoUp(ty)) {
                            moveUp();
                        } else {
                            yMove = 0;
                            xMove = speed;
                            moveRight();
                        }
                    } else {
                        yMove = 0;
                        xMove = speed;
                        moveRight();
                    }
                    break;
                default:
                    break;
            }
            //moving down
        } else if (yMove > 0) {
            switch (orientation) {
                // if already moving in y-direction, change move immediately
                case DOWN:
                case UP:
                    moveDown();
                    break;
                // if moving left, check if aligned with tile grid, then try to move down,
                // if not aligned with tile grid, continue moving left
                case LEFT:
                    if ((int) (x) % 40 == 0) {
                        int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.SIZE;
                        if (canGoDown(ty)) {
                            moveDown();
                        } else {
                            yMove = 0;
                            xMove -= speed;
                            moveLeft();
                        }
                    } else {
                        yMove = 0;
                        xMove -= speed;
                        moveLeft();
                    }
                    break;
                // if moving right, check if aligned with tile grid, then try to move down,
                // if not aligned with tile grid, continue moving right
                case RIGHT:
                    if ((int) (x) % 40 == 0) {
                        int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.SIZE;
                        if (canGoDown(ty)) {
                            moveDown();
                        } else {
                            yMove = 0;
                            xMove = speed;
                            moveRight();
                        }
                    } else {
                        yMove = 0;
                        xMove = speed;
                        moveRight();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles PacMan's collision with a ghost.
     * In accordance to current game situation, it sets
     * corresponding action.
     *
     * @param e enemy colliding with PacMan
     */
    private void handleCollision(Enemy e) {
        if (handler.getMap().isBlueGhosts()) {
            // PacMan can eat enemies and the colliding one is not immune to be eaten
            if (e.notImmuneToBlue()) {
                e.setAlive(false);
                moveX();
                // PacMan loses a life
            } else {
                handler.getGame().setLives(handler.getGame().getLives() - 1);
                handler.getMap().setEatingPacMan(true, e);
            }
            // PacMan loses a life
        } else {
            handler.getGame().setLives(handler.getGame().getLives() - 1);
            handler.getMap().setEatingPacMan(true, e);
        }
    }

    /**
     * Calculates next animation image and next move of PacMan.
     * Next move is only calculated, if the game is running
     * and PacMan is not in process of losing a life.
     */
    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;
        aDown.tick();
        aUp.tick();
        aLeft.tick();
        aRight.tick();

        if (handler.getMap().notLevelBegin() && !handler.getMap().isEatingPacMan()) {
            getInput();
            move();
        }
    }

    /**
     * According to user input, it sets the
     * <code>xMove</code> and <code>yMove</code>
     * variables to desired values.
     */
    private void getInput() {
        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
        }
    }

    /**
     * Draws current animation of PacMan.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationType(),
                (int) x + bounds.x, (int) y + bounds.y, width, height, null);
    }

    /**
     * Gets current animation type for PacMan rendering.
     *
     * @return current animation to render
     */
    private BufferedImage getCurrentAnimationType() {
        if (orientation == Orientation.LEFT) {
            return aLeft.getCurrentType();
        } else if (orientation == Orientation.UP) {
            return aUp.getCurrentType();
        } else if (orientation == Orientation.DOWN) {
            return aDown.getCurrentType();
        } else {
            return aRight.getCurrentType();
        }
    }
}
