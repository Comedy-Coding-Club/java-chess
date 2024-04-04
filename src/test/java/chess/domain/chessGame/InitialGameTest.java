package chess.domain.chessGame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.location.File;
import chess.domain.location.Location;
import chess.domain.location.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitialGameTest {

    public static final ChessGame INITIAL_GAME = new InitialGame(1);
    public static final Location B1 = new Location(File.B, Rank.ONE);
    public static final Location B2 = new Location(File.B, Rank.TWO);

    @DisplayName("초기 게임은 게임을 시작할 수 있다.")
    @Test
    void startGameTest() {
        ChessGame startGame = INITIAL_GAME.startGame(() -> true);
        assertThat(startGame).isInstanceOf(PlayingGame.class);
    }

    @DisplayName("초기 게임은 게임을 종료할 수 있다.")
    @Test
    void endGameTest() {
        ChessGame endGame = INITIAL_GAME.endGame();
        assertThat(endGame).isInstanceOf(EndGame.class);
    }

    @DisplayName("초기 게임에서 기물을 이동시킬 수 없다.")
    @Test
    void moveGameTest() {
        assertThatThrownBy(() -> INITIAL_GAME.move(B1, B2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임을 먼저 시작해야 합니다.");
    }

    @DisplayName("초기 게임에서 종료된 상태를 확인할 수 있다.")
    @Test
    void checkStateTest() {
        assertThat(INITIAL_GAME.isEnd()).isFalse();
    }

    @DisplayName("초기 게임에서는 보드를 확인할 수 없다.")
    @Test
    void getBoardTest() {
        assertThatThrownBy(INITIAL_GAME::getBoard)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임을 먼저 시작해야 합니다.");
    }

    @DisplayName("초기 보드에서 승자를 확인할 수 없다.")
    @Test
    void notFinishedGameTest() {
        assertThatThrownBy(INITIAL_GAME::getWinner)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임을 먼저 시작해야 합니다.");
    }
}
