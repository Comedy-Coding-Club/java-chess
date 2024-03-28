package domain.piece.piecerole;

import domain.piece.Score;
import domain.position.Position;

public abstract class PieceRole {
    private final Score score;

    protected PieceRole(Score score) {
        this.score = score;
    }

    public abstract boolean canMove(Position sourcePosition, Position targetPosition);

    public abstract boolean isPawn();

    public abstract boolean isKing();

    public abstract boolean isSlidingPiece();

    public Score getScore() {
        return score;
    }
}
