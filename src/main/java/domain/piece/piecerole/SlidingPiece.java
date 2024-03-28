package domain.piece.piecerole;

import domain.piece.Score;

public abstract class SlidingPiece extends PieceRole {
    protected SlidingPiece(Score score) {
        super(score);
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isSlidingPiece() {
        return true;
    }
}
