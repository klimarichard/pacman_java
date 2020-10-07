package cz.cuni.mff.java.klima.pacman.map.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Tile class holds data for a single map tile.
 */
public class Tile {
    public static final int SIZE = 40;

    public static Tile[] tiles = new Tile[32];
    public static Tile wall = new Wall(0);
    public static Tile empty = new Empty(31);

    private final int id;
    private BufferedImage texture;

    /**
     * Constructor of the Tile class. It takes the image
     * that the tile is rendered with and a unique Tile type
     * id.
     *
     * @param texture an image of the tile
     * @param id      unique id depending on tile type
     */
    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    /**
     * Draws this tile's image on given coordinates.
     *
     * @param g Graphics instance on which the images are rendered
     * @param x x-coordinate to draw the image
     * @param y y-coordinate to draw the image
     */
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, SIZE, SIZE, null);
    }

    /**
     * Gets obstacle status of this tile.
     *
     * @return obstacle status of this tile
     */
    public boolean isObstacle() {
        return false;
    }

    /**
     * Gets id of this tile.
     *
     * @return id of this tile
     */
    public int getId() {
        return id;
    }
}
