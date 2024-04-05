package chess.domain.piece;

public enum Color {

    BLACK,
    WHITE,
    EMPTY;

    public boolean isEnemyColor(final Color other) {
        if (this == BLACK) {
            return other == WHITE;
        }
        if (this == WHITE) {
            return other == BLACK;
        }
        return false;
    }

    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
