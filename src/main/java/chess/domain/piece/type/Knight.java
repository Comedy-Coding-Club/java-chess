package chess.domain.piece.type;

import chess.domain.piece.PieceType;
import chess.domain.position.Movement;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Set;

public class Knight extends Piece {

    private static final int DEFAULT_STEP_ONE = 1;
    private static final int DEFAULT_STEP_TWO = 2;
    private static final double KNIGHT_SCORE = 2.5;

    public Knight(final Color color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean canMove(final Movement movement) {
        return movement.getRankDistance() == DEFAULT_STEP_TWO && movement.getFileDistance() == DEFAULT_STEP_ONE
                || movement.getRankDistance() == DEFAULT_STEP_ONE && movement.getFileDistance() == DEFAULT_STEP_TWO;
    }

    @Override
    public double getScore() {
        return KNIGHT_SCORE;
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
        return false;
    }
}
