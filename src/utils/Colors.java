package utils;

import java.awt.Color;
import java.util.Random;

public class ColorsPlayer {
    public static Color[] COLORS = {
        // BLUE
        new Color(42, 78, 112),
        new Color(7, 121, 242),

        // PURPLE
        new Color(117, 7, 242),
        // PINK
        new Color(196, 114, 158),

        // GREEN
        new Color(81, 150, 12),
    };

    public static Color RANDOM() {
        // Random random = new Random();
        // int color = random.nextInt(COLORS.length);
        // return COLORS[color];
        return COLORS[new Random().nextInt(COLORS.length)];
    }
}
