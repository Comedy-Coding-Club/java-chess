package chess.domain.piece.type;

import static chess.domain.Direction.DOWN;
import static chess.domain.Direction.LEFT;
import static chess.domain.Direction.LEFT_DOWN;
import static chess.domain.Direction.LEFT_UP;
import static chess.domain.Direction.RIGHT;
import static chess.domain.Direction.RIGHT_DOWN;
import static chess.domain.Direction.RIGHT_UP;
import static chess.domain.Direction.UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import java.util.Set;

public class King extends JumpingPiece {

    public King(final Color color) {
        super(color);
    }

    @Override
    protected Set<Direction> directions() {
        return Set.of(RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN, UP, DOWN, RIGHT, LEFT);
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.KING;
    }
}
