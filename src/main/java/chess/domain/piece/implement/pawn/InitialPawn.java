package chess.domain.piece.implement.pawn;

import chess.domain.board.Path;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

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
