package chess.domain.strategy.speical;

import chess.domain.position.Direction;
import java.util.List;

public class KingMoveStrategy extends SpecialPieceMoveStrategy{

    private static final int DEFAULT_MAX_MOVE_DISTANCE = 1;

    private static final List<Direction> DIRECTIONS = Direction.straightAndDiagonalDirections();

    public KingMoveStrategy() {
        super(DIRECTIONS, DEFAULT_MAX_MOVE_DISTANCE);
    }
}
