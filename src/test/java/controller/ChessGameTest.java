package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.OutputView;

class ChessGameTest {
    @DisplayName("체스 게임을 시작한다.")
    @Test
    void startChessGame() {
        ChessGame chessGame = new ChessGame();
        chessGame.start(new OutputView());

        assertThat(chessGame.isContinuing()).isTrue();
    }

    @DisplayName("체스 게임을 종료한다.")
    @Test
    void endChessGame() {
        ChessGame chessGame = new ChessGame();
        chessGame.start(new OutputView());
        chessGame.end();

        assertThat(chessGame.isContinuing()).isFalse();
    }

    @DisplayName("체스 게임이 시작되지 않은 상태에서 이동을 하는 경우 오류를 반환한다.")
    @Test
    void failMoveIfGameIsNotStarted() {
        ChessGame chessGame = new ChessGame();
        Position source = new Position(new File('a'), new Rank(2));
        Position target = new Position(new File('a'), new Rank(3));

        assertThatThrownBy(() -> chessGame.move(new OutputView(), source, target))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("체스 게임이 중단된 상태에서 이동을 하는 경우 오류를 반환한다.")
    @Test
    void failMoveIfGameIsStopped() {
        ChessGame chessGame = new ChessGame();
        chessGame.start(new OutputView());
        chessGame.end();
        Position source = new Position(new File('a'), new Rank(2));
        Position target = new Position(new File('a'), new Rank(3));

        assertThatThrownBy(() -> chessGame.move(new OutputView(), source, target))
                .isInstanceOf(IllegalStateException.class);
    }
}
