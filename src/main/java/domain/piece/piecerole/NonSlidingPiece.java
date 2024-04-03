package domain.piece.piecerole;

import domain.score.Score;

public abstract class NonSlidingPiece extends PieceRole {

    protected NonSlidingPiece(PieceType pieceType, Score score) {
        super(pieceType, score);
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
        return false;
    }
}
