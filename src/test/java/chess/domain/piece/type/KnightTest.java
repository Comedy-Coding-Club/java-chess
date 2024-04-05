package chess.domain.piece.type;

import static chess.Fixtures.B3;
import static chess.Fixtures.B5;
import static chess.Fixtures.C2;
import static chess.Fixtures.C6;
import static chess.Fixtures.D4;
import static chess.Fixtures.E2;
import static chess.Fixtures.E6;
import static chess.Fixtures.EMPTY_PIECES;
import static chess.Fixtures.F3;
import static chess.Fixtures.F5;
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

class KnightTest {

    /*
    ........
    ........
    ..*.*...
    .*...*..
    ...n....
    .*...*..
    ..*.*...
    ........
     */
    @DisplayName("나이트의 이동 위치들을 반환한다.")
    @Test
    void getRouteRightUp2() {
        // given
        final Knight knight = new Knight(Color.WHITE);
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D4, knight);

        // when
        final Set<Position> positions = knight.getPositions(new Position(File.D, Rank.FOUR), pieces);

        // then
        assertThat(positions).contains(E6, F5, F3, E2, C2, B3, B5, C6);
    }
}
