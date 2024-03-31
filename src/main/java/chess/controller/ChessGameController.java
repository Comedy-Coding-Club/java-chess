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

public class ChessGameController { // TODO DB 테이블 이름 변경

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

        ChessBoard chessBoard = new DBChessBoard(new BoardDao(DBConnectionUtils.getConnection())); // TODO 이게 맞을까..?
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
        if (command == Command.MOVE) { // TODO start 없이 명령어 입력 받을 수 있는 문제 수정
            handleMoveCommand(chessGame, commandDto);
        }
        if (command == Command.STATUS) {
            handleStatusCommand(chessGame);
        }
        if (command == Command.END || chessGame.isGameOver()) {
            handleEndCommand(chessGame);
            return false;
        }
        return true;
    }

    private void handleStartCommand(ChessGame chessGame) {
        try {
            handleInit(chessGame);
            outputView.printBoard(chessGame.getBoard());
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            handleStartCommand(chessGame);
        }
    }

    private void handleInit(ChessGame chessGame) {
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
