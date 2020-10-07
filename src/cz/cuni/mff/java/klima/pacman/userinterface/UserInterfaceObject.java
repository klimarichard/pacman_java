package cz.cuni.mff.java.klima.pacman.userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * <code>UserInterfaceObject</code> is an abstract class for
 * all different types of user interface elements in the game.
 */
public abstract class UserInterfaceObject {
    protected float x, y;
    protected int width, height;
    boolean hover = false;
    private Rectangle bounds;

    /**
     * Constructor of the abstract UI object class always
     * called by all UI objects present in the game.
     * It takes x and y-coordinates of upper left corner
     * of the object and its width and height as parameters.
     * <p>
     * It also initializes the bounding rectangle.
     *
     * @param x      x-coordinate of the upper left corner
     * @param y      y-coordinate of the upper left corner
     * @param width  object width
     * @param height object height
     */
    UserInterfaceObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void onClick();

    /**
     * Sets hover variable to <code>true</code> if mouse
     * position is within the bounding rectangle.
     *
     * @param e a mouse move triggering the method
     */
    void onMouseMove(MouseEvent e) {
        hover = bounds.contains(e.getX(), e.getY());
    }

    /**
     * Calls the <code>onClick</code> method is mouse is released
     * when within the bounding rectangle.
     *
     * @param e a mouse event triggering the method
     */
    void onMouseRelease(MouseEvent e) {
        if (hover) {
            onClick();
        }
    }

    //region Getters & Setters

    /**
     * Gets x-coordinate.
     *
     * @return x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets x-coordinate.
     *
     * @param x new x-coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets y-coordinate.
     *
     * @return y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets y-coordinate.
     *
     * @param y new y-coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets object width.
     *
     * @return object width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets object width.
     *
     * @param width new object width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets object height.
     *
     * @return object height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets object height.
     *
     * @param height new object height
     */
    public void setHeight(int height) {
        this.height = height;
    }
    //endregion
}
