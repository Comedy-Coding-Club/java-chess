package domain.game;

import controller.constants.GameState;
import domain.position.Position;

public class ChessGame {
    private final ChessBoard chessBoard;
    private GameState gameState;

    public ChessGame() {
        chessBoard = new ChessBoard();
        gameState = GameState.RUNNING;
    }

    public void start() {
        gameState = GameState.RUNNING;
    }

    public void end() {
        gameState = GameState.STOPPED;
    }

    public void move(final Position source, final Position target) {
        if (gameState.isStopped()) {
            throw new IllegalStateException("[ERROR] 게임이 종료된 상태이므로 이동할 수 없습니다.");
        }
        chessBoard.move(source, target);
    }

    public boolean isRunning() {
        return gameState.isRunning();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }
}
