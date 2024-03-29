package domain.piece.piecerole;

import static domain.game.Direction.*;

import domain.game.*;
import domain.piece.*;
import domain.position.*;
import java.util.*;

public class Rook extends PieceRole {
    private static final int MAX_MOVEMENT = 7;
    private static final double SCORE = 5;

    private Rook(final List<Movable> routes) {
        super(routes);
    }

    public static Rook create() {
        List<Movable> routes = List.of(
                new Movable(MAX_MOVEMENT, NORTH),
                new Movable(MAX_MOVEMENT, EAST),
                new Movable(MAX_MOVEMENT, SOUTH),
                new Movable(MAX_MOVEMENT, WEST)
        );
        return new Rook(routes);
    }

    @Override
    public void validateMovableRoute(
            final Position source,
            final Position target,
            final Map<Position, Piece> chessBoard
    ) {
        validateCorrectRouteForPiece(source, target);
        validateBlockedRoute(source, target, chessBoard);
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
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(routes);
    }
}
