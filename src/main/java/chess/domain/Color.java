package chess.domain;

public enum Color {

    BLACK, WHITE, NONE;

    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
