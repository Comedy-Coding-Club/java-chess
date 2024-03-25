package chess.domain.piece;

public enum Color {
    BLACK, WHITE;

    public Color getOpponent() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
