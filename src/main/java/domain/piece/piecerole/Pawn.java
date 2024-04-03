package domain.piece.piecerole;

import domain.score.Score;

public abstract class Pawn extends PieceRole {
    private static final Score SCORE = new Score(1.0);

    protected Pawn(PieceType pieceType) {
        super(pieceType, SCORE);
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
