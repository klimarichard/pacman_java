package cz.cuni.mff.java.klima.pacman.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * The <code>Display</code> class controls the game's main window.
 * It holds the window itself and a canvas for drawing
 * game images on.
 */
public class Display {

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;

    /**
     * Constructor of the <code>Display</code> class. It takes the window's
     * name, width and height as parameters. It also calls
     * the <code>createDisplay</code> method to create the main
     * window.
     *
     * @param title  a String containing name of the window
     * @param width  width of the window
     * @param height height of the window
     */
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    /**
     * Creates the main window and the canvas and initializes
     * their parameters.
     */
    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(Assets.icon);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    //region Getters

    /**
     * Gets the window's canvas.
     *
     * @return the window's canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Gets the window.
     *
     * @return the window
     */
    public JFrame getFrame() {
        return frame;
    }
    //endregion
}
