package domain.game;

import static domain.piece.Color.BLACK;

import domain.piece.Color;
import domain.piece.Piece;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Turn turn = (Turn) o;
        return color == turn.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
