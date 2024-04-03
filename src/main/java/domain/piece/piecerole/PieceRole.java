package domain.piece.piecerole;

import domain.score.Score;
import domain.position.Position;

public abstract class PieceRole {
    private final PieceType pieceType;
    private final Score score;

    protected PieceRole(PieceType pieceType, Score score) {
        this.pieceType = pieceType;
        this.score = score;
    }

    public abstract boolean canMove(Position sourcePosition, Position targetPosition);

    public abstract boolean isPawn();

    public abstract boolean isKing();

    public abstract boolean isSlidingPiece();

    public Score getScore() {
        return score;
    }

    public String getPieceType() {
        return pieceType.name();
    }
}
