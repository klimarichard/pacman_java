package cz.cuni.mff.java.klima.pacman.userinput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The <code>KeyManager</code> class is responsible
 * for getting user keyboard input.
 */
public class KeyManager implements KeyListener {

    public boolean up, down, left, right;
    private boolean[] keys;

    /**
     * Constructor of the <code>KeyManager</code> class.
     */
    public KeyManager() {
        keys = new boolean[4];
    }

    /**
     * Updates pressed arrow keys.
     */
    public void tick() {
        up = keys[0];
        down = keys[1];
        left = keys[2];
        right = keys[3];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Updates pressed keys array.
     *
     * @param e a key event triggering the method
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_UP:
                keys[0] = true;
                keys[1] = false;
                keys[2] = false;
                keys[3] = false;
                break;
            case KeyEvent.VK_DOWN:
                keys[0] = false;
                keys[1] = true;
                keys[2] = false;
                keys[3] = false;
                break;
            case KeyEvent.VK_LEFT:
                keys[0] = false;
                keys[1] = false;
                keys[2] = true;
                keys[3] = false;
                break;
            case KeyEvent.VK_RIGHT:
                keys[0] = false;
                keys[1] = false;
                keys[2] = false;
                keys[3] = true;
                break;
            default:
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Resets all pressed keys, so that none is seen as pressed.
     */
    public void resetKeys() {
        keys[0] = false;
        keys[1] = false;
        keys[2] = false;
        keys[3] = false;
    }
}
