package cz.cuni.mff.java.klima.pacman.enums;

/**
 * The <code>Orientation</code> enum holds data for storing
 * information about moving entities' orientation.
 * It contains two static method to transfer this
 * enum's values to and from integer value respectively.
 */
public enum Orientation {
    RIGHT, LEFT, UP, DOWN;

    /**
     * Gets <code>Orientation</code> value from given integer value.
     *
     * @param n integer value of the <code>Orientation</code>
     * @return <code>Orientation</code> value from given integer value
     */
    public static Orientation fromInt(int n) {
        switch (n) {
            case 0:
                return RIGHT;
            case 1:
                return LEFT;
            case 2:
                return UP;
            case 3:
                return DOWN;
            default:
                return RIGHT;
        }
    }

    /**
     * Gets integer value of given <code>Orientation</code> value.
     *
     * @param orientation <code>Orientation</code> value
     * @return integer value of given <code>Orientation</code> value
     */
    public static int toInt(Orientation orientation) {
        switch (orientation) {
            case RIGHT:
                return 0;
            case LEFT:
                return 1;
            case UP:
                return 2;
            case DOWN:
                return 3;
            default:
                return 0;
        }
    }
}
