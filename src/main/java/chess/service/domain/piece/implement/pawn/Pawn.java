package chess.service.domain.piece.implement.pawn;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;
import chess.service.domain.piece.Score;

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
