package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceButton;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceImage;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

/**
 * The <code>MenuState</code> class is responsible
 * for creating and rendering the Menu state.
 */
public class MenuState extends State {

    /**
     * Constructor of the <code>MenuState</code> class.
     * It calls the super constructor and then calls the
     * <code>createMenuState</code> method to initialize
     * the appearance of the state.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     */
    public MenuState(Handler handler) {
        super(handler);
        manager = new UserInterfaceManager(handler);
        createMenuState();
    }

    /**
     * Creates all user interface elements of the state.
     */
    private void createMenuState() {
        // main game image on top of the window
        manager.addObject(new UserInterfaceImage((handler.getGame().getWidth() - Assets.title.getWidth()) / 2f,
                50, Assets.title.getWidth(), Assets.title.getHeight(), Assets.title));

        // play button
        manager.addObject(new UserInterfaceButton((handler.getGame().getWidth() - BUTTON_WIDTH) / 2f,
                (handler.getGame().getHeight() - BUTTON_HEIGHT) / 2f,
                BUTTON_WIDTH, BUTTON_HEIGHT, Assets.playButton,
                () -> {
                    State.setState(handler.getGame().gameState);
                    handler.getMouseManager().setManager(State.getState().getManager());
                }));

        // rules & controls button
        manager.addObject(new UserInterfaceButton((handler.getGame().getWidth() - BUTTON_WIDTH) / 2f,
                (handler.getGame().getHeight() - BUTTON_HEIGHT) / 2f + BUTTON_DIFFERENCE,
                BUTTON_WIDTH, BUTTON_HEIGHT, Assets.rulesButton,
                () -> {
                    State.setState(handler.getGame().rulesState);
                    handler.getMouseManager().setManager(State.getState().getManager());
                }));

        // about button
        manager.addObject(new UserInterfaceButton((handler.getGame().getWidth() - BUTTON_WIDTH) / 2f,
                (handler.getGame().getHeight() - BUTTON_HEIGHT) / 2f + 2 * BUTTON_DIFFERENCE,
                BUTTON_WIDTH, BUTTON_HEIGHT, Assets.aboutButton,
                () -> {
                    State.setState(handler.getGame().aboutState);
                    handler.getMouseManager().setManager(State.getState().getManager());
                }));
    }
}
