package domain;

import static controller.constants.GameState.NOT_STARTED;
import static controller.constants.GameState.RUNNING;

import controller.constants.GameState;
import controller.dto.MoveResult;
import domain.game.ChessBoard;
import domain.game.ChessBoardGenerator;
import domain.position.Position;

public class ChessGame {
    private ChessBoard chessBoard;
    private GameState gameState;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.gameState = NOT_STARTED;
    }

    public ChessBoard start() {
        gameState = GameState.RUNNING;

        chessBoard = ChessBoardGenerator.generate();
        chessBoard.saveChessBoard();
        return chessBoard;
    }

    public void end() {
        gameState = GameState.STOPPED;

        chessBoard.clear();
    }

    public MoveResult move(final Position source, final Position target) {
        validateNotRunning();
        this.gameState = chessBoard.move(source, target);

        return new MoveResult(gameState, chessBoard.findPieceByPosition(target));
    }

    public GameResult status() {
        validateNotRunning();
        return GameResult.from(chessBoard);
    }

    private void validateNotRunning() {
        if (gameState != RUNNING) {
            throw new IllegalStateException("[ERROR] 게임이 진행 중인 상태가 아닙니다.");
        }
    }

    public ChessBoard continueGame() {
        gameState = RUNNING;
        return chessBoard;
    }

    public boolean isContinuing() {
        return gameState == NOT_STARTED || gameState == RUNNING;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public boolean hasNotGameInProgress() {
        return chessBoard.isEmpty();
    }
}
