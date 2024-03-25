package domain.piece.piecerole;

import static domain.move.Direction.E;
import static domain.move.Direction.N;
import static domain.move.Direction.NE;
import static domain.move.Direction.NW;
import static domain.move.Direction.S;
import static domain.move.Direction.SE;
import static domain.move.Direction.SW;
import static domain.move.Direction.W;

import domain.move.Movable;
import domain.position.Position;
import java.util.List;
import java.util.Objects;

public class Queen implements PieceRole {
    private static final int MAX_MOVEMENT = 7;

    private final List<Movable> routes;

    public Queen() {
        routes = List.of(
                new Movable(MAX_MOVEMENT, N),
                new Movable(MAX_MOVEMENT, E),
                new Movable(MAX_MOVEMENT, S),
                new Movable(MAX_MOVEMENT, W),
                new Movable(MAX_MOVEMENT, NE),
                new Movable(MAX_MOVEMENT, NW),
                new Movable(MAX_MOVEMENT, SE),
                new Movable(MAX_MOVEMENT, SW)

        );
    }

    @Override
    public boolean canMove(final Position sourcePosition, final Position targetPosition) {
        return routes.stream()
                .anyMatch(movable -> movable.canMove(sourcePosition, targetPosition));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Queen queen = (Queen) o;
        return Objects.equals(routes, queen.routes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routes);
    }
}
