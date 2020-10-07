package cz.cuni.mff.java.klima.pacman.userinterface;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The <code>UserInterfaceButton</code> class is responsible
 * for actions and rendering of all buttons in the game.
 */
public class UserInterfaceButton extends UserInterfaceObject {

    private BufferedImage[] images;
    private ClickListener clicker;

    /**
     * Constructor of the <code>UserInterfaceButton</code> class.
     * It takes two more parameters than the super constructor -
     * an array of images containing images for non-hover and hover
     * appearance of the button, and an implementation of
     * <code>ClickListener</code> interface.
     *
     * @param x       x-coordinate of the upper left corner
     * @param y       y-coordinate of the upper left corner
     * @param width   button width
     * @param height  button height
     * @param images  an array of images containing non-hover and hover
     *                appearance of the button
     * @param clicker an implementation of the <code>ClickListener</code>
     *                interface
     */
    public UserInterfaceButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }

    @Override
    public void tick() {

    }

    /**
     * Draws button image depending on the hover status.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        if (hover) {
            g.drawImage(images[1], (int) x, (int) y, null);
        } else {
            g.drawImage(images[0], (int) x, (int) y, null);
        }
    }

    /**
     * Performs an action when the button is clicked.
     */
    @Override
    public void onClick() {
        clicker.onClick();
        hover = false;
    }
}
