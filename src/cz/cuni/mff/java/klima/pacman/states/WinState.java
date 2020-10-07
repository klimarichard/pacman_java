package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;

/**
 * The <code>WinState</code> class is responsible
 * for loading <code>win</code> image into
 * <code>EndState</code> abstract class.
 */
class WinState extends EndState {
    WinState(Handler handler) {
        super(handler, Assets.win);
    }
}
