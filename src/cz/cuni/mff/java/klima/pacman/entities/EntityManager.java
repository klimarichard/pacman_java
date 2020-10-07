package cz.cuni.mff.java.klima.pacman.entities;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.entities.characters.PacMan;
import cz.cuni.mff.java.klima.pacman.entities.characters.enemies.Enemy;
import cz.cuni.mff.java.klima.pacman.entities.characters.enemies.EnemyPink;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <code>EntityManager</code> is a class that holds all entities present
 * in current game. It contains methods for handling events
 * happening to entities.
 */
public class EntityManager {

    private Handler handler;
    private PacMan pacMan;
    private Enemy pacManEater;
    private EnemyPink pinkie;
    private ArrayList<Entity> entities;

    /**
     * Constructor of <code>EntityManager</code> class takes the game
     * handler for having access to the game map and its
     * elements and an instance of <code>PacMan</code>.
     * <p>
     * It also initializes the list of entities.
     *
     * @param handler game handler for accessing other
     *                elements of the game
     * @param pacMan  instance of PacMan character
     */
    public EntityManager(Handler handler, PacMan pacMan) {
        this.handler = handler;
        this.pacMan = pacMan;
        entities = new ArrayList<>();
    }

    /**
     * Adds food to the list of entities.
     * Food is always added to the front of the list,
     * in order to render it first.
     *
     * @param e instance of Entity (this method is only
     *          called when the instance is of type
     *          Food)
     */
    public void addFood(Entity e) {
        entities.add(0, e);
    }

    /**
     * Adds moving entity to the list of entities.
     * Moving entities are always added to the rear of
     * the list, in order to render them last.
     *
     * @param e instance of Entity (this method is only
     *          called when the instance is of type
     *          Moving)
     */
    public void addMoving(Entity e) {
        entities.add(e);
    }

    /**
     * Ensures that all entities in the list of entities
     * perform their tick method.
     */
    public void tick() {
        Iterator<Entity> i = entities.iterator();

        while (i.hasNext()) {
            Entity entity = i.next();
            entity.tick();
            // if the food was eaten in the last tick, remove it
            if (!entity.isActive()) {
                i.remove();
            }
        }
    }

    /**
     * Ensures that all entities in the list of entities
     * perform their render method.
     *
     * @param g Graphics instance on which the images are rendered
     */
    public void render(Graphics g) {
        for (Entity entity : entities) {
            entity.render(g);
        }
    }

    //region Getters & Setters

    /**
     * Gets game handler.
     *
     * @return game handler
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * Sets game handler.
     *
     * @param handler new game handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * Gets PacMan.
     *
     * @return PacMan
     */
    public PacMan getPacMan() {
        return pacMan;
    }

    /**
     * Gets the enemy, which is currently eating PacMan.
     *
     * @return enemy currently eating PacMan
     */
    public Enemy getPacManEater() {
        return pacManEater;
    }

    /**
     * Sets the enemy, which is currently eating PacMan.
     *
     * @param pacManEater new PacMan eater
     */
    public void setPacManEater(Enemy pacManEater) {
        this.pacManEater = pacManEater;
    }

    /**
     * Gets the pink enemy.
     *
     * @return the pink enemy
     */
    public EnemyPink getPinkie() {
        return pinkie;
    }

    /**
     * Sets the pink enemy.
     *
     * @param pinkie new pink enemy
     */
    public void setPinkie(EnemyPink pinkie) {
        this.pinkie = pinkie;
    }

    /**
     * Gets the list of entities.
     *
     * @return the list of entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Sets the list of entities.
     *
     * @param entities new list of entities
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
    //endregion
}
