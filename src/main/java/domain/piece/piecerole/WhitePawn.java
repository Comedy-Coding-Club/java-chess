package domain.piece.piecerole;

import domain.movement.Direction;
import domain.movement.Movable;
import domain.position.Position;
import java.util.List;
import java.util.Objects;

public class WhitePawn extends Pawn {
    private static final int INITIAL_RANK_POSITION = 2;
    private static final int INITIAL_MAX_MOVEMENT = 2;
    private static final int GENERAL_MAX_MOVEMENT = 1;
    private static final List<Movable> INITIAL_ROUTES = List.of(
            new Movable(INITIAL_MAX_MOVEMENT, Direction.N),
            new Movable(GENERAL_MAX_MOVEMENT, Direction.NE),
            new Movable(GENERAL_MAX_MOVEMENT, Direction.NW)
    );
    private static final List<Movable> GENERAL_ROUTES = List.of(
            new Movable(GENERAL_MAX_MOVEMENT, Direction.N),
            new Movable(GENERAL_MAX_MOVEMENT, Direction.NE),
            new Movable(GENERAL_MAX_MOVEMENT, Direction.NW)
    );

    @Override
    public boolean canMove(Position sourcePosition, Position targetPosition) {
        if (sourcePosition.isRankAt(INITIAL_RANK_POSITION)) {
            return INITIAL_ROUTES.stream()
                    .anyMatch(movable -> movable.canMove(sourcePosition, targetPosition));
        }
        return GENERAL_ROUTES.stream()
                .anyMatch(movable -> movable.canMove(sourcePosition, targetPosition));
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean isSlidingPiece() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(GENERAL_ROUTES);
    }
}
