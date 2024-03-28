package domain.piece.piecerole;

public abstract class Pawn implements PieceRole {
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
