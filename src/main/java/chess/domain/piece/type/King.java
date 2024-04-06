package chess.domain.piece.type;

import chess.domain.piece.PieceType;
import chess.domain.position.Movement;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Set;

public class King extends Piece {

    private static final int DEFAULT_STEP = 1;
    private static final double KING_SCORE = 0;

    public King(final Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean canMove(final Movement movement) {
        return movement.isVertical() && movement.getRankDistance() == DEFAULT_STEP
                || movement.isHorizontal() && movement.getFileDistance() == DEFAULT_STEP
                || movement.isDiagonal() && movement.getRankDistance() == DEFAULT_STEP;
    }

    @Override
    public double getScore() {
        return KING_SCORE;
    }

    @Override
    public Set<Position> getRoute(final Movement movement) {
        return Set.of();
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return true;
    }
}
