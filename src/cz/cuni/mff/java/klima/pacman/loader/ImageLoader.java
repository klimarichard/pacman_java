package cz.cuni.mff.java.klima.pacman.loader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The <code>ImageLoader</code> class controls loading of images
 * that are used in the game from resource files.
 * It contains static methods only.
 */
public class ImageLoader {

    /**
     * Loads image from resource file.
     *
     * @param path a string containing path to the resource file
     * @return an image from the resource file or <code>null</code>, if the path doesn't exist
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
