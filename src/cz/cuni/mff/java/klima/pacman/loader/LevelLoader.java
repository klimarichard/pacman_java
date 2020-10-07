package cz.cuni.mff.java.klima.pacman.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The <code>LevelLoader</code> class controls loading of the level map
 * from resource map file. It contains static methods only.
 */
public class LevelLoader {

    /**
     * Loads the level map from resource file to a single string.
     *
     * @param path path to resource map file
     * @return single string containing the loaded map
     */
    public static String loadMapToString(String path) {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * Converts a character to a corresponding integer value
     * used in the game.
     *
     * @param c a character
     * @return corresponding integer value
     */
    public static int charToInt(char c) {
        int i = "XFBCSPVRYQ".indexOf(c);

        // if unknown char is examined, return EMPTY value
        if (i == -1)
            return 31;
        else
            return i;
    }

    /**
     * Gets an integer value from a string containing a number.
     * If the string cannot be converted to a number (it contains
     * other characters than numbers and unary minus) it returns
     * <code>-1</code>.
     *
     * @param number a string containing a number
     * @return integer value of the String
     */
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
