package cz.cuni.mff.java.klima.pacman.map;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.entities.Entity;
import cz.cuni.mff.java.klima.pacman.entities.EntityManager;
import cz.cuni.mff.java.klima.pacman.entities.characters.Moving;
import cz.cuni.mff.java.klima.pacman.entities.characters.PacMan;
import cz.cuni.mff.java.klima.pacman.entities.characters.enemies.*;
import cz.cuni.mff.java.klima.pacman.entities.objects.*;
import cz.cuni.mff.java.klima.pacman.enums.Orientation;
import cz.cuni.mff.java.klima.pacman.loader.LevelLoader;
import cz.cuni.mff.java.klima.pacman.map.tiles.Tile;
import cz.cuni.mff.java.klima.pacman.states.GameOverState;
import cz.cuni.mff.java.klima.pacman.states.State;

import java.awt.*;

/**
 * The <code>Map</code> class is the class responsible for
 * controlling all the game characters by calling their
 * <code>tick</code> and <code>render</code> methods.
 */
public class Map {
    private final int WALL = 0;
    private final int KILLER_FOOD = 1;
    private final int BANANA = 2;
    private final int CHERRY = 3;
    private final int STRAWBERRY = 4;
    private final int ENEMY_PINK = 5;
    private final int ENEMY_PURPLE = 6;
    private final int ENEMY_RED = 7;
    private final int ENEMY_YELLOW = 8;
    private final int EMPTY_WITHOUT_FOOD = 9;
    private final int EMPTY = 31;

    private Handler handler;
    private int width, height;
    private int[][] tiles;
    private int foodCount;
    private int level;
    private EntityManager entityManager;

    private boolean blueGhosts, levelBegin, eatingPacMan;
    private boolean chase;
    private boolean scatter;
    private long blueGhostTimer, levelBeginTimer;
    private long chaseTimer, scatterTimer, eatingTimer, eatingInnerTimer;
    private long lastTime, chaseLastTime, scatterLastTime, eatingLastTime;

    /**
     * Constructor of the <code>Map</code> class. It takes the game handler,
     * for having access to all elements of the game, and level number as
     * parameters.
     * It calls the <code>loadMap</code> method to load the game map of
     * the correct level.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param level   current game level (map to be loaded)
     */
    public Map(Handler handler, int level) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new PacMan(handler, 0, 0));
        loadMap(level);
    }

    /**
     * Loads the level map from resource map file and initializes
     * all necessary local variables accordingly.
     *
     * @param level current game level
     */
    private void loadMap(int level) {
        // all levels are of same width and height (20 x 20)
        width = 20;
        height = 20;

        this.level = level;

        // initializing default level state (level is beginning, then chase mode first, no blue ghosts)
        blueGhosts = false;
        levelBegin = true;
        eatingPacMan = false;
        setChase(true);

        // before the level starts, the player has 3 seconds to examine it
        levelBeginTimer = 3000;
        lastTime = System.currentTimeMillis();

        String[] file = LevelLoader.loadMapToString("res/maps/" + level + ".lvl").split("\n");

        // first line of resource map file contains PacMan starting position
        String[] pacManCoordinates = file[0].split(" ");
        int pacManSpawnX = LevelLoader.parseInt(pacManCoordinates[0]);
        int pacManSpawnY = LevelLoader.parseInt(pacManCoordinates[1]);
        entityManager.getPacMan().setxSpawn(pacManSpawnX * Tile.SIZE);
        entityManager.getPacMan().setySpawn(pacManSpawnY * Tile.SIZE);
        entityManager.getPacMan().setX(entityManager.getPacMan().getxSpawn());
        entityManager.getPacMan().setY(entityManager.getPacMan().getySpawn());

        // initialize the tile array
        tiles = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // there is no food underneath PacMan's starting position
                if ((y == pacManSpawnY) && (x == pacManSpawnX)) {
                    tiles[x][y] = EMPTY;
                    continue;
                }

                // convert character from resource map file to integer
                int c = LevelLoader.charToInt(file[1 + y].charAt(x));

                switch (c) {
                    case WALL:
                        tiles[x][y] = WALL;
                        continue;
                    case KILLER_FOOD:
                        entityManager.addFood(new FoodKiller(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case BANANA:
                        entityManager.addFood(new FoodBanana(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case CHERRY:
                        entityManager.addFood(new FoodCherry(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case STRAWBERRY:
                        entityManager.addFood(new FoodStrawberry(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case ENEMY_PINK:
                        // pink enemy needs to be accessed by red enemy to properly count red enemy's target tile,
                        // hence it is stored in the entity manager by itself
                        EnemyPink pinkie = new EnemyPink(handler, x * Tile.SIZE, y * Tile.SIZE);
                        entityManager.addMoving(pinkie);
                        entityManager.setPinkie(pinkie);
                        break;
                    case ENEMY_PURPLE:
                        entityManager.addMoving(new EnemyPurple(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case ENEMY_RED:
                        entityManager.addMoving(new EnemyRed(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case ENEMY_YELLOW:
                        entityManager.addMoving(new EnemyYellow(handler, x * Tile.SIZE, y * Tile.SIZE));
                        break;
                    case EMPTY:
                        entityManager.addFood(new FoodNormal(handler, x * Tile.SIZE, y * Tile.SIZE));
                        foodCount++;
                        break;
                    case EMPTY_WITHOUT_FOOD:
                    default:
                        break;
                }

                tiles[x][y] = EMPTY;
            }
        }

        // PacMan is added as last to the entity manager, in order to render him as last
        entityManager.addMoving(entityManager.getPacMan());
    }

    /**
     * Restarts the moving entities positions after PacMan loses a life.
     * It also clears the keyboard input data.
     */
    private void restartAfterPacManDeath() {
        for (Entity e : entityManager.getEntities()) {
            if (e instanceof Moving) {
                e.setX(((Moving) e).getxSpawn());
                e.setY(((Moving) e).getySpawn());
            }
        }

        handler.getKeyManager().resetKeys();
        entityManager.getPacMan().setOrientation(Orientation.RIGHT);
        setLevelBegin(true);
    }

    /**
     * Decides what actions to make based on current game situation.
     */
    public void tick() {
        // when the level is in the levelBegin wait state, only PacMan is ticking (his animation doesn't stop)
        if (levelBegin) {
            long now = System.currentTimeMillis();
            levelBeginTimer -= (now - lastTime);
            lastTime = now;
            if (levelBeginTimer <= 0) {
                setLevelBegin(false);
            }
            entityManager.getPacMan().tick();
            return;
        }

        // if the PacMan is being eaten, only the ghost eating PacMan is moving, and PacMan's animation doesn't stop
        if (eatingPacMan) {
            long now = System.currentTimeMillis();
            eatingTimer -= (now - eatingLastTime);
            eatingLastTime = now;
            if (eatingInnerTimer++ % 15 == 0) {
                entityManager.getPacManEater().tick();
                entityManager.getPacMan().tick();
            }
            if (eatingTimer <= 0) {
                // PacMan lost all of his lives, game is over
                if (handler.getGame().getLives() == 0) {
                    State.setState(new GameOverState(handler));
                    handler.getMouseManager().setManager(State.getState().getManager());
                }
                setEatingPacMan(false, null);
                restartAfterPacManDeath();
            }
            return;
        }

        // every tick subtracts passed time from timer variable, so that the modes can properly interchange
        if (blueGhosts) {
            long now = System.currentTimeMillis();
            blueGhostTimer -= (now - lastTime);
            lastTime = now;
            if (blueGhostTimer <= 0) {
                setBlueGhosts(false);
            }
        } else if (chase) {
            long now = System.currentTimeMillis();
            chaseTimer -= (now - chaseLastTime);
            chaseLastTime = now;
            if (chaseTimer <= 0) {
                setChase(false);
                setScatter(true);
            }
        } else if (scatter) {
            long now = System.currentTimeMillis();
            scatterTimer -= (now - scatterLastTime);
            scatterLastTime = now;
            if (scatterTimer <= 0) {
                setScatter(false);
                setChase(true);
            }
        }

        entityManager.tick();
    }

    /**
     * Goes over the map tile by tile and renders them on the screen,
     * the does the same with all entities.
     *
     * @param g Graphics instance on which the images are rendered
     */
    public void render(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(g, x * Tile.SIZE, y * Tile.SIZE);
            }
        }

        entityManager.render(g);

        // if some ghost is eating PacMan, he is drawn once more, so he would appear as the topmost
        if (eatingPacMan) {
            entityManager.getPacManEater().render(g);
        }
    }

    /**
     * Gets tile on given coordinates.
     *
     * @param x x-coordinate of the tile
     * @param y y-coordinate of the tile
     * @return tile on given coordinates
     */
    public Tile getTile(int x, int y) {
        // if the coordinates are out of bounds, return wall
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height))
            return Tile.wall;

        Tile t = Tile.tiles[tiles[x][y]];

        // if the tile on given index in the tiles array is not initialized, return wall
        if (t == null) {
            return Tile.wall;
        }

        return t;
    }

    //region Getters & Setters

    /**
     * Gets map width in tiles.
     *
     * @return map width in tiles
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets map width in tiles.
     *
     * @param width new map width in tiles
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets map height in tiles.
     *
     * @return map height in tiles
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets map height in tiles.
     *
     * @param height new map height in tiles
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the map's entity manager.
     *
     * @return the map's entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Gets blue ghosts status.
     *
     * @return <code>true</code>, if blue ghosts mode is active, <code>false</code> otherwise
     */
    public boolean isBlueGhosts() {
        return blueGhosts;
    }

    /**
     * Sets blue ghosts status.
     *
     * @param blueGhosts new blue ghosts status
     */
    public void setBlueGhosts(boolean blueGhosts) {
        this.blueGhosts = blueGhosts;
        if (blueGhosts) {
            blueGhostTimer = 5000;
            lastTime = System.currentTimeMillis();

            for (Entity e : entityManager.getEntities()) {
                if (e instanceof Enemy) {
                    ((Enemy) e).setImmuneToBlue(false);
                    ((Enemy) e).flipOrientation();
                }
            }

        } else {
            for (Entity e : entityManager.getEntities()) {
                if (e instanceof Enemy) {
                    ((Enemy) e).flipOrientation();
                }
            }

            if (chase) {
                chaseLastTime = System.currentTimeMillis();
            }
            if (scatter) {
                scatterLastTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Gets chase status.
     *
     * @return <code>true</code>, if chase mode is active, <code>false</code> otherwise
     */
    public boolean isChase() {
        return chase;
    }

    /**
     * Sets chase status.
     *
     * @param chase new chase status
     */
    private void setChase(boolean chase) {
        this.chase = chase;
        if (chase) {
            chaseTimer = 10000;
            chaseLastTime = System.currentTimeMillis();
            for (Entity e : entityManager.getEntities()) {
                if (e instanceof Enemy) {
                    ((Enemy) e).flipOrientation();
                }
            }
        }
    }

    /**
     * Gets scatter status.
     *
     * @return <code>true</code>, if scatter mode is active, <code>false</code> otherwise
     */
    public boolean isScatter() {
        return scatter;
    }

    /**
     * Sets scatter status.
     *
     * @param scatter new scatter status
     */
    private void setScatter(boolean scatter) {
        this.scatter = scatter;
        if (scatter) {
            scatterTimer = 10000 - (level - 1) * 1500;
            scatterLastTime = System.currentTimeMillis();
            for (Entity e : entityManager.getEntities()) {
                if (e instanceof Enemy) {
                    ((Enemy) e).flipOrientation();
                }
            }
        }
    }

    /**
     * Gets eating PacMan status.
     *
     * @return <code>true</code>, if eating PacMan mode is active, <code>false</code> otherwise
     */
    public boolean isEatingPacMan() {
        return eatingPacMan;
    }

    /**
     * Sets eating PacMan status.
     *
     * @param eatingPacMan new eating PacMan status
     * @param e            the enemy eating PacMan
     */
    public void setEatingPacMan(boolean eatingPacMan, Enemy e) {
        this.eatingPacMan = eatingPacMan;
        entityManager.setPacManEater(e);
        if (eatingPacMan) {
            eatingInnerTimer = 0;
            eatingTimer = 1500;
            eatingLastTime = System.currentTimeMillis();
        } else {
            if (chase) {
                chaseLastTime = System.currentTimeMillis();
            }
            if (scatter) {
                scatterLastTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Gets level begin status.
     *
     * @return <code>false</code>, if level begin mode is active, <code>true</code> otherwise
     */
    public boolean notLevelBegin() {
        return !levelBegin;
    }

    /**
     * Sets level begin status.
     *
     * @param levelBegin new level begin status
     */
    private void setLevelBegin(boolean levelBegin) {
        this.levelBegin = levelBegin;
        if (levelBegin) {
            levelBeginTimer = 1000;
            lastTime = System.currentTimeMillis();
        } else {
            if (chase) {
                chaseLastTime = System.currentTimeMillis();
            }
            if (scatter) {
                scatterLastTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Sets last time.
     *
     * @param lastTime new value of last time
     */
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Gets current food count.
     *
     * @return current food count
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Sets current food count.
     *
     * @param foodCount new food count
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }
    //endregion
}
