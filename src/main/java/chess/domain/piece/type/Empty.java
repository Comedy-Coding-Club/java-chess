package chess.domain.piece.type;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.Map;
import java.util.Set;

public class Empty extends Piece {

    public Empty() {
        super(Color.EMPTY);
    }

    @Override
    protected Set<Direction> directions() {
        return Set.of();
    }

    @Override
    protected Set<Position> getPositionsByDirection(final Direction direction, final Position sourcePosition,
                                                    final Map<Position, Piece> pieces) {
        return Set.of();
    }

    @Override
    public double getScore() {
        return 0;
    }
    @Override
    public PieceType getPieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.EMPTY;
    }
}
