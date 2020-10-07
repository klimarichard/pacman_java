package cz.cuni.mff.java.klima.pacman.graphics;

import java.awt.image.BufferedImage;

/**
 * The <code>Animation</code> class holds all necessary data
 * for images that should be rendered with some
 * animations.
 */
public class Animation {

    private int speed, index;
    private long lastTime, timer;
    private BufferedImage[] types;

    /**
     * Constructor of the <code>Animation</code> class. It takes the speed
     * of the animation and an array of images that contains
     * the animation frame by frame. It also sets local
     * variables, such as the indexing variable and timer
     * variables.
     * <p>
     * The <code>speed</code> field of the animation is a millisecond value,
     * which means, that for example for <code>speed = 50</code>, the current
     * frame of the animation will change every 50 ms, resulting in
     * 20 frames per second animation.
     *
     * @param speed animation speed
     * @param types an array of images with the animation
     *              frame by frame.
     */
    public Animation(int speed, BufferedImage[] types) {
        this.speed = speed;
        this.types = types;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    /**
     * Decides, whether it is time to change the indexing
     * variable of the animation array.
     * That means, that if more time than desired for
     * animation <code>speed</code> has passed, the next frame will be
     * indexed in <code>getCurrentType</code> method.
     */
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            // after last frame, show the first
            if (index >= types.length) {
                index = 0;
            }
        }
    }

    /**
     * Gets current animation frame from animation array.
     * The indexing variable is set in the <code>tick</code>
     * method.
     *
     * @return current animation frame
     */
    public BufferedImage getCurrentType() {
        return types[index];
    }
}
