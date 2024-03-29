package controller;

import controller.command.*;
import view.*;
import view.command.*;

public class ChessController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void runChessGame() {
        ChessGame chessGame = new ChessGame();
        outputView.printStartMessage();
        while (chessGame.isRunning()) {
            inputCommandAndExecute(chessGame);
        }
    }

    public void inputCommandAndExecute(final ChessGame chessGame) {
        try {
            CommandType commandType = inputView.inputCommand();
            Command command = Command.from(commandType);
            command.execute(outputView, chessGame);

        } catch (final Exception exception) {
            OutputView.printErrorMessage(exception.getMessage());
            chessGame.end();
        }
    }
}
