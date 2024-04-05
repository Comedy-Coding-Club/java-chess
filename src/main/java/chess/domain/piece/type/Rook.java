package chess.domain.piece.type;

import static chess.domain.Direction.DOWN;
import static chess.domain.Direction.LEFT;
import static chess.domain.Direction.RIGHT;
import static chess.domain.Direction.UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import java.util.Set;

public class Rook extends SlidingPiece {

    public Rook(final Color color) {
        super(color);
    }
    @Override
    protected Set<Direction> directions() {
        return Set.of(UP, DOWN, RIGHT, LEFT);
    }

    @Override
    public double getScore() {
        return 5;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.ROOK;
    }
}
