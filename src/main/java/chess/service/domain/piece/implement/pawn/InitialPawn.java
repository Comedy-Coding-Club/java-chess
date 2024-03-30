package chess.service.domain.piece.implement.pawn;

import chess.service.domain.board.Path;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.PieceType;

public class InitialPawn extends Pawn {

    public InitialPawn(Color color) {
        super(color, PieceType.INITIAL_PAWN);
    }

    @Override
    boolean canMoveForward(Path path) {
        return path.isDistanceOf(1) || path.isDistanceOf(2);
    }


    @Override
    public Piece move() {
        return new MovedPawn(getColor());
    }
}
