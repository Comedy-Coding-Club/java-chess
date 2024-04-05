package chess.domain.piece.type;

import static chess.domain.Direction.LEFT_UP;
import static chess.domain.Direction.RIGHT_UP;
import static chess.domain.Direction.UP;
import static chess.domain.Direction.UP_UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WhitePawn extends Pawn {
    public WhitePawn() {
        super(Color.WHITE);
    }

    @Override
    public Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        if (sourcePosition.canMove(RIGHT_UP) && isNextEnemy(pieces, RIGHT_UP, sourcePosition)) {
            positions.add(sourcePosition.move(RIGHT_UP));
        }
        if (sourcePosition.canMove(LEFT_UP) && isNextEnemy(pieces, LEFT_UP, sourcePosition)) {
            positions.add(sourcePosition.move(LEFT_UP));
        }
        if (sourcePosition.canMove(UP_UP) && sourcePosition.isSameRank(Rank.TWO)) {
            positions.add(sourcePosition.move(UP_UP));
        }
        if (sourcePosition.canMove(UP)) {
            positions.add(sourcePosition.move(Direction.UP));
        }
        return positions;
    }
}
