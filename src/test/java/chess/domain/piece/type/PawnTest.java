package chess.domain.piece.type;

import static chess.Fixtures.D2;
import static chess.Fixtures.D3;
import static chess.Fixtures.D4;
import static chess.Fixtures.D5;
import static chess.Fixtures.D6;
import static chess.Fixtures.D7;
import static chess.Fixtures.E4;
import static chess.Fixtures.E5;
import static chess.Fixtures.EMPTY_PIECES;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.File;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PawnTest {

    /*
    ........
    ........
    ........
    ........
    ...*....
    ........
    ...p....
    ........
     */
    @DisplayName("화이트폰은 처음에는 위로 두칸 또는 한칸 이동할 수 있다.")
    @Test
    void getPositions_white_first_time() {
        // given
        final Pawn pawn = new WhitePawn();
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D2, pawn);

        // when
        final Set<Position> positions = pawn.getPositions(new Position(File.D, Rank.TWO), pieces);

        // then
        assertThat(positions).containsExactlyInAnyOrder(D4, D3);
    }

    /*
    ........
    ...p....
    ........
    ...*....
    ........
    ........
    ........
    ........
     */
    @DisplayName("블랙폰은 처음에는 아래로 두칸 또는 한칸 이동할 수 있다.")
    @Test
    void getPositions_black_first_time() {
        // given
        final Pawn pawn = new BlackPawn();
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D7, pawn);

        // when
        final Set<Position> positions = pawn.getPositions(new Position(File.D, Rank.SEVEN), pieces);

        // then
        assertThat(positions).containsExactlyInAnyOrder(D6, D5);
    }

    /*
    ........
    ........
    ........
    ....P...
    ...p....
    ........
    ........
    ........
     */
    @DisplayName("화이트폰은 기물을 잡으러 대각선으로 이동할 수 있다.")
    @Test
    void getPositions_white_catcahble() {
        // given
        final Pawn pawn = new WhitePawn();
        final Pawn enemy = new BlackPawn();
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(D4, pawn);
        pieces.put(E5, enemy);

        // when
        final Set<Position> positions = pawn.getPositions(new Position(File.D, Rank.FOUR), pieces);

        // then
        assertThat(positions).containsExactlyInAnyOrder(D5, E5);
    }

    /*
    ........
    ........
    ........
    ....P...
    ...p....
    ........
    ........
    ........
     */
    @DisplayName("블랙폰은 기물을 잡으러 대각선으로 이동할 수 있다.")
    @Test
    void getPositions_black_catcahble() {
        // given
        final Pawn pawn = new BlackPawn();
        final Pawn enemy = new WhitePawn();
        Map<Position, Piece> pieces = new HashMap<>(EMPTY_PIECES);
        pieces.put(E5, pawn);
        pieces.put(D4, enemy);

        // when
        final Set<Position> positions = pawn.getPositions(new Position(File.E, Rank.FIVE), pieces);

        // then
        assertThat(positions).containsExactlyInAnyOrder(D4, E4);
    }
}
