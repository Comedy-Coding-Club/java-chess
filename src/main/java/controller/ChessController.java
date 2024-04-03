package controller;

import controller.command.Command;
import view.InputView;
import view.OutputView;
import view.command.CommandType;

public class ChessController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void runChessGame() {
        ChessGame chessGame = new ChessGame();
        outputView.printStartMessage();
        if (chessGame.isAlreadyRunning()) {
            outputView.printContinuingMessage(chessGame.getChessBoard());
        }
        while (chessGame.isContinuing()) {
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
