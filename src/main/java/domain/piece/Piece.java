package domain.piece;

import domain.piece.piecerole.*;
import domain.position.*;
import java.util.*;

public class Piece {
    private final PieceRole pieceRole;
    private final Color color;

    public Piece(final PieceRole pieceRole, final Color color) {
        this.pieceRole = pieceRole;
        this.color = color;
    }

    public void validateMovableRoute(
            final Position source,
            final Position target,
            final Map<Position, Piece> chessBoard
    ) {
        pieceRole.validateMovableRoute(source, target, chessBoard);
    }

    public boolean isWhite() {
        return isEqualColor(Color.WHITE);
    }

    public boolean isEqualColor(final Color target) {
        return this.color == target;
    }

    public boolean equalPieceRole(final PieceRole pieceRole) {
        return this.pieceRole.equals(pieceRole);
    }

    public boolean doesGameEndsWhenCaptured() {
        return pieceRole.doesGameEndWhenCaptured();
    }

    public double score(final Position current, final Map<Position, Piece> piecePosition) {
        return pieceRole.score(current, piecePosition);
    }

    public boolean isPawn() {
        return pieceRole instanceof Pawn;
    }

    public Color getColor() {
        return this.color;
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
        return pieceRole.equals(piece.pieceRole) && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceRole, color);
    }
}
