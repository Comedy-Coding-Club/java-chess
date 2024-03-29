package chess.domain.piece;

import chess.domain.board.Path;
import java.util.Objects;

public abstract class Piece {
    private final Color color;
    private final PieceType pieceType;

    protected Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public abstract boolean canMove(Path path);

    public abstract Piece move();

    public abstract Score getPieceScore();

    public boolean isAlly(Piece other) {
        return this.color == other.color;
    }

    public boolean isColor(Color color) {
        return this.color == color;
    }

    public boolean isTypeOf(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean isPawn() {
        return this.pieceType.isPawn();
    }

    public boolean isForward(Path path) {
        return color.isForward(path);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return color == piece.color && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, pieceType);
    }
}
