package chess.domain.piece.implement;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;

public class Queen extends Piece {

    private static final Score QUEEN_SCORE = new Score(9);

    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean canMove(Path path) {
        if (path.hasPiecePathExceptTarget()) {
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
        return QUEEN_SCORE;
    }
}

