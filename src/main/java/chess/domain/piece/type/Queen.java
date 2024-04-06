package chess.domain.piece.type;

import chess.domain.piece.PieceType;
import chess.domain.position.Movement;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class Queen extends Piece {

    private static final double QUEEN_SCORE = 9;

    public Queen(final Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean canMove(final Movement movement) {
        return movement.isDiagonal()
                || movement.isVertical()
                || movement.isHorizontal();
    }

    @Override
    public double getScore() {
        return QUEEN_SCORE;
    }
}
