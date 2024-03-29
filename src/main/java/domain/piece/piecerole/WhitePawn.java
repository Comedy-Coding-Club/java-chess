package domain.piece.piecerole;

import domain.game.*;
import domain.piece.*;
import domain.position.*;
import java.util.*;

public class WhitePawn extends Pawn {
    private WhitePawn(final List<Movable> movables) {
        super(movables);
    }

    public static WhitePawn create() {
        List<Movable> routes = List.of(
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH),
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_EAST),
                new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_WEST)
        );
        return new WhitePawn(routes);
    }

    @Override
    protected List<Movable> generateCurrentMovable(final Position source) {
        if (isStartPosition(source)) {
            return List.of(
                    new Movable(INITIAL_MAX_MOVEMENT, Direction.NORTH),
                    new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_EAST),
                    new Movable(ORIGINAL_MAX_MOVEMENT, Direction.NORTH_WEST)
            );
        }
        return routes;
    }

    @Override
    public boolean isStartPosition(final Position source) {
        return source.isRank2();
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
        if (position.isEqualFile(current) && piece.isPawn() && piece.isEqualColor(Color.WHITE)) {
            return true;
        }
        return false;
    }
}
