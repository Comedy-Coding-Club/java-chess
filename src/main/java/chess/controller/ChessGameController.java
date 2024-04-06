package chess.controller;

import chess.dto.CommandDto;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final CommandController commandController;

    public ChessGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        commandController = new CommandController(inputView, outputView);
    }

    public void run() {
        outputView.printStartMessage();
        process();
    }

    private void process() {
        boolean isRunning = true;

        while (isRunning) {
            isRunning = processGame();
        }
    }

    private boolean processGame() {
        try {
            CommandDto commandDto = inputView.readCommand();
            Command command = commandDto.command();
            return commandController.handleCommand(commandDto, command);
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            return processGame();
        }
    }
}
