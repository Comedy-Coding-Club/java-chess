package chess.service.domain.chessGame;

import static chess.service.domain.location.LocationFixture.A1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.service.domain.board.Board;
import chess.service.domain.location.File;
import chess.service.domain.location.Location;
import chess.service.domain.location.Rank;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.implement.King;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EndGameTest {
    public static final ChessGame END_GAME = new EndGame(1, Board.createEmptyBoard());
    public static final Location B1 = new Location(File.B, Rank.ONE);
    public static final Location B2 = new Location(File.B, Rank.TWO);

    @DisplayName("종료된 게임 시작 테스트")
    @Test
    void startGameTest() {
        ChessGame startedGame = END_GAME.startGame(() -> true);
        assertThat(startedGame)
                .isInstanceOf(PlayingGame.class);
    }

    @DisplayName("종료된 게임 종료 테스트")
    @Test
    void endGameTest() {
        ChessGame endedGame = END_GAME.endGame();
        assertThat(endedGame)
                .isInstanceOf(EndGame.class);
    }

    @DisplayName("이미 종료된 게임에서 기물을 이동시킬 수 없다.")
    @Test
    void moveGameTest() {
        assertThatThrownBy(() -> END_GAME.move(B1, B2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 게임이 종료되었습니다.");
    }

    @DisplayName("종료된 게임에서 종료된 상태를 확인할 수 있다.")
    @Test
    void checkStateTest() {
        assertThat(END_GAME.isEnd()).isTrue();
    }

    @DisplayName("종료된 게임에서는 보드를 확인할 수 있다.")
    @Test
    void getBoardTest() {
        assertThatNoException()
                .isThrownBy(END_GAME::getBoard);
    }

    @DisplayName("종료된 게임에서 승자를 확인할 수 있다.")
    @Nested
    class getWinnerTest {
        @DisplayName("게임이 마무리 되지 않은 경우 승자를 확인할 수 없다.")
        @Test
        void notFinishedGameTest() {
            assertThatThrownBy(END_GAME::getWinner)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("승부가 나지 않았습니다.");
        }

        @DisplayName("흑의 승리를 확인할 수 있다.")
        @Test
        void blackWinTest() {
            EndGame blackWinGame = new EndGame(1, new Board(Map.of(
                    A1, new King(Color.BLACK)
            )));

            assertThat(blackWinGame.getWinner()).isEqualTo(Color.BLACK);
        }

        @DisplayName("백의 승리를 확인할 수 있다.")
        @Test
        void whiteWinTest() {
            EndGame blackWinGame = new EndGame(1, new Board(Map.of(
                    A1, new King(Color.WHITE)
            )));

            assertThat(blackWinGame.getWinner()).isEqualTo(Color.WHITE);
        }
    }
}
