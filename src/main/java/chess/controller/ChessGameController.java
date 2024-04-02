package chess.controller;

import chess.domain.ChessGameService;
import chess.domain.Color;
import chess.domain.ScoreCalculator;
import chess.domain.board.ChessBoardService;
import chess.db.DBBoardRepository;
import chess.db.BoardDao;
import chess.db.DBConnectionUtils;
import chess.db.GameDao;
import chess.domain.position.Position;
import chess.dto.PositionParser;
import chess.dto.CommandDto;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;
import java.sql.Connection;
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

        Connection connection = DBConnectionUtils.getConnection();
        ChessBoardService chessBoardService = new ChessBoardService(new DBBoardRepository(new BoardDao(connection)));
        ChessGameService chessGameService = new ChessGameService(chessBoardService, new ScoreCalculator(), new GameDao(connection));
        while (isRunning) {
            isRunning = processGame(chessGameService);
        }
    }

    private boolean processGame(ChessGameService chessGameService) {
        try {
            CommandDto commandDto = inputView.readCommand();
            Command command = commandDto.command();
            return handleCommand(chessGameService, commandDto, command);
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return processGame(chessGameService);
        }
    }

    private boolean handleCommand(ChessGameService chessGameService, CommandDto commandDto, Command command) {
        if (command == Command.START) {
            handleStartCommand(chessGameService);
        }
        if (command == Command.MOVE) {
            handleMoveCommand(chessGameService, commandDto);
        }
        if (command == Command.STATUS) {
            handleStatusCommand(chessGameService);
        }
        if (command == Command.END || chessGameService.isGameOver()) {
            handleStatusCommand(chessGameService);
            handleEndCommand(chessGameService);
            return false;
        }
        return true;
    }

    private void handleStartCommand(ChessGameService chessGameService) {
        try {
            handleInitGame(chessGameService);
            outputView.printBoard(chessGameService.getBoard());
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            handleStartCommand(chessGameService);
        }
    }

    private void handleInitGame(ChessGameService chessGameService) {
        if (chessGameService.isFirstGame() || inputView.readStartNewGame()) {
            chessGameService.initNewGame();
            return;
        }
        chessGameService.loadGame();
    }

    private void handleMoveCommand(ChessGameService chessGameService, CommandDto commandDto) {
        Position fromPosition = PositionParser.parse(commandDto.from());
        Position toPosition = PositionParser.parse(commandDto.to());
        chessGameService.handleMove(fromPosition, toPosition);
        outputView.printBoard(chessGameService.getBoard());
    }

    private void handleEndCommand(ChessGameService chessGameService) {
        Color color = chessGameService.calculateWinner();
        chessGameService.handleEndGame();
        outputView.printWinner(color);
    }

    private void handleStatusCommand(ChessGameService chessGameService) {
        Map<Color, Double> score = chessGameService.handleStatus();
        outputView.printScore(score);
    }
}
