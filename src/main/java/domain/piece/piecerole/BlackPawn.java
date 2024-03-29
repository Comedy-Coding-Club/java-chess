package domain.piece.piecerole;

import domain.game.*;
import domain.piece.*;
import domain.position.*;
import java.util.*;

public class BlackPawn extends Pawn {
    private BlackPawn(final List<Movable> movables) {
        super(movables);
    }

    public static BlackPawn create() {
        List<Movable> routes = List.of(
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.SOUTH),
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.SOUTH_EAST),
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.SOUTH_WEST)
        );
        return new BlackPawn(routes);
    }

    @Override
    protected List<Movable> generateCurrentMovable(final Position source) {
        if (isStartPosition(source)) {
            return List.of(
                    new Movable(INITIAL_MAX_MOVEMENT, Direction.SOUTH),
                    new Movable(ORIGINAL_MAX_MOVEMENT, Direction.SOUTH_WEST),
                    new Movable(ORIGINAL_MAX_MOVEMENT, Direction.SOUTH_EAST)
            );
        }
        return routes;
    }

    @Override
    public boolean isStartPosition(final Position source) {
        return source.isRank7();
    }

    @Override
    protected boolean hasSameColorPawnOnCurrentFile(
            final Position current,
            final Position position,
            final Piece piece
    ) {
        if (position.equals(current)) {
            return false;
        }
        if (position.isEqualFile(current) && piece.isPawn() && piece.isEqualColor(Color.BLACK)) {
            return true;
        }
        return false;
    }
}
