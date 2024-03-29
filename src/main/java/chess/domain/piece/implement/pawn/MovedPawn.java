package chess.domain.piece.implement.pawn;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

public class MovedPawn extends Pawn {

    public MovedPawn(Color color) {
        super(color, PieceType.MOVED_PAWN);
    }

    @Override
    boolean canMoveForward(Path path) {
        return path.isDistanceOf(1);
    }

    @Override
    public Piece move() {
        return this;
    }
}
