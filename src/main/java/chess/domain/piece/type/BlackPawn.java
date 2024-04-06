package chess.domain.piece.type;

import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import chess.domain.position.Movement;
import chess.domain.position.Rank;

public class BlackPawn extends Pawn{

    private static final Rank INIT_BLACK_RANK = Rank.SEVEN;

    public BlackPawn() {
        super(Color.BLACK);
    }

    @Override
    public boolean canCatch(final Movement movement) {
        return movement.isDiagonal() && !movement.isUp() && movement.getRankDistance() == Pawn.DEFAULT_STEP;
    }

    @Override
    public boolean canMove(final Movement movement) {
        if (isInitPosition(movement)) {
            return movement.isVertical() && !movement.isUp() && movement.getRankDistance() == INIT_AVAILABLE_STEP
                    || movement.isVertical() && !movement.isUp() && movement.getRankDistance() == DEFAULT_STEP;
        }

        return movement.isVertical() && !movement.isUp() && movement.getRankDistance() == DEFAULT_STEP;
    }

    private boolean isInitPosition(final Movement movement) {
        return movement.isCurrentRank(INIT_BLACK_RANK);
    }
}
