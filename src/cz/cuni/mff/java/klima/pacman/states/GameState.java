package cz.cuni.mff.java.klima.pacman.states;

import cz.cuni.mff.java.klima.pacman.Handler;
import cz.cuni.mff.java.klima.pacman.graphics.Animation;
import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.map.Map;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceButton;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceLabel;
import cz.cuni.mff.java.klima.pacman.userinterface.UserInterfaceManager;

import java.awt.*;

/**
 * The <code>GameState</code> class is responsible
 * for creating and rendering the Games state, as well
 * as for some parts of the game itself.
 * <p>
 * It holds the current level of the game and the game map.
 */
public class GameState extends State {

    private Map map;
    private int level;
    private UserInterfaceLabel labelScore, labelHighScore, labelLevel, labelLives;
    private Animation animation;

    /**
     * Constructor of the <code>GameState</code> class.
     * It calls the super constructor and then calls the
     * <code>createGameState</code> method to initialize
     * the appearance of the state. It also initializes
     * local variables, such as level, animation and map.
     *
     * @param handler game handler for accessing other elements
     *                of the game
     */
    public GameState(Handler handler) {
        super(handler);
        manager = new UserInterfaceManager(handler);
        level = 1;
        createGameState();
        animation = new Animation(80, Assets.pacManRight);
        map = new Map(handler, level);
        handler.setMap(map);
    }

    /**
     * Creates all user interface elements of the state.
     */
    private void createGameState() {
        // menu button
        manager.addObject(new UserInterfaceButton(
                handler.getGame().getWidth() - RIGHT_COLUMN_SIZE + ((RIGHT_COLUMN_SIZE - BUTTON_WIDTH) / 2f),
                (handler.getGame().getHeight() * 5f) / 6,
                BUTTON_WIDTH, BUTTON_HEIGHT, Assets.menuButton, () -> {
            State.setState(handler.getGame().menuState);
            handler.getMouseManager().setManager(State.getState().getManager());
        }));

        // level label
        labelLevel = new UserInterfaceLabel(
                handler.getGame().getWidth() - RIGHT_COLUMN_SIZE + RIGHT_COLUMN_INDENT,
                handler.getGame().getHeight() / 10f,
                "Level", Integer.toString(level));
        manager.addObject(labelLevel);

        // score label
        labelScore = new UserInterfaceLabel(
                handler.getGame().getWidth() - RIGHT_COLUMN_SIZE + RIGHT_COLUMN_INDENT,
                (handler.getGame().getHeight() * 3f) / 10,
                "Score:", Integer.toString(handler.getGame().getScore()));
        manager.addObject(labelScore);

        // high-score label
        labelHighScore = new UserInterfaceLabel(
                handler.getGame().getWidth() - RIGHT_COLUMN_SIZE + RIGHT_COLUMN_INDENT,
                (handler.getGame().getHeight() * 9f) / 20,
                "High score:", Integer.toString(handler.getGame().getHighScore()));
        manager.addObject(labelHighScore);

        // lives label
        labelLives = new UserInterfaceLabel(
                handler.getGame().getWidth() - RIGHT_COLUMN_SIZE + RIGHT_COLUMN_INDENT,
                (handler.getGame().getHeight() * 7f) / 10,
                "Lives:", "");
        manager.addObject(labelLives);
    }

    /**
     * Performs level up actions.
     * Depending on level number, it loads the new
     * level's map, or switches the game to Win state.
     */
    public void levelUp() {
        level++;
        int timer = 1500;
        long now = System.currentTimeMillis();
        long lastTime = System.currentTimeMillis();

        while ((lastTime - now) < timer) {
            lastTime = System.currentTimeMillis();
        }

        // there are only seven levels, if the player wins the seventh, he wins the game
        if (level > 7) {
            State.setState(new WinState(handler));
            handler.getMouseManager().setManager(State.getState().getManager());
        } else {
            map = new Map(handler, level);
            handler.setMap(map);
        }
    }

    /**
     * Updates level, score and high score count,
     * all other user interface elements and the map.
     */
    @Override
    public void tick() {
        labelLevel.setChangeable(Integer.toString(level));
        labelScore.setChangeable(Integer.toString(handler.getGame().getScore()));
        labelHighScore.setChangeable(Integer.toString(handler.getGame().getHighScore()));
        map.tick();
        animation.tick();
        super.tick();
    }

    /**
     * Draws all user interface elements, the map and lives count.
     *
     * @param g Graphics instance on which the images are rendered
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        map.render(g);

        // draw as many lives, as are remaining
        switch (handler.getGame().getLives()) {
            case 3:
                g.drawImage(animation.getCurrentType(),
                        (int) labelLives.getX() + 125 + 2 * animation.getCurrentType().getWidth() + 3 * 20,
                        (int) labelLives.getY() - 33,
                        animation.getCurrentType().getWidth(), animation.getCurrentType().getHeight(),
                        null);
            case 2:
                g.drawImage(animation.getCurrentType(),
                        (int) labelLives.getX() + 125 + animation.getCurrentType().getWidth() + 2 * 20,
                        (int) labelLives.getY() - 33,
                        animation.getCurrentType().getWidth(), animation.getCurrentType().getHeight(),
                        null);
            case 1:
                g.drawImage(animation.getCurrentType(),
                        (int) labelLives.getX() + 125 + 20,
                        (int) labelLives.getY() - 33,
                        animation.getCurrentType().getWidth(), animation.getCurrentType().getHeight(),
                        null);
            default:
                break;
        }
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map getMap() {
        return map;
    }
}
