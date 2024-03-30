package chess.service.domain.piece;

public enum PieceType {
    KING, QUEEN, ROOK, KNIGHT, BISHOP, INITIAL_PAWN, MOVED_PAWN;

    public boolean isPawn() {
        return this == INITIAL_PAWN || this == MOVED_PAWN;
    }
}
