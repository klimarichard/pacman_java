package cz.cuni.mff.java.klima.pacman.map.tiles;

import cz.cuni.mff.java.klima.pacman.graphics.Assets;

/**
 * The Empty tile class holds data for empty tiles.
 */
class Empty extends Tile {

    /**
     * Constructor of the Empty tile class. It takes
     * the empty tile's unique id as parameter.
     *
     * @param id empty tile id
     */
    Empty(int id) {
        super(Assets.empty, id);
    }
}
