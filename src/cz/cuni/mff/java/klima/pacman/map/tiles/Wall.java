package cz.cuni.mff.java.klima.pacman.map.tiles;

import cz.cuni.mff.java.klima.pacman.graphics.Assets;

/**
 * The Wall tile class holds data for wall tiles.
 */
public class Wall extends Tile {

    /**
     * Constructor of the Wall tile class. It takes
     * the wall tile's unique id as parameter.
     *
     * @param id wall tile id
     */
    Wall(int id) {
        super(Assets.wall, id);
    }

    /**
     * Gets obstacle status of this tile.
     *
     * @return obstacle status of this tile
     */
    @Override
    public boolean isObstacle() {
        return true;
    }
}
