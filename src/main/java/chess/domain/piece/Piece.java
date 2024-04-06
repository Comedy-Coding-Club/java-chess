package chess.domain.piece;

import chess.domain.position.Direction;
import chess.domain.position.Movement;
import chess.domain.position.Position;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Color color;

    protected Piece(final Color color, final PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public Set<Position> getRoute(final Movement movement) {
        Position position = movement.getLowerPosition();

        final Direction direction = Direction.from(movement);
        if (direction.equals(Direction.HORIZONTAL)) {
            position = movement.getLefterPosition();
        }

        final Set<Position> positions = new HashSet<>();
        for (int i = 1; i < distance(movement); i++) {
            position = position.move(direction.getDx(), direction.getDy());
            positions.add(position);
        }
        return positions;
    }

    public boolean isOpponent(final Piece otherPiece) {
        return !color.equals(otherPiece.color);
    }

    public boolean isColor(final Color otherColor) {
        return color.equals(otherColor);
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    private int distance(final Movement movement) {
        return Math.max(movement.getRankDistance(), movement.getFileDistance());
    }

    public abstract boolean isPawn();

    public abstract boolean isKing();

    public abstract boolean canMove(final Movement movement);

    public abstract double getScore();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
