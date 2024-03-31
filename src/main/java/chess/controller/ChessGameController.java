package chess.controller;

import chess.domain.ChessGame;
import chess.domain.Color;
import chess.domain.ScoreCalculator;
import chess.domain.board.ChessBoard;
import chess.domain.board.DBChessBoard;
import chess.domain.dbUtils.BoardDao;
import chess.domain.dbUtils.DBConnectionUtils;
import chess.domain.position.Position;
import chess.dto.PositionParser;
import chess.dto.CommandDto;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;

public class ChessGameController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        process();
    }

    private void process() {
        boolean isRunning = true;

        ChessBoard chessBoard = new DBChessBoard(new BoardDao(DBConnectionUtils.getConnection()));
        ChessGame chessGame = new ChessGame(chessBoard, new ScoreCalculator());
        while (isRunning) {
            isRunning = processGame(chessGame);
        }
    }

    private boolean processGame(ChessGame chessGame) {
        try {
            CommandDto commandDto = inputView.readCommand();
            Command command = commandDto.command();
            return handleCommand(chessGame, commandDto, command);
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return processGame(chessGame);
        }
    }

    private boolean handleCommand(ChessGame chessGame, CommandDto commandDto, Command command) {
        if (command == Command.START) {
            handleStartCommand(chessGame);
        }
        if (command == Command.MOVE) {
            handleMoveCommand(chessGame, commandDto);
        }
        if (command == Command.STATUS) {
            handleStatusCommand(chessGame);
        }
        if (command == Command.END || chessGame.isGameOver()) {
            handleStatusCommand(chessGame);
            handleEndCommand(chessGame);
            return false;
        }
        return true;
    }

    private void handleStartCommand(ChessGame chessGame) {
        try {
            handleInitGame(chessGame);
            outputView.printBoard(chessGame.getBoard());
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            handleStartCommand(chessGame);
        }
    }

    private void handleInitGame(ChessGame chessGame) {
        if (chessGame.isFirstGame() || inputView.readStartNewGame()) {
            chessGame.initNewGame();
            return;
        }
        chessGame.loadGame();
    }

    private void handleMoveCommand(ChessGame chessGame, CommandDto commandDto) {
        Position fromPosition = PositionParser.parse(commandDto.from());
        Position toPosition = PositionParser.parse(commandDto.to());
        chessGame.handleMove(fromPosition, toPosition);
        outputView.printBoard(chessGame.getBoard());
    }

    private void handleEndCommand(ChessGame chessGame) {
        Color color = chessGame.calculateWinner();
        chessGame.handleClearGame();
        outputView.printWinner(color);
    }

    private void handleStatusCommand(ChessGame chessGame) {
        Map<Color, Double> score = chessGame.handleStatus();
        outputView.printScore(score);
    }
}
