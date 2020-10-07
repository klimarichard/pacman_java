package cz.cuni.mff.java.klima.pacman;

import cz.cuni.mff.java.klima.pacman.graphics.Assets;
import cz.cuni.mff.java.klima.pacman.graphics.Display;
import cz.cuni.mff.java.klima.pacman.states.*;
import cz.cuni.mff.java.klima.pacman.userinput.KeyManager;
import cz.cuni.mff.java.klima.pacman.userinput.MouseManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * The <code>Game</code> class is the main class of
 * the HungryPacMan game responsible for running
 * the game loop (<code>tick</code> and <code>render</code>).
 */
public class Game implements Runnable {

    public State gameState;
    public State menuState;
    public State rulesState;
    public State aboutState;
    private Display display;
    private Thread thread;
    private boolean running = false;
    private KeyManager keyManager;
    private MouseManager mouseManager;
    private Handler handler;
    private String title;
    private int width, height;
    private int score;
    private int highScore;
    private int lives;

    /**
     * Constructor of the main <code>Game</code> class.
     * It takes the game's title, its width and height as
     * parameters. It also initializes the local variables,
     * such as <code>score</code>, <code>lives</code>, etc.
     *
     * @param title  the game title
     * @param width  the game width
     * @param height the game height
     */
    Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        score = 0;
        highScore = 0;
        lives = 3;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    /**
     * Initializes the game at first start.
     */
    private void initialize() {
        // load all images from resource files
        Assets.initialize();

        // create new window
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        handler = new Handler(this);

        // create all states
        gameState = new GameState(handler);
        rulesState = new RulesState(handler);
        aboutState = new AboutState(handler);
        menuState = new MenuState(handler);

        //display Menu state
        State.setState(menuState);
        handler.getMouseManager().setManager(State.getState().getManager());
    }

    /**
     * Resets game data to initial ones.
     */
    void resetGame() {
        score = 0;
        lives = 3;
        gameState = new GameState(handler);
        keyManager.resetKeys();
    }

    /**
     * Calls the <code>tick</code> method of current state.
     */
    private void tick() {
        keyManager.tick();

        if (State.getState() != null) {
            State.getState().tick();
        }

    }

    /**
     * Draws the current game data from the current state
     * to the screen.
     */
    private void render() {
        // using buffer for smoother animations
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        // clear the screen with background colour
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        g.setColor(new Color(0, 0, 139));
        g.fillRect(0, 0, width, height);

        // draw current state
        if (State.getState() != null) {
            State.getState().render(g);
        }

        bs.show();
        g.dispose();
    }

    /**
     * Runs the game loop.
     */
    @Override
    public void run() {
        // initialize the game at first start
        initialize();

        //region Declaration of timer variables
        int fps = 50;                                   // desired fps (frames per second)
        double timePerTick = 1_000_000_000f / fps;      // how much time one tick takes
        double delta = 0;                               // tracking of passed time
        long now;                                       // now and lastTime are used to count passed time
        long lastTime = System.nanoTime();
        //endregion

        while (running) {
            //region This part of code ensures that the game runs at 50 fps
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;
            //endregion

            // whenever more time has passed from the last tick than how much time one tick takes
            if (delta >= 1) {
                // the main game loop
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    /**
     * Starts the thread with the game.
     */
    synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the thread with the game.
     */
    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //region Getters & Setters

    /**
     * Gets the key manager.
     *
     * @return the key manager
     */
    KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * Gets the mouse manager.
     *
     * @return the mouse manager
     */
    MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * Gets the game width.
     *
     * @return the game width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the game height
     *
     * @return the game height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets current score.
     *
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score new score
     */
    public void setScore(int score) {
        this.score = score;
        if (score > highScore) {
            highScore = score;
        }
    }

    /**
     * Gets the current high score.
     *
     * @return current high score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Gets current lives count.
     *
     * @return current lives count
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets current lives count.
     *
     * @param lives new life count
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets the <code>GameState</code>.
     *
     * @return the <code>GameState</code>
     */
    public GameState getGameState() {
        return (GameState) gameState;
    }
    //endregion
}
