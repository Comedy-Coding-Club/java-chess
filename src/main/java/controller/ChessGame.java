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
import repository.GameStateRepository;
import view.OutputView;

public class ChessGame {
    private ChessBoard chessBoard;
    private GameState gameState;
    private GameStateRepository gameStateRepository;

    public ChessGame() {
        this.chessBoard = new ChessBoard();

        this.gameStateRepository = new GameStateRepository();
        this.gameState = gameStateRepository.find();
        this.gameStateRepository.save(gameState);
    }

    public void start(final OutputView outputView) {
        gameState = GameState.RUNNING;
        gameStateRepository.save(gameState);

        chessBoard = ChessBoardGenerator.generate();
        chessBoard.saveChessBoard();
        outputView.printChessBoard(chessBoard);
    }

    public void end() {
        gameState = GameState.STOPPED;
        gameStateRepository.save(NOT_STARTED);

        chessBoard.clear();
    }

    public void move(final OutputView outputView, final Position source, final Position target) {
        throwIfNotRunning();
        this.gameState = chessBoard.move(source, target);
        gameStateRepository.save(generateSavingState(gameState));
        outputView.printChessBoard(chessBoard);

        if (gameState == CHECKMATE) {
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
        throwIfNotRunning();
        gameState = GameState.STOPPED;
        gameStateRepository.save(NOT_STARTED);

        Referee referee = new Referee(chessBoard);
        GameResult gameResult = referee.judge();
        outputView.printGameResult(gameResult);

        chessBoard.clear();
    }

    private void throwIfNotRunning() {
        if (gameState != RUNNING) {
            throw new IllegalStateException("[ERROR] 게임이 진행 중인 상태가 아닙니다.");
        }
    }

    public boolean isContinuing() {
        return gameState.isNotStarted() || gameState.isRunning();
    }
}
