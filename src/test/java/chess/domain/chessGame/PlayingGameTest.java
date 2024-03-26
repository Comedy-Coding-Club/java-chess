package chess.domain.chessGame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import chess.domain.board.Board;
import chess.domain.location.File;
import chess.domain.location.Location;
import chess.domain.location.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.implement.King;
import chess.domain.piece.implement.Rook;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayingGameTest {


    public static final ChessGame PLAYING_GAME = new PlayingGame();
    public static final Location B2 = new Location(File.B, Rank.TWO);
    public static final Location B3 = new Location(File.B, Rank.THREE);
    public static final Location B6 = new Location(File.B, Rank.SIX);
    public static final Location B7 = new Location(File.B, Rank.SEVEN);

    @DisplayName("진행중인 게임은 게임을 시작할 수 있다.")
    @Test
    void startGameTest() {
        ChessGame restartGame = PLAYING_GAME.startGame(() -> true);
        assertThat(restartGame).isNotEqualTo(PLAYING_GAME);
    }

    @DisplayName("진행중인 게임에서 재시작을 요청후 재시작 하지 않으면 이전 게임이 유지된다.")
    @Test
    void restartGameTest() {
        ChessGame notRestartGame = PLAYING_GAME.startGame(() -> false);
        assertThat(notRestartGame).isEqualTo(PLAYING_GAME);
    }

    @DisplayName("진행중인 게임은 게임을 종료할 수 있다.")
    @Test
    void endGameTest() {
        ChessGame endGame = PLAYING_GAME.endGame();
        assertThat(endGame).isInstanceOf(EndGame.class);
    }

    @DisplayName("진행중인 게임에서 기물을 이동시킬 수 있다.")
    @Test
    void moveGameTest() {
        assertThatNoException()
                .isThrownBy(() -> PLAYING_GAME.move(B2, B3));
    }

    @DisplayName("진행중인 게임에서 종료된 상태를 확인할 수 있다.")
    @Test
    void checkStateTest() {
        assertThat(PLAYING_GAME.isNotEnd()).isTrue();
    }

    @DisplayName("진행중인 게임에서는 보드를 확인할 수 없다.")
    @Test
    void getBoardTest() {
        assertThatNoException()
                .isThrownBy(PLAYING_GAME::getBoard);
    }

    @DisplayName("게임의 첫 턴은 백의 턴이다.")
    @Test
    void firstTurnTest() {
        PlayingGame firstTurnGame = new PlayingGame();
        assertThat(firstTurnGame.getTurnPlayer()).isEqualTo(Color.WHITE);
    }

    @DisplayName("턴이 한번 진행될 때 마다 상대방의 턴으로 변경된다.")
    @Nested
    class NextTurnTest {

        @DisplayName("흑의 턴 다음엔 백의 턴이다.")
        @Test
        void nextOfBlackTest() {
            Board board = new Board();
            PlayingGame currentTurnGame = new PlayingGame(board, Color.BLACK);

            PlayingGame nextTurnGame = (PlayingGame) currentTurnGame.move(B7, B6);
            assertThat(nextTurnGame.getTurnPlayer()).isEqualTo(Color.BLACK.getOpponent());
        }

        @DisplayName("백의 턴 다음엔 흑의 턴이다.")
        @Test
        void nextOfWhiteTest() {
            Board board = new Board();
            PlayingGame currentTurnGame = new PlayingGame(board, Color.WHITE);

            PlayingGame nextTurnGame = (PlayingGame) currentTurnGame.move(B2, B3);
            assertThat(nextTurnGame.getTurnPlayer()).isEqualTo(Color.WHITE.getOpponent());
        }
    }

    @DisplayName("왕이 잡히면 게임이 종료된다.")
    @Test
    void endGame_When_KingIsDie() {
        Location blackRookPosition = new Location(File.A, Rank.ONE);
        Location whiteKingPosition = new Location(File.A, Rank.THREE);
        Board board = new Board(new HashMap<>(Map.of(
                blackRookPosition, new Rook(Color.BLACK),
                whiteKingPosition, new King(Color.WHITE)
        )));

        PlayingGame game = new PlayingGame(board, Color.BLACK);
        assertThat(game.move(blackRookPosition, whiteKingPosition))
                .isInstanceOf(EndGame.class);
    }
}
