package domain.piece;

import domain.ChessVector;
import domain.Square;
import domain.Team;

import java.util.List;
import java.util.Objects;

public class Knight extends Piece {
    private static final List<ChessVector> SQUARE_VECTORS = List.of(
            new ChessVector(1, 2), new ChessVector(1, -2), new ChessVector(-1, 2), new ChessVector(-1, -2),
            new ChessVector(2, 1), new ChessVector(2, -1), new ChessVector(-2, 1), new ChessVector(-2, -1));

    public Knight(final Team color) {
        super(color);
    }

    @Override
    public boolean canMove(final Square source, final Square target) {
        final ChessVector chessVector = target.calculateVector(source);

        // TODO: contains 로 변경
        return SQUARE_VECTORS.stream()
                .anyMatch(vector -> vector.equals(chessVector));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Knight piece)) {
            return false;
        }
        return this.team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, Knight.class);
    }
}
