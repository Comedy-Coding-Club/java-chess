package domain.game;

import static domain.piece.Color.BLACK;

import domain.piece.Color;
import domain.piece.Piece;

public class Turn {
    private Color color;

    public Turn(final Color color) {
        this.color = color;
    }

    public void change() {
        this.color = opponent();
    }

    private Color opponent() {
        if (color == BLACK) {
            return Color.WHITE;
        }
        return BLACK;
    }

    public boolean isNotTurn(final Piece sourcePiece) {
        return !color.equals(sourcePiece.getColor());
    }

    public String getName() {
        return color.name();
    }
}
