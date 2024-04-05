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

class QueenTest {

    /*
     .*...*..
     ..*.*....
     ...***...
     ....q....
     ..*****..
     ..*.*....
     .*...*..
     *.....*.
      */
    @DisplayName("퀸의 이동 가능한 위치들을 반환한다.")
    @Test
    void getRouteRightUp2() {
        // given
        final Queen queen = new Queen(Color.WHITE);
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D4, queen);

        // when
        final Set<Position> positions = queen.getPositions(new Position(File.D, Rank.FOUR), pieces);

        // then
        assertThat(positions).contains(D1, D2, D3, D5, D6, D7, D8,
                A4, B4, C4, E4, F4, G4, H4,
                A1, B2, C3, E5, F6, G7, H8, A7, B6, C5, E3, F2, G1);
    }
}
