package chess.domain;

import java.util.Arrays;

public enum Color {

    BLACK, WHITE, NONE;

    public static Color findByName(String name) {
        return Arrays.stream(values())
                .filter(color -> color.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 Color 가 없습니다."));
    }

    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
