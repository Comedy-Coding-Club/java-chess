package chess.domain.piece.implement;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;

public class King extends Piece {
    private static final Score KING_SCORE = Score.ZERO;

    private static final int MAX_MOVE_DISTANCE = 1;

    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean canMove(Path path) {
        return path.isDistanceOf(MAX_MOVE_DISTANCE)
                && !path.isAllyAtTarget();
    }

    @Override
    public Piece move() {
        return this;
    }

    @Override
    public Score getPieceScore() {
        return KING_SCORE;
    }
}
