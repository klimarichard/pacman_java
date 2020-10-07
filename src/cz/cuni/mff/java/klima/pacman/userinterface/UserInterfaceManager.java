package cz.cuni.mff.java.klima.pacman.userinterface;

import cz.cuni.mff.java.klima.pacman.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The <code>UserInterfaceManager</code> class is responsible
 * for updating and rendering all UI objects present in the game.
 */
public class UserInterfaceManager {

    private Handler handler;
    private ArrayList<UserInterfaceObject> objects;

    /**
     * Constructor of the <code>UserInterfaceManager</code> class.
     * It takes the game handler, for having access to all elements
     * of the game, as a parameter. It also initializes the
     * <code>objects</code> array.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     */
    public UserInterfaceManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
    }

    /**
     * Updates all objects in the <code>objects</code> array.
     */
    public void tick() {
        for (UserInterfaceObject object : objects) {
            object.tick();
        }
    }

    /**
     * Draws all objects from the <code>objects</code> array.
     *
     * @param g Graphics instance on which the images are rendered
     */
    public void render(Graphics g) {
        for (UserInterfaceObject object : objects) {
            object.render(g);
        }
    }

    /**
     * Performs actions on mouse move on all objects
     * from the <code>objects</code> array.
     *
     * @param e a mouse move triggering the method
     */
    public void onMouseMove(MouseEvent e) {
        for (UserInterfaceObject object : objects) {
            object.onMouseMove(e);
        }
    }

    /**
     * Performs actions on mouse release on all objects
     * from the <code>objects</code> array.
     *
     * @param e a mouse event triggering the method
     */
    public void onMouseRelease(MouseEvent e) {
        for (UserInterfaceObject object : objects) {
            object.onMouseRelease(e);
        }
    }

    /**
     * Adds UI object to the <code>objects</code> array.
     *
     * @param object the object to be added
     */
    public void addObject(UserInterfaceObject object) {
        objects.add(object);
    }

    //region Getters & Setters

    /**
     * Gets the game handler.
     *
     * @return the game handler
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * Sets the game handler.
     *
     * @param handler new game handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    //endregion
}
