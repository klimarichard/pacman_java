package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;

/**
 * The <code>GameOverState</code> class is responsible
 * for loading <code>game_over</code> image into
 * <code>EndState</code> abstract class.
 */
public class GameOverState extends EndState {
    public GameOverState(Handler handler) {
        super(handler, Assets.game_over);
    }
}
