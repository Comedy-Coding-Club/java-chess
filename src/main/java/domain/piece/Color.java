package domain.piece;

import java.util.Arrays;

public enum Color {
    BLACK,
    WHITE;

    public Color reverseColor() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }

    public static Color of(String color) {
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(color))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid color: " + color));
    }
}
