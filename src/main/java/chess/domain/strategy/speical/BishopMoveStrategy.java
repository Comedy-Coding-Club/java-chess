package chess.domain.strategy.speical;

import chess.domain.position.Direction;
import java.util.List;

public class BishopMoveStrategy extends SpecialPieceMoveStrategy{

    private static final int DEFAULT_MAX_MOVE_DISTANCE = 7;

    private static final List<Direction> DIRECTIONS = Direction.diagonalDirections();

    public BishopMoveStrategy() {
        super(DIRECTIONS, DEFAULT_MAX_MOVE_DISTANCE);
    }
}
