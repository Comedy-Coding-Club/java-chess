package chess.domain.piece.type;

import chess.domain.piece.PieceType;
import chess.domain.position.Movement;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class Rook extends Piece {

    private static final double ROOK_SCORE = 5;

    public Rook(final Color color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public boolean canMove(final Movement movement) {
        return movement.isVertical() || movement.isHorizontal();
    }

    @Override
    public double getScore() {
        return ROOK_SCORE;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
