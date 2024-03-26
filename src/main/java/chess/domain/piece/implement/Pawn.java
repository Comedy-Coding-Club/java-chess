package chess.domain.piece.implement;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;

public abstract class Pawn extends Piece {
    private static final Score DEFAULT_SCORE = new Score(1);
    private static final Score SAME_LINE_SCORE = new Score(0.5);

    private boolean moved;

    protected Pawn(Color color) {
        super(color, PieceType.PAWN);
        this.moved = false;
    }

    abstract protected boolean isBackward(Path path);

    @Override
    public boolean canMove(Path path) {
        if (isNotGeneralMove(path)) {
            return false;
        }
        if (path.containsDiagonalDirection()) {
            return path.isDistanceOf(1) && path.isEnemyAtTarget();
        }
        if (path.isDistanceOf(2)) {
            return path.isAllEmpty() && isFirstMove();
        }
        return path.isDistanceOf(1) && path.isAllEmpty();
    }

    private boolean isNotGeneralMove(Path path) {
        if (!path.hasCountOfDistinctDirection(1)) {
            return true;
        }
        return isBackward(path);
    }

    private boolean isFirstMove() {
        return !moved;
    }

    @Override
    public void move() {
        moved = true;
    }

    @Override
    public Score getPieceScore() {
        return DEFAULT_SCORE;
    }
}
