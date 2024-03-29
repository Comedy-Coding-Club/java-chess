package domain.piece.piecerole;

import static domain.game.Direction.*;

import domain.game.*;
import domain.piece.*;
import domain.position.*;
import java.util.*;

public class Bishop extends PieceRole {
    private static final int MAX_MOVEMENT = 7;
    private static final double SCORE = 3;

    private Bishop(final List<Movable> routes) {
        super(routes);
    }

    public static Bishop create() {
        List<Movable> routes = List.of(
                new Movable(MAX_MOVEMENT, NORTH_WEST),
                new Movable(MAX_MOVEMENT, NORTH_EAST),
                new Movable(MAX_MOVEMENT, SOUTH_EAST),
                new Movable(MAX_MOVEMENT, SOUTH_WEST)
        );
        return new Bishop(routes);
    }

    @Override
    public void validateMovableRoute(final Position source, final Position target,
                                     final Map<Position, Piece> chessBoard) {
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bishop bishop = (Bishop) o;
        return Objects.equals(routes, bishop.routes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routes);
    }
}
