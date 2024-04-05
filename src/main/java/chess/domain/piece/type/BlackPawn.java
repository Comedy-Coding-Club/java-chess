package chess.domain.piece.type;

import static chess.domain.Direction.DOWN;
import static chess.domain.Direction.DOWN_DOWN;
import static chess.domain.Direction.LEFT_DOWN;
import static chess.domain.Direction.RIGHT_DOWN;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlackPawn extends Pawn {
    public BlackPawn() {
        super(Color.BLACK);
    }

    @Override
    public Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        if (sourcePosition.canMove(RIGHT_DOWN) && isNextEnemy(pieces, RIGHT_DOWN, sourcePosition)) {
            positions.add(sourcePosition.move(RIGHT_DOWN));
        }
        if (sourcePosition.canMove(LEFT_DOWN) && isNextEnemy(pieces, LEFT_DOWN, sourcePosition)) {
            positions.add(sourcePosition.move(LEFT_DOWN));
        }
        if (sourcePosition.canMove(DOWN_DOWN) && sourcePosition.isSameRank(Rank.SEVEN)) {
            positions.add(sourcePosition.move(DOWN_DOWN));
        }
        if (sourcePosition.canMove(DOWN)) {
            positions.add(sourcePosition.move(DOWN));
        }
        return positions;
    }
}
