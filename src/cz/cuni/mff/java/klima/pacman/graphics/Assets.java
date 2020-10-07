package cz.cuni.mff.java.klima.pacman.graphics;

import cz.cuni.mff.java.klima.pacman.loader.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * The <code>Assets</code> class holds all image data for the game.
 * Every image displayed on the screen at one point in time
 * or another is called from this class.
 * All its methods and fields are static.
 */
public class Assets {

    private static final int SIZE = 40;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 75;

    public static BufferedImage title, about, rules_controls, game_over, win;
    public static BufferedImage pacMan;
    public static BufferedImage icon;
    public static BufferedImage[] playButton, menuButton, aboutButton, rulesButton;
    public static BufferedImage[] pacManRight, pacManDown, pacManLeft, pacManUp;
    public static BufferedImage enemyBlue, enemyPink, enemyPurple, enemyRed, enemyYellow, enemyDead;
    public static BufferedImage foodNormal, foodKiller, foodBanana, foodCherry, foodStrawberry;
    public static BufferedImage empty, wall;

    /**
     * Loads all images to be displayed in game from resource files.
     */
    public static void initialize() {
        title = ImageLoader.loadImage("/images/title.png");
        icon = ImageLoader.loadImage("/images/pacman.png");
        about = ImageLoader.loadImage("/images/about_game.png");
        rules_controls = ImageLoader.loadImage("/images/rules_controls.png");
        game_over = ImageLoader.loadImage("/images/game_over.png");
        win = ImageLoader.loadImage("/images/win.png");

        // all buttons are in one resource file
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/images/buttons.png"));

        aboutButton = new BufferedImage[2];
        aboutButton[0] = sheet.crop(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        aboutButton[1] = sheet.crop(BUTTON_WIDTH, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

        menuButton = new BufferedImage[2];
        menuButton[0] = sheet.crop(0, BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        menuButton[1] = sheet.crop(BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

        playButton = new BufferedImage[2];
        playButton[0] = sheet.crop(0, 2 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        playButton[1] = sheet.crop(BUTTON_WIDTH, 2 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

        rulesButton = new BufferedImage[2];
        rulesButton[0] = sheet.crop(0, 3 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        rulesButton[1] = sheet.crop(BUTTON_WIDTH, 3 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

        // all game textures are in one resource file
        sheet = new SpriteSheet(ImageLoader.loadImage("/images/textures.png"));

        enemyBlue = sheet.crop(0, 0, SIZE, SIZE);
        enemyDead = sheet.crop(SIZE, 0, SIZE, SIZE);
        enemyPink = sheet.crop(2 * SIZE, 0, SIZE, SIZE);
        enemyPurple = sheet.crop(3 * SIZE, 0, SIZE, SIZE);
        enemyRed = sheet.crop(4 * SIZE, 0, SIZE, SIZE);
        enemyYellow = sheet.crop(0, SIZE, SIZE, SIZE);

        foodBanana = sheet.crop(SIZE, SIZE, SIZE, SIZE);
        foodCherry = sheet.crop(2 * SIZE, SIZE, SIZE, SIZE);
        foodKiller = sheet.crop(3 * SIZE, SIZE, SIZE, SIZE);
        foodNormal = sheet.crop(4 * SIZE, SIZE, SIZE, SIZE);
        foodStrawberry = sheet.crop(0, 2 * SIZE, SIZE, SIZE);

        pacMan = sheet.crop(SIZE, 2 * SIZE, SIZE, SIZE);

        pacManDown = new BufferedImage[6];
        pacManDown[0] = pacMan;
        pacManDown[1] = sheet.crop(2 * SIZE, 2 * SIZE, SIZE, SIZE);
        pacManDown[2] = sheet.crop(3 * SIZE, 2 * SIZE, SIZE, SIZE);
        pacManDown[3] = sheet.crop(4 * SIZE, 2 * SIZE, SIZE, SIZE);
        pacManDown[4] = pacManDown[2];
        pacManDown[5] = pacManDown[1];

        pacManLeft = new BufferedImage[6];
        pacManLeft[0] = pacMan;
        pacManLeft[1] = sheet.crop(0, 3 * SIZE, SIZE, SIZE);
        pacManLeft[2] = sheet.crop(SIZE, 3 * SIZE, SIZE, SIZE);
        pacManLeft[3] = sheet.crop(2 * SIZE, 3 * SIZE, SIZE, SIZE);
        pacManLeft[4] = pacManLeft[2];
        pacManLeft[5] = pacManLeft[1];

        pacManRight = new BufferedImage[6];
        pacManRight[0] = pacMan;
        pacManRight[1] = sheet.crop(3 * SIZE, 3 * SIZE, SIZE, SIZE);
        pacManRight[2] = sheet.crop(4 * SIZE, 3 * SIZE, SIZE, SIZE);
        pacManRight[3] = sheet.crop(0, 4 * SIZE, SIZE, SIZE);
        pacManRight[4] = pacManRight[2];
        pacManRight[5] = pacManRight[1];

        pacManUp = new BufferedImage[6];
        pacManUp[0] = pacMan;
        pacManUp[1] = sheet.crop(SIZE, 4 * SIZE, SIZE, SIZE);
        pacManUp[2] = sheet.crop(2 * SIZE, 4 * SIZE, SIZE, SIZE);
        pacManUp[3] = sheet.crop(3 * SIZE, 4 * SIZE, SIZE, SIZE);
        pacManUp[4] = pacManUp[2];
        pacManUp[5] = pacManUp[1];

        wall = sheet.crop(4 * SIZE, 4 * SIZE, SIZE, SIZE);
        empty = new BufferedImage(40, 40, BufferedImage.TYPE_4BYTE_ABGR);

    }
}

/**
 * The <code>SpriteSheet</code> inner class provides methods for slicing
 * an image.
 * It is used to take parts from large sprite sheets and return
 * those parts as individual images.
 */
class SpriteSheet {
    private BufferedImage sheet;

    /**
     * Constructor of the <code>SpriteSheet</code> inner class. It takes
     * the sprite sheet to be sliced as parameter.
     *
     * @param sheet an image containing the sprite sheet
     *              to be sliced
     */
    SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    /**
     * Returns an image sliced from this inner class'
     * sheet image.
     *
     * @param x      x-coordinate of upper left corner of
     *               the sliced part
     * @param y      y-coordinate of upper left corner of
     *               the sliced part
     * @param width  width of the sliced part
     * @param height height of the sliced part
     * @return an image sliced from this class' sheet image
     */
    BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
