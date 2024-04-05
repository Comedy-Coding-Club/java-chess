package chess.domain.piece.type;

import static chess.domain.Direction.LEFT_DOWN;
import static chess.domain.Direction.LEFT_UP;
import static chess.domain.Direction.RIGHT_DOWN;
import static chess.domain.Direction.RIGHT_UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import java.util.Set;

public class Bishop extends SlidingPiece {

    public Bishop(final Color color) {
        super(color);
    }

    @Override
    protected Set<Direction> directions() {
        return Set.of(RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN);
    }

    @Override
    public double getScore() {
        return 3;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.BISHOP;
    }
}
