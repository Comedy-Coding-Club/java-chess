package domain.piece.piecerole;

import static domain.game.Direction.*;

import domain.game.*;
import domain.piece.*;
import domain.position.*;
import java.util.*;

public class King extends PieceRole {
    private static final int MAX_MOVEMENT = 1;
    private static final double SCORE = 0;

    private King(final List<Movable> routes) {
        super(routes);
    }

    public static King create() {
        List<Movable> routes = List.of(
                new Movable(MAX_MOVEMENT, NORTH),
                new Movable(MAX_MOVEMENT, EAST),
                new Movable(MAX_MOVEMENT, SOUTH),
                new Movable(MAX_MOVEMENT, WEST)
        );
        return new King(routes);
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
    public boolean doesGameEndWhenCaptured() {
        return true;
    }

    @Override
    public double score(final Position current, final Map<Position, Piece> piecePosition) {
        return SCORE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        King king = (King) o;
        return Objects.equals(routes, king.routes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routes);
    }
}
