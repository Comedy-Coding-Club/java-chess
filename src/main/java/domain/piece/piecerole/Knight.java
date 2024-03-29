package domain.piece.piecerole;

import static domain.game.Direction.*;

import domain.game.*;
import domain.piece.*;
import domain.position.*;
import java.util.*;

public class Knight extends PieceRole {
    private static final int MAX_MOVEMENT = 1;
    private static final double DOUBLE = 2.5;

    private Knight(final List<Movable> routes) {
        super(routes);
    }

    public static Knight create() {
        List<Movable> routes = List.of(
                new Movable(MAX_MOVEMENT, DOWN_LEFT),
                new Movable(MAX_MOVEMENT, DOWN_RIGHT),
                new Movable(MAX_MOVEMENT, LEFT_DOWN),
                new Movable(MAX_MOVEMENT, LEFT_UP),
                new Movable(MAX_MOVEMENT, RIGHT_DOWN),
                new Movable(MAX_MOVEMENT, RIGHT_UP),
                new Movable(MAX_MOVEMENT, UP_LEFT),
                new Movable(MAX_MOVEMENT, UP_RIGHT)
        );
        return new Knight(routes);
    }

    @Override
    public void validateMovableRoute(
            final Position source,
            final Position target,
            final Map<Position, Piece> chessBoard
    ) {
        validateCorrectRouteForPiece(source, target);
    }

    @Override
    public double score(final Position current, final Map<Position, Piece> piecePosition) {
        return DOUBLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Knight knight = (Knight) o;
        return Objects.equals(routes, knight.routes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routes);
    }
}
