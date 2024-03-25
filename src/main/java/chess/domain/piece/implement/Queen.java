package chess.domain.piece.implement;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean canMove(Path path) {
        if (path.hasPiecePathExceptTarget()) {
            return false;
        }
        return path.isNotAllyAtTarget() && path.hasCountOfDistinctDirection(1);
    }

    @Override
    public void move() {
    }
}

