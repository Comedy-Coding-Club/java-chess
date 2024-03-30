package chess.service.domain.piece.implement;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;
import chess.service.domain.piece.Score;

public class Knight extends Piece {

    private static final Score KNIGHT_SCORE = new Score(2.5);

    public Knight(Color color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean canMove(Path path) {
        return path.isDistanceOf(2)
                && !path.isAllyAtTarget()
                && path.containsDiagonalDirection()
                && path.containsOrthogonalDirection();
    }

    @Override
    public Piece move() {
        return this;
    }

    @Override
    public Score getPieceScore() {
        return KNIGHT_SCORE;
    }
}
