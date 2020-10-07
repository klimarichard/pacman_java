package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceButton;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceImage;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

/**
 * The <code>RulesState</code> class is responsible
 * for creating and rendering the Rules state.
 */
public class RulesState extends State {

    /**
     * Constructor of the <code>RulesState</code> class.
     * It calls the super constructor and then calls the
     * <code>createRulesState</code> method to initialize
     * the appearance of the state.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     */
    public RulesState(Handler handler) {
        super(handler);
        manager = new UserInterfaceManager(handler);
        createRulesState();
    }

    /**
     * Creates all user interface elements of the state.
     */
    private void createRulesState() {
        // main game image on top of the window
        manager.addObject(new UserInterfaceImage((handler.getGame().getWidth() - Assets.title.getWidth()) / 2f,
                50, Assets.title.getWidth(), Assets.title.getHeight(), Assets.title));

        // rules & controls text
        manager.addObject(new UserInterfaceImage(
                (handler.getGame().getWidth() - Assets.rules_controls.getWidth()) / 2f,
                265, Assets.rules_controls.getWidth(),
                Assets.rules_controls.getHeight(), Assets.rules_controls));

        // play button
        manager.addObject(new UserInterfaceButton(
                handler.getGame().getWidth() - RIGHT_COLUMN_SIZE + ((RIGHT_COLUMN_SIZE - BUTTON_WIDTH) / 2f),
                (handler.getGame().getHeight() * 5f) / 6,
                BUTTON_WIDTH, BUTTON_HEIGHT, Assets.playButton, () -> {
            State.setState(handler.getGame().gameState);
            handler.getMouseManager().setManager(State.getState().getManager());
        }));

        // menu button
        manager.addObject(new UserInterfaceButton((RIGHT_COLUMN_SIZE - BUTTON_WIDTH) / 2f,
                (handler.getGame().getHeight() * 5f) / 6,
                BUTTON_WIDTH, BUTTON_HEIGHT, Assets.menuButton, () -> {
            State.setState(handler.getGame().menuState);
            handler.getMouseManager().setManager(State.getState().getManager());
        }));
    }
}
