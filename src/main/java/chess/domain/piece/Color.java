package chess.domain.piece;

import java.util.Arrays;

public enum Color {

    BLACK, WHITE;

    public static Color from(final String color) {
        return Arrays.stream(values()).filter(value -> value.name().equals(color))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 색입니다."));
    }

    public Color getOtherColor() {
        if (this.equals(BLACK)) {
            return WHITE;
        }

        return BLACK;
    }
}
