package chess.domain.piece;

import chess.domain.Direction;
import chess.domain.piece.type.Empty;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece {

    protected final Color color;

    protected Piece(final Color color) {
        this.color = color;
    }

    public abstract double getScore();

    public abstract PieceType getPieceType();

    public abstract boolean isType(PieceType pieceType);

    protected abstract Set<Direction> directions();

    protected abstract Set<Position> getPositionsByDirection(final Direction direction, Position sourcePosition,
                                                             final Map<Position, Piece> pieces);

    public Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces) {
        return directions().stream()
                .flatMap(direction -> getPositionsByDirection(direction, sourcePosition, pieces).stream())
                .collect(Collectors.toSet());
    }

    public double getPawnScore(final boolean hasSameFilePawn) {
        return 0;
    }

    public double getScore(final boolean hasSameFilePawn) {
        if (isType(PieceType.PAWN)) {
            return getPawnScore(hasSameFilePawn);
        }
        return getScore();
    }

    protected boolean isObstacleFree(final Map<Position, Piece> pieces, final Direction direction,
                                     final Position sourcePostition) {
        return isNextEnemy(pieces, direction, sourcePostition) || isNextEmpty(pieces, direction, sourcePostition);
    }

    public boolean isExist() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return getClass().equals(Empty.class);
    }

    public boolean isMyColor(final Color other) {
        return this.color == other;
    }

    public boolean isMyColor(final Piece other) {
        return isMyColor(other.color);
    }

    public boolean isEnemyColor(final Color other) {
        return color.isEnemyColor(other);
    }

    public boolean isBlack() {
        return this.color.equals(Color.BLACK);
    }

    public boolean isWhite() {
        return this.color.equals(Color.WHITE);
    }

    public boolean isClass(final Class<?> other) {
        return getClass().equals(other);
    }

    public boolean isNextEnemy(final Map<Position, Piece> pieces, final Direction direction,
                               final Position currentPosition) {
        return pieces.get(currentPosition.move(direction)).isEnemyColor(color);
    }

    private boolean isNextEmpty(final Map<Position, Piece> pieces, final Direction direction,
                                final Position currentPosition) {
        return !pieces.get(currentPosition.move(direction)).isExist();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + color;
    }
}
