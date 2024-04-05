package chess.domain.piece.type;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class JumpingPiece extends Piece {
    protected JumpingPiece(final Color color) {
        super(color);
    }

    @Override
    public Set<Position> getPositionsByDirection(final Direction direction, Position sourcePosition,
                                                  final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        if (sourcePosition.canMove(direction) && isObstacleFree(pieces, direction, sourcePosition)) {
            positions.add(sourcePosition.move(direction));
        }

        return positions;
    }
}
