package chess.domain.piece.type;

import static chess.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Color;
import chess.domain.piece.File;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KingTest {

    /*
    ........
    ........
    ........
    ..***...
    ..*k*...
    ..***...
    ........
    ........
     */
    @DisplayName("킹의 이동 위치들을 반환한다.")
    @Test
    void getRouteRightUp2() {
// given
        final King king = new King(Color.WHITE);
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D4, king);

        // when
        final Set<Position> positions = king.getPositions(new Position(File.D, Rank.FOUR), pieces);


        // then
        assertThat(positions).contains(D5, E5, E4, E3, D3, C3, C4, C5);
    }
}
