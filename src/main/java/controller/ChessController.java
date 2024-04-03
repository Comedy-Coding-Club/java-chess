package controller;

import controller.command.Command;
import domain.ChessGame;
import service.ChessGameService;
import view.InputView;
import view.OutputView;
import view.command.CommandDto;

public class ChessController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final ChessGameService chessGameService = new ChessGameService();

    public void runChessGame() {
        ChessGame chessGame = new ChessGame(
                chessGameService.findTurn(),
                chessGameService.findAllPiecePositions()
        );
        outputView.printStartMessage();
        while (chessGame.isContinuing()) {
            inputCommandAndExecute(chessGame);
        }
    }

    public void inputCommandAndExecute(final ChessGame chessGame) {
        try {
            CommandDto commandDto = inputView.inputCommand();
            Command command = Command.from(commandDto);
            command.execute(commandDto, chessGameService, outputView, chessGame);
        } catch (final Exception exception) {
            outputView.printErrorMessage(exception.getMessage());
            chessGame.end();
        }
    }
}
