package cz.cuni.mff.java.klima.pacman.entities.characters.enemies;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.entities.Entity;
import cz.cuni.mff.java.klima.pacman.entities.characters.Moving;
import cz.cuni.mff.java.klima.pacman.entities.characters.PacMan;
import cz.cuni.mff.java.klima.pacman.enums.Orientation;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;

import java.awt.*;
import java.util.Random;

/**
 * <code>Enemy</code> is an abstract class for all different types
 * of ghosts present in the game.
 * <p>
 * Each type of ghost has different behaviour regarding
 * his movements in chase and scatter modes. All share the
 * same behaviour, when in blue-ghosts mode or dead mode.
 */
public abstract class Enemy extends Moving {
    boolean rendered;
    int xTile, yTile;
    int targetX, targetY;
    int[] dX = {1, -1, 0, 0};
    int[] dY = {0, 0, -1, 1};
    Random random;
    private int resurrectionTimer, eatingPacManConstant = 0;
    private boolean immuneToBlue;
    private boolean[] free;
    private double currentMinimal;

    /**
     * Constructor of abstract enemy class always called
     * by all ghosts present in game. It takes the game handler
     * for having access to the game map and its elements,
     * an x-coordinate and a y-coordinate, which are both
     * absolute in pixels, rather than in map tiles.
     * <p>
     * It also sets local variables, such as random number
     * generator, x and y-coordinates in map tiles and
     * the bounding rectangle.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param x       absolute x-coordinate in pixels
     * @param y       absolute y-coordinate in pixels
     */
    Enemy(Handler handler, float x, float y) {
        super(handler, x, y);

        random = new Random();

        immuneToBlue = false;
        orientation = Orientation.fromInt(random.nextInt(3));

        targetX = (int) xSpawn / Tile.SIZE;
        targetY = (int) ySpawn / Tile.SIZE;

        bounds.x = 4;
        bounds.y = 2;
        bounds.width = 32;
        bounds.height = 36;
    }

    /**
     * Enemies' implementation of general move method
     * which is abstract in <code>Moving</code> class.
     * Checks for collision with PacMan and handles its
     * consequences, if there are any.
     */
    @Override
    public void move() {
        if ((xMove > 0) || (xMove < 0)) {
            Entity e = checkCollisions(xMove, 0f);
            // not colliding with any entity, or colliding with food or other enemy
            if ((e == null) || (e.notCollidable()) || (e instanceof Enemy)) {
                moveX();
                // colliding with PacMan
            } else if (e instanceof PacMan) {
                if (this.notAlive()) {
                    moveX();
                } else {
                    handleCollision();
                }
            }
        } else if ((yMove > 0) || (yMove < 0)) {
            Entity e = checkCollisions(0f, yMove);
            // not colliding with any entity, or colliding with food or other enemy
            if ((e == null) || (e.notCollidable()) || (e instanceof Enemy)) {
                moveY();
                // colliding with PacMan
            } else if (e instanceof PacMan) {
                if (this.notAlive()) {
                    moveY();
                } else {
                    handleCollision();
                }
            }
        }
    }

    @Override
    public void moveX() {
        if (xMove > 0) {
            moveRight();
        } else if (xMove < 0) {
            moveLeft();
        }
    }

    @Override
    public void moveY() {
        //moving up
        if (yMove < 0) {
            moveUp();
        } else if (yMove > 0) {
            moveDown();
        }
    }

    /**
     * Handles enemies' collision with PacMan.
     * In accordance to current game situation, it sets
     * corresponding action.
     */
    private void handleCollision() {
        if (handler.getMap().isBlueGhosts()) {
            // PacMan can eat enemies and the colliding one is not immune to be eaten
            if (this.notImmuneToBlue()) {
                this.setAlive(false);
                // PacMan loses a life
            } else {
                handler.getGame().setLives(handler.getGame().getLives() - 1);
                handler.getMap().setEatingPacMan(true, this);
            }
            // PacMan loses a life
        } else {
            handler.getGame().setLives(handler.getGame().getLives() - 1);
            handler.getMap().setEatingPacMan(true, this);
        }
    }

    /**
     * Basic action when moving in chase mode.
     * It sets the enemy's coordinates to new ones.
     */
    void moveChase() {
        setMovement(targetX, targetY);
    }

    /**
     * Basic action when moving in scatter mode.
     * It sets the enemy's coordinates to new ones.
     */
    void moveScatter() {
        setMovement(targetX, targetY);
    }

    /**
     * Handles movement of enemies, when they can be eaten
     * by PacMan.
     * <p>
     * When PacMan eats a killer food, the enemies turn blue
     * and can be temporarily eaten by PacMan. In this state,
     * they choose their destination tile randomly on every
     * crossroads.
     */
    private void moveBlue() {
        targetX = random.nextInt(20);
        targetY = random.nextInt(20);

        setMovement(targetX, targetY);
    }

    /**
     * Handles movement of enemies, when they are not alive.
     * <p>
     * When PacMan eats an enemy, the enemy has to return
     * to its spawn coordinates before it is resurrected.
     * If it comes to its spawn coordinates too quickly, it
     * has to wait some time (the overall time of being dead
     * cannot be less than 2 seconds).
     * <p>
     * If enemy is already present at its spawn coordinates,
     * it is resurrected, if the minimum dead time has
     * passed. Otherwise, the target tile is the enemy's
     * spawn tile.
     */
    private void moveDead() {
        if ((x == xSpawn) && (y == ySpawn)) {
            if (resurrectionTimer < 0) {
                setAlive(true);
            }
            return;
        }

        targetX = (int) xSpawn / Tile.SIZE;
        targetY = (int) ySpawn / Tile.SIZE;

        setMovement(targetX, targetY);
    }

    /**
     * Moves the enemy, which is eating PacMan.
     * If this enemy is the one that caught PacMan and is eating him,
     * it is moved to PacMan's coordinates in 5 steps, creating
     * an animation of eating PacMan.
     */
    private void moveEating() {
        float pacManX = handler.getMap().getEntityManager().getPacMan().getX();
        float pacManY = handler.getMap().getEntityManager().getPacMan().getY();

        xMove += (1f / (5 - eatingPacManConstant)) * (pacManX - x);
        yMove += (1f / (5 - eatingPacManConstant)) * (pacManY - y);

        eatingPacManConstant = (eatingPacManConstant + 1) % 5;
    }

    /**
     * Finds free neighbour tiles to go to.
     * This method is only called when enemy is aligned
     * with the tile grid. It also ensures, that the enemy
     * won't turn around unless it is necessary (it is the
     * only possible move).
     */
    private void findFree() {
        free = new boolean[4];
        int freeCount = 0;

        free[Orientation.toInt(Orientation.RIGHT)] = (handler.getMap().getTile(xTile + 1, yTile).getId() ==
                Tile.empty.getId());
        free[Orientation.toInt(Orientation.LEFT)] = (handler.getMap().getTile(xTile - 1, yTile).getId() ==
                Tile.empty.getId());
        free[Orientation.toInt(Orientation.UP)] = (handler.getMap().getTile(xTile, yTile - 1).getId() ==
                Tile.empty.getId());
        free[Orientation.toInt(Orientation.DOWN)] = (handler.getMap().getTile(xTile, yTile + 1).getId() ==
                Tile.empty.getId());

        for (boolean freeTile : free) {
            if (freeTile) {
                freeCount++;
            }
        }

        //ensuring ghosts wouldn't get stuck in tunnels (no turning back other than single-choice corners)
        if (freeCount > 1) {
            switch (orientation) {
                case RIGHT:
                    free[Orientation.toInt(Orientation.LEFT)] = false;
                    break;
                case LEFT:
                    free[Orientation.toInt(Orientation.RIGHT)] = false;
                    break;
                case UP:
                    free[Orientation.toInt(Orientation.DOWN)] = false;
                    break;
                case DOWN:
                    free[Orientation.toInt(Orientation.UP)] = false;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Chooses a free neighbour tile which is the closest
     * to the target tile and sets enemy's movement in that
     * direction.
     *
     * @param targetX x-coordinate of target tile
     * @param targetY y-coordinate of target tile
     */
    private void setMovement(int targetX, int targetY) {
        for (int i = 0; i < free.length; i++) {
            if (free[i]) {
                double distance = distance(xTile + dX[i], yTile + dY[i], targetX, targetY);
                if (distance < currentMinimal) {
                    currentMinimal = distance;
                    xMove = dX[i] * speed;
                    yMove = dY[i] * speed;
                }
            }
        }
    }

    /**
     * Immediately flips the enemy's orientation.
     * This method is called whenever there is a change
     * in enemies' movement mode, for example from chase
     * to scatter or from blue ghosts to chase.
     */
    public void flipOrientation() {
        switch (orientation) {
            case RIGHT:
                orientation = Orientation.LEFT;
                break;
            case LEFT:
                orientation = Orientation.RIGHT;
                break;
            case UP:
                orientation = Orientation.DOWN;
                break;
            case DOWN:
                orientation = Orientation.UP;
                break;
            default:
                break;
        }
    }

    /**
     * Gets euclidean distance between two tiles.
     *
     * @param x1 x-coordinate of first tile
     * @param y1 y-coordinate of first tile
     * @param x2 x-coordinate of second tile
     * @param y2 y-coordinate of second tile
     * @return euclidean distance between the two tiles
     */
    double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Chooses which calculation of next enemy move should be done
     * and calls it.
     */
    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        if (handler.getMap().notLevelBegin()) {
            // if this is the enemy eating PacMan, move this accordingly and return
            if (handler.getMap().isEatingPacMan()) {
                if (handler.getMap().getEntityManager().getPacManEater() == this) {
                    moveEating();
                    x += xMove;
                    y += yMove;
                    return;
                }
            }

            // if enemy is not aligned to the tile grid, continue movement in current direction
            if (x % 40 != 0) {
                switch (orientation) {
                    case RIGHT:
                        xMove += speed;
                        break;
                    case LEFT:
                        xMove -= speed;
                        break;
                    default:
                        break;
                }
            } else if (y % 40 != 0) {
                switch (orientation) {
                    case UP:
                        yMove -= speed;
                        break;
                    case DOWN:
                        yMove += speed;
                        break;
                    default:
                        break;
                }
            } else {
                // getting ghosts position in tile-coordinates (this code is only reached, if it is aligned to the grid)
                xTile = (int) x / Tile.SIZE;
                yTile = (int) y / Tile.SIZE;

                // find options for next move and reset currentMinimal
                findFree();
                currentMinimal = Double.POSITIVE_INFINITY;

                // adjust speed and move regarding to current game situation
                if (!alive) {
                    resurrectionTimer--;
                    speed = 8;
                    moveDead();
                } else if (handler.getMap().isBlueGhosts()) {
                    speed = 1;
                    moveBlue();
                } else if (handler.getMap().isChase()) {
                    speed = 2.5f;
                    moveChase();
                } else if (handler.getMap().isScatter()) {
                    speed = 4;
                    moveScatter();
                }
            }

            move();
        }
    }

    /**
     * Draws enemy if it is dead or if the game is in blue-ghosts mode.
     *
     * @param g Graphics instance on which the images
     *          are rendered
     */
    @Override
    public void render(Graphics g) {
        rendered = false;
        if (notAlive()) {
            g.drawImage(Assets.enemyDead, (int) x, (int) y, width, height, null);
            rendered = true;
            return;
        }
        if (handler.getMap().isBlueGhosts()) {
            if (!immuneToBlue) {
                g.drawImage(Assets.enemyBlue, (int) x, (int) y, width, height, null);
                rendered = true;
            }
        }
    }

    //region Getters & Setters

    /**
     * Sets alive status of enemy and adjusts local variables
     * according to new alive status.
     *
     * @param alive new alive status of enemy
     */
    @Override
    public void setAlive(boolean alive) {
        super.setAlive(alive);
        if (alive) {
            immuneToBlue = true;
        } else {
            handler.getGame().setScore(handler.getGame().getScore() + 200);
            resurrectionTimer = 100;
        }
    }

    /**
     * Gets immunity status of enemy.
     *
     * @return <code>false</code>, if enemy is immune, <code>true</code> otherwise
     */
    public boolean notImmuneToBlue() {
        return !immuneToBlue;
    }

    /**
     * Sets immune status of enemy.
     *
     * @param immuneToBlue new immune status of enemy
     */
    public void setImmuneToBlue(boolean immuneToBlue) {
        this.immuneToBlue = immuneToBlue;
    }

    //endregion
}
