package chess.controller;

import chess.domain.board.BoardInitializer;
import chess.domain.board.DefaultBoardInitializer;
import chess.domain.ChessGame;
import chess.domain.Color;
import chess.domain.ScoreCalculator;
import chess.domain.board.MysqlBoardInitializer;
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
        BoardInitializer boardInitializer = new MysqlBoardInitializer();
        ChessGame chessGame = new ChessGame(boardInitializer.initialize(), new ScoreCalculator());
        while (isRunning) {
            isRunning = processGame(chessGame);
        }
    }

    private boolean processGame(ChessGame chessGame) {
        try {
            CommandDto commandDto = inputView.readCommend();
            Command command = commandDto.command();
            return handleCommend(chessGame, commandDto, command);
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return processGame(chessGame);
        }
    }

    private boolean handleCommend(ChessGame chessGame, CommandDto commandDto, Command command) {
        if (command == Command.START) {
            handleStartCommend(chessGame);
        }
        if (command == Command.MOVE) {
            handleMoveCommend(chessGame, commandDto);
        }
        if (command == Command.STATUS) {
            handleStatusCommend(chessGame);
        }
        if (command == Command.END || chessGame.isEnd()) {
            handleEndCommand(chessGame);
            return false;
        }
        return true;
    }

    private void handleStartCommend(ChessGame chessGame) {
        outputView.printBoard(chessGame.getBoard());
    }

    private void handleMoveCommend(ChessGame chessGame, CommandDto commandDto) {
        Position fromPosition = PositionParser.parse(commandDto.from());
        Position toPosition = PositionParser.parse(commandDto.to());
        chessGame.handleMove(fromPosition, toPosition);
        outputView.printBoard(chessGame.getBoard());
    }

    private void handleEndCommand(ChessGame chessGame) {
        Color color = chessGame.calculateWinner();
        outputView.printWinner(color);
    }

    private void handleStatusCommend(ChessGame chessGame) {
        Map<Color, Double> score = chessGame.handleStatus();
        outputView.printScore(score);
    }
}
