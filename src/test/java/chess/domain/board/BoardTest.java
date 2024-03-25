package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private static final Board BOARD = new Board();
    public static final Location B2 = new Location(Column.B, Row.TWO);
    public static final Location B3 = new Location(Column.B, Row.THREE);

    public static final Location B7 = new Location(Column.B, Row.SEVEN);
    public static final Location B6 = new Location(Column.B, Row.SIX);

    @DisplayName("흑은 백의 기물을 움직일 수 없다.")
    @Test
    void opponentBlackPieceMoveTest() {
        assertThatThrownBy(() -> BOARD.move(B2, B3, Color.BLACK))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 기물만 움직일 수 있습니다.");
    }

    @DisplayName("백은 흑의 기물을 움직일 수 없다.")
    @Test
    void opponentWhitePieceMoveTest() {
        assertThatThrownBy(() -> BOARD.move(B7, B6, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 기물만 움직일 수 있습니다.");
    }
}
