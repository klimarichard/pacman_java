package cz.cuni.mff.java.klima.pacman.userinterface;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The <code>UserInterfaceImage</code> class is responsible
 * for actions and rendering of all non-play images in the game.
 */
public class UserInterfaceImage extends UserInterfaceObject {

    private BufferedImage image;

    /**
     * Constructor of the <code>UserInterfaceImage</code> class.
     * It takes one more parameter than the super constructor -
     * an image to be drawn on the screen.
     *
     * @param x      x-coordinate of the upper left corner
     * @param y      y-coordinate of the upper left corner
     * @param width  button width
     * @param height button height
     * @param image  an image
     */
    public UserInterfaceImage(float x, float y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.image = image;
    }

    @Override
    public void tick() {

    }

    /**
     * Draws the image.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    @Override
    public void onClick() {

    }
}
