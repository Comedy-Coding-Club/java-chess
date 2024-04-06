package chess.controller;

import chess.domain.ChessGameService;
import chess.domain.Color;
import chess.domain.ScoreCalculator;
import chess.domain.position.Position;
import chess.dto.CommandDto;
import chess.dto.PositionParser;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;

public class CommandController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGameService chessGameService;

    public CommandController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        chessGameService = new ChessGameService(new ScoreCalculator());
    }

    public State handleCommand(CommandDto commandDto, Command command) {
        if (command == Command.START) {
            handleStartCommand();
        }
        if (command == Command.MOVE) {
            handleMoveCommand(commandDto);
        }
        if (command == Command.STATUS) {
            handleStatusCommand();
        }
        if (command == Command.END || chessGameService.isGameOver()) {
            handleStatusCommand();
            handleEndCommand();
            return State.END;
        }
        return State.RUNNING;
    }

    private void handleStartCommand() {
        try {
            handleInitGame();
            outputView.printBoard(chessGameService.getBoard());
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            handleStartCommand();
        }
    }

    private void handleInitGame() {
        if (chessGameService.isFirstGame() || inputView.readStartNewGame()) {
            chessGameService.initNewGame();
        }
    }

    private void handleMoveCommand(CommandDto commandDto) {
        Position fromPosition = PositionParser.parse(commandDto.from());
        Position toPosition = PositionParser.parse(commandDto.to());
        chessGameService.handleMove(fromPosition, toPosition);
        outputView.printBoard(chessGameService.getBoard());
    }

    private void handleEndCommand() {
        Color color = chessGameService.calculateWinner();
        chessGameService.handleEndGame();
        outputView.printWinner(color);
    }

    private void handleStatusCommand() {
        Map<Color, Double> score = chessGameService.handleStatus();
        outputView.printScore(score);
    }
}
