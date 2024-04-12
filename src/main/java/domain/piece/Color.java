package domain.piece;

import java.util.Arrays;

public enum Color {
    BLACK,
    WHITE;

    public static Color of(String color) {
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(color))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 색깔입니다."));
    }

    public Color reverseColor() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
