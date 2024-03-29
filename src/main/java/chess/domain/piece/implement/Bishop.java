package chess.domain.piece.implement;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;

public class Bishop extends Piece {
    private static final Score BISHOP_SCORE = new Score(3);

    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public boolean canMove(Path path) {
        if (path.hasPiecePathExceptTarget()) {
            return false;
        }
        if (path.containsOrthogonalDirection()) {
            return false;
        }
        return path.isNotAllyAtTarget() && path.hasCountOfDistinctDirection(1);
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
