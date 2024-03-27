package domain.piece;

public enum Color {
    BLACK,
    WHITE;

    public Color reverseColor() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
