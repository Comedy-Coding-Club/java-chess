package controller;

import static controller.constants.GameState.CHECKMATE;
import static controller.constants.GameState.NOT_STARTED;
import static controller.constants.GameState.RUNNING;

import controller.constants.GameState;
import controller.dto.GameResult;
import domain.game.ChessBoard;
import domain.game.ChessBoardGenerator;
import domain.game.Referee;
import domain.piece.Piece;
import domain.position.Position;
import view.OutputView;

public class ChessGame {
    private ChessBoard chessBoard;
    private GameState gameState;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.gameState = NOT_STARTED;
    }

    public void start(final OutputView outputView) {
        gameState = GameState.RUNNING;

        chessBoard = ChessBoardGenerator.generate();
        chessBoard.saveChessBoard();
        outputView.printChessBoard(chessBoard);
    }

    public void end() {
        gameState = GameState.STOPPED;

        chessBoard.clear();
    }

    public void move(final OutputView outputView, final Position source, final Position target) {
        validateNotRunning();
        this.gameState = chessBoard.move(source, target);
        outputView.printChessBoard(chessBoard);

        if (gameState == CHECKMATE) { // TODO: npe를 체크하는 담당이 아님. 외부에서 넘겨준 객체를 믿고 간다!
            Piece winner = chessBoard.findPieceByPosition(target);
            outputView.printCheckmateWinner(winner.getColor());
        }
    }

    private GameState generateSavingState(final GameState gameState) {
        if (gameState == CHECKMATE) {
            return NOT_STARTED;
        }
        return gameState;
    }

    public void status(final OutputView outputView) {
        validateNotRunning();

        Referee referee = new Referee(chessBoard);
        GameResult gameResult = referee.judge();
        outputView.printGameResult(gameResult);

        chessBoard.clear();
    }

    private void validateNotRunning() {
        if (gameState != RUNNING) {
            throw new IllegalStateException("[ERROR] 게임이 진행 중인 상태가 아닙니다.");
        }
    }

    public boolean isContinuing() {
        return gameState == NOT_STARTED || gameState == RUNNING;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }
}
