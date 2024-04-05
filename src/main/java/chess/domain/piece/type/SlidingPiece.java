package chess.domain.piece.type;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SlidingPiece extends Piece {
    protected SlidingPiece(final Color color) {
        super(color);
    }

    @Override
    public Set<Position> getPositionsByDirection(final Direction direction, Position sourcePosition,
                                                 final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        while (sourcePosition.canMove(direction) && isObstacleFree(pieces, direction, sourcePosition)) {
            positions.add(sourcePosition.move(direction));
            sourcePosition = sourcePosition.move(direction);
        }

        return positions;
    }
}
