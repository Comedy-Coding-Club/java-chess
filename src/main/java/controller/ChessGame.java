package controller;

import controller.constants.GameState;
import domain.game.ChessBoard;
import domain.game.ChessBoardGenerator;
import domain.position.Position;
import view.OutputView;

public class ChessGame {
    private final ChessBoard chessBoard;
    private GameState gameState;

    public ChessGame() {
        chessBoard = ChessBoardGenerator.generate();
        gameState = GameState.NOT_STARTED;
    }

    public void start(final OutputView outputView) {
        gameState = GameState.RUNNING;
        outputView.printChessBoard(chessBoard);
    }

    public void end() {
        gameState = GameState.STOPPED;
    }

    public void move(final OutputView outputView, final Position source, final Position target) {
        if (gameState.isNotStarted()) {
            throw new IllegalStateException("[ERROR] 게임이 시작되지 않았으므로 이동할 수 없습니다.");
        }
        if (gameState.isStopped()) {
            throw new IllegalStateException("[ERROR] 게임이 종료된 상태이므로 이동할 수 없습니다.");
        }
        this.gameState = chessBoard.move(source, target);
        outputView.printChessBoard(chessBoard);
    }

    public void status(final OutputView outputView) {
        gameState = GameState.STOPPED;
    }


    public boolean isRunning() {
        return gameState.isNotStarted() || gameState.isRunning();
    }
}
