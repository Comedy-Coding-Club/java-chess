package chess.service.domain.piece.implement;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;
import chess.service.domain.piece.Score;

public class Bishop extends Piece {
    private static final Score BISHOP_SCORE = new Score(3);

    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public boolean canMove(Path path) {
        if (path.hasPieceExceptTarget()) {
            return false;
        }
        if (path.containsOrthogonalDirection()) {
            return false;
        }
        return !path.isAllyAtTarget()
                && path.hasCountOfDistinctDirection(1);
    }

    @Override
    public Piece move() {
        return this;
    }

    @Override
    public Score getPieceScore() {
        return BISHOP_SCORE;
    }
}
