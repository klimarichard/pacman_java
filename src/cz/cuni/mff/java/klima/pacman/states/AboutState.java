package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceButton;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceImage;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

/**
 * The <code>AboutState</code> class is responsible
 * for creating and rendering the About state.
 */
public class AboutState extends State {

    /**
     * Constructor of the <code>AboutState</code> class.
     * It calls the super constructor and then calls the
     * <code>createAboutState</code> method to initialize
     * the appearance of the state.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     */
    public AboutState(Handler handler) {
        super(handler);
        manager = new UserInterfaceManager(handler);
        createAboutState();
    }

    /**
     * Creates all user interface elements of the state.
     */
    private void createAboutState() {
        // main game image on top of the window
        manager.addObject(new UserInterfaceImage((handler.getGame().getWidth() - Assets.title.getWidth()) / 2f,
                50, Assets.title.getWidth(), Assets.title.getHeight(), Assets.title));

        // about game text
        manager.addObject(new UserInterfaceImage((handler.getGame().getWidth() - Assets.about.getWidth()) / 2f,
                265, Assets.about.getWidth(), Assets.about.getHeight(), Assets.about));

        // main menu button
        manager.addObject(new UserInterfaceButton((handler.getGame().getWidth() - BUTTON_WIDTH) / 2f,
                (handler.getGame().getHeight() * 5f) / 6, BUTTON_WIDTH, BUTTON_HEIGHT,
                Assets.menuButton, () -> {
            State.setState(handler.getGame().menuState);
            handler.getMouseManager().setManager(State.getState().getManager());
        }));
    }
}
