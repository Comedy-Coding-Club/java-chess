package chess.service.domain.piece.implement;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;
import chess.service.domain.piece.Score;

public class Rook extends Piece {

    private static final Score ROOK_SCORE = new Score(5);

    public Rook(Color color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public boolean canMove(Path path) {
        if (path.hasPieceExceptTarget()) {
            return false;
        }
        if (path.containsDiagonalDirection()) {
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
        return ROOK_SCORE;
    }
}
