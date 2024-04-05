package chess.domain.piece.type;

import static chess.domain.Direction.DOWN_DOWN_LEFT;
import static chess.domain.Direction.DOWN_DOWN_RIGHT;
import static chess.domain.Direction.LEFT_LEFT_DOWN;
import static chess.domain.Direction.LEFT_LEFT_UP;
import static chess.domain.Direction.RIGHT_RIGHT_DOWN;
import static chess.domain.Direction.RIGHT_RIGHT_UP;
import static chess.domain.Direction.UP_UP_LEFT;
import static chess.domain.Direction.UP_UP_RIGHT;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import java.util.Set;

public class Knight extends JumpingPiece {

    public Knight(final Color color) {
        super(color);
    }

    @Override
    protected Set<Direction> directions() {
        return Set.of(RIGHT_RIGHT_DOWN, RIGHT_RIGHT_UP, LEFT_LEFT_UP, LEFT_LEFT_DOWN, UP_UP_RIGHT, UP_UP_LEFT, DOWN_DOWN_RIGHT, DOWN_DOWN_LEFT);
    }

    @Override
    public double getScore() {
        return 2.5;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.KNIGHT;
    }
}
