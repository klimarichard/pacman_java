package cz.cuni.mff.java.klima.pacman.userinput;

import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The <code>MouseManager</code> class is responsible
 * for getting user mouse input.
 */
public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean leftPressed, rightPressed;
    private int mouseX, mouseY;
    private UserInterfaceManager manager;

    /**
     * Stores the pressed button to local variable.
     *
     * @param e a mouse event triggering the method
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }
    }

    /**
     * Stores the unpressed button to local variable.
     *
     * @param e a mouse event triggering the method
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }

        if (manager != null) {
            manager.onMouseRelease(e);
        }
    }

    /**
     * Stores mouse current coordinates to local variables.
     *
     * @param e a mouse movement triggering the method
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (manager != null) {
            manager.onMouseMove(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    //region Getters & Setters

    /**
     * Sets the user interface manager.
     *
     * @param manager new user interface manager
     */
    public void setManager(UserInterfaceManager manager) {
        this.manager = manager;
    }
    //endregion
}
