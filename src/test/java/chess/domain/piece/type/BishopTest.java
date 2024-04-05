package chess.domain.piece.type;

import static chess.Fixtures.A1;
import static chess.Fixtures.A7;
import static chess.Fixtures.B2;
import static chess.Fixtures.B6;
import static chess.Fixtures.C3;
import static chess.Fixtures.C5;
import static chess.Fixtures.D4;
import static chess.Fixtures.E3;
import static chess.Fixtures.E5;
import static chess.Fixtures.EMPTY_PIECES;
import static chess.Fixtures.F2;
import static chess.Fixtures.F6;
import static chess.Fixtures.G1;
import static chess.Fixtures.G7;
import static chess.Fixtures.H8;
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

class BishopTest {

    /*
    .......*
    *.....*.
    .*...*..
    ..*.*...
    ...b....
    ..*.*...
    .*...*..
    *.....*.
     */
    @DisplayName("비숍의 이동 가능한 위치들을 반환한다.")
    @Test
    void getPositions() {
        // given
        final Bishop bishop = new Bishop(Color.WHITE);
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D4, bishop);

        // when
        final Set<Position> positions = bishop.getPositions(new Position(File.D, Rank.FOUR), pieces);

        // then
        assertThat(positions).contains(E5, F6, E3, G1, C3, C5, H8, F2, A1, A7, G7, B2, B6);
    }
}
