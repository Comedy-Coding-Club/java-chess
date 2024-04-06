package chess.domain.piece.type;

import chess.domain.piece.PieceType;
import chess.domain.position.Movement;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public abstract class Pawn extends Piece {

    protected static final int DEFAULT_STEP = 1;
    protected static final int INIT_AVAILABLE_STEP = 2;
    protected static final double PAWN_DEFAULT_SCORE = 1;

    protected Pawn(final Color color) {
        super(color, PieceType.PAWN);
    }

    public abstract boolean canCatch(final Movement movement);

    @Override
    public double getScore() {
        return PAWN_DEFAULT_SCORE;
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean isKing() { return false; }
}
