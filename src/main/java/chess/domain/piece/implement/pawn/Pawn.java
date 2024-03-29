package chess.domain.piece.implement.pawn;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;

public abstract class Pawn extends Piece {
    private static final Score DEFAULT_SCORE = new Score(1);

    protected Pawn(Color color, PieceType pieceType) {
        super(color, pieceType);
    }

    abstract boolean canMoveForward(Path path);

    @Override
    public boolean canMove(Path path) {
        if (!isForward(path)) {
            return false;
        }
        if (path.hasPieceExceptTarget()) {
            return false;
        }
        if (path.containsDiagonalDirection()) {
            return path.isDistanceOf(1) && path.isEnemyAtTarget();
        }
        return canMoveForward(path) && path.isAllEmpty();
    }

    @Override
    public Score getPieceScore() {
        return DEFAULT_SCORE;
    }
}
