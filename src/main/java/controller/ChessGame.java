package controller;

import static controller.constants.GameState.CHECKMATE;

import controller.constants.GameState;
import controller.dto.GameResult;
import domain.game.ChessBoard;
import domain.game.ChessBoardGenerator;
import domain.game.Referee;
import domain.piece.Piece;
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
        chessBoard.clear();
    }

    public void move(final OutputView outputView, final Position source, final Position target) {
        throwIfNotRunning();
        this.gameState = chessBoard.move(source, target);
        outputView.printChessBoard(chessBoard);

        if (gameState == CHECKMATE) {
            Piece winner = chessBoard.findPieceByPosition(target);
            outputView.printCheckmateWinner(winner.getColor());
        }
    }

    public void status(final OutputView outputView) {
        throwIfNotRunning();

        Referee referee = new Referee(chessBoard);
        GameResult gameResult = referee.judge();
        outputView.printGameResult(gameResult);

        gameState = GameState.STOPPED;
        chessBoard.clear();
    }

    private void throwIfNotRunning() {
        if (!isRunning()) {
            throw new IllegalStateException("[ERROR] 게임이 진행 중인 상태가 아닙니다.");
        }
    }

    public boolean isRunning() {
        return gameState.isNotStarted() || gameState.isRunning();
    }
}
