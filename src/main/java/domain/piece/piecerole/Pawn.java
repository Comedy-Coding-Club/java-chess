package domain.piece.piecerole;

import domain.piece.Score;

public abstract class Pawn extends PieceRole {
    public static final Score SCORE = new Score(1.0);

    protected Pawn() {
        super(SCORE);
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isSlidingPiece() {
        return false;
    }
}
