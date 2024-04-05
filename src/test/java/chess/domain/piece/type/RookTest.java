package chess.domain.piece.type;

import static chess.Fixtures.A4;
import static chess.Fixtures.B1;
import static chess.Fixtures.B2;
import static chess.Fixtures.B3;
import static chess.Fixtures.B4;
import static chess.Fixtures.B5;
import static chess.Fixtures.B6;
import static chess.Fixtures.B7;
import static chess.Fixtures.B8;
import static chess.Fixtures.C4;
import static chess.Fixtures.D4;
import static chess.Fixtures.E4;
import static chess.Fixtures.EMPTY_PIECES;
import static chess.Fixtures.F4;
import static chess.Fixtures.G4;
import static chess.Fixtures.H4;
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

class RookTest {

    /*
      ....*...
      ....*...
      ....*...
      ....*...
      ****r***
      ....*...
      ....*...
      ....*...
       */
    @DisplayName("룩의 이동 가능한 위치들을 반환한다.")
    @Test
    void getRouteRightUp2() {
        // given
        final Rook rook = new Rook(Color.WHITE);
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(B4, rook);

        // when
        final Set<Position> positions = rook.getPositions(new Position(File.B, Rank.FOUR), pieces);

        // then
        assertThat(positions).contains(B1, B2, B3, B5, B6, B7, B8, A4, C4, D4, E4, F4, G4, H4);
    }
}
