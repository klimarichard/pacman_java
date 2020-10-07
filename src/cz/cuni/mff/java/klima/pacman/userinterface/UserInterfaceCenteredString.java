package cz.cuni.mff.java.klima.pacman.userinterface;

import java.awt.*;

/**
 * The <code>UserInterfaceCenteredString</code> class is responsible
 * for actions and rendering of all labels in the game, whose texts are
 * aligned to center.
 */
public class UserInterfaceCenteredString extends UserInterfaceObject {
    private final Font FONT = new Font("Broadway", Font.PLAIN, 88);

    private int width;

    private String text;

    /**
     * Constructor of the <code>UserInterfaceCenteredString</code> class.
     * It takes the y-coordinate of the upper left corner, the width and
     * the label text as parameters.
     *
     * @param y     y-coordinate of the upper left corner
     * @param width label width
     * @param text  a string containing the text of the label
     */
    public UserInterfaceCenteredString(float y, int width, String text) {
        super(0, y, 0, 0);
        this.width = width;
        this.text = text;
    }

    @Override
    public void tick() {

    }

    /**
     * Calculates pixel size of the string and draws in on the screen.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        FontMetrics fontMetrics = g.getFontMetrics(FONT);

        float actualX = x + (width - fontMetrics.stringWidth(text)) / 2f;
        g.setFont(FONT);
        g.setColor(new Color(255, 255, 255));
        g.drawString(text, (int) actualX, (int) y);
    }

    @Override
    public void onClick() {

    }

    //region Getters & Setters

    /**
     * Sets the label text.
     *
     * @param text new label text
     */
    public void setText(String text) {
        this.text = text;
    }
    //endregion
}
