package chess.service.domain.piece.implement;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;
import chess.service.domain.piece.Score;

public class Queen extends Piece {

    private static final Score QUEEN_SCORE = new Score(9);

    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean canMove(Path path) {
        if (path.hasPieceExceptTarget()) {
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
        return QUEEN_SCORE;
    }
}

