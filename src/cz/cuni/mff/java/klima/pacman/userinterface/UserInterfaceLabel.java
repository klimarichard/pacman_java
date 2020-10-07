package cz.cuni.mff.java.klima.pacman.userinterface;

import java.awt.*;

/**
 * The <code>UserInterfaceLabel</code> class is responsible
 * for actions and rendering of all labels in the game.
 */
public class UserInterfaceLabel extends UserInterfaceObject {
    private final Font FONT = new Font("Broadway", Font.PLAIN, 36);

    private String fixed;
    private String changeable;

    /**
     * Constructor of the <code>UserInterfaceLabel</code> class.
     * It takes x and y-coordiantes of the upper left corner,
     * a fixed text and a changeable text as parameters.
     *
     * @param x          x-coordinate of the upper left corner
     * @param y          y-coordinate of the upper left corner
     * @param fixed      a string with the fixed text
     * @param changeable a string with the changeable text
     */
    public UserInterfaceLabel(float x, float y, String fixed, String changeable) {
        super(x, y, 0, 0);
        this.fixed = fixed;
        this.changeable = changeable;
    }

    @Override
    public void tick() {

    }

    /**
     * Draws the label on the screen.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        g.setFont(FONT);
        g.setColor(new Color(255, 255, 255));
        g.drawString(fixed + " " + changeable, (int) x, (int) y);
    }

    @Override
    public void onClick() {

    }

    //region Getters & Setters

    /**
     * Sets the changeable text.
     *
     * @param changeable new changeable text
     */
    public void setChangeable(String changeable) {
        this.changeable = changeable;
    }
    //endregion
}
