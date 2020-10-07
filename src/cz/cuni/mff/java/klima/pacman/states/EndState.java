package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceButton;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceCenteredString;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceImage;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

import java.awt.image.BufferedImage;

/**
 * <code>EndState</code> is an abstract class responsible
 * for creating and rendering the end states.
 */
public abstract class EndState extends State {

    private UserInterfaceCenteredString labelScore;
    private BufferedImage image;

    /**
     * Constructor of the <code>EndState</code> abstract class.
     * It calls the super constructor and then calls the
     * <code>createEndState</code> method to initialize
     * the appearance of the state.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     * @param image   an image displayed on top of the window
     */
    EndState(Handler handler, BufferedImage image) {
        super(handler);
        manager = new UserInterfaceManager(handler);
        this.image = image;
        createEndState();
    }

    /**
     * Creates all user interface elements of the state.
     */
    private void createEndState() {
        manager.addObject(new UserInterfaceImage((handler.getGame().getWidth() - image.getWidth()) / 2f,
                50, image.getWidth(), image.getHeight(), image));

        labelScore = new UserInterfaceCenteredString(450, handler.getWidth(),
                Integer.toString(handler.getGame().getScore()));
        manager.addObject(labelScore);

        manager.addObject(new UserInterfaceButton((handler.getGame().getWidth() - BUTTON_WIDTH) / 2f,
                (handler.getGame().getHeight() * 5f) / 6, BUTTON_WIDTH, BUTTON_HEIGHT,
                Assets.menuButton, () -> {
            State.setState(handler.getGame().menuState);
            handler.getMouseManager().setManager(State.getState().getManager());
            handler.resetGame();
        }));
    }

    /**
     * Updates score count and all other user interface elements.
     */
    @Override
    public void tick() {
        labelScore.setText(Integer.toString(handler.getGame().getScore()));
        super.tick();
    }
}
