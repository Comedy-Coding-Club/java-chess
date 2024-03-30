package chess.service.domain.piece.implement.pawn;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;

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
