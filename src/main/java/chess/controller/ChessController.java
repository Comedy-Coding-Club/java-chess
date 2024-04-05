package chess.controller;

import chess.dao.ChessGameDao;
import chess.domain.ChessGame;
import chess.domain.Command;
import chess.dto.CommandPosition;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGameDao chessGameDao;

    public ChessController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.chessGameDao = new ChessGameDao();
    }

    public void runChess() {
        outputView.printStart();
        CommandPosition commandPosition = CommandPosition.from(inputView.readCommand());

        if (commandPosition.isCommand(Command.START)) {
            chessGameDao.deleteGame();
            final ChessGame chessGame = new ChessGame();
            chessGameDao.addGame(chessGame);

            outputView.printChessBoard(chessGame.getChessBoard());
            playGame(chessGame, commandPosition);
            return;
        }
        if (commandPosition.isCommand(Command.CONTINUE)) {
            final ChessGame chessGame = new ChessGame(chessGameDao.findGame());

            outputView.printChessBoard(chessGame.getChessBoard());
            playGame(chessGame, commandPosition);
            return;
        }
        if (commandPosition.isCommand(Command.END)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
    }

    private void playGame(final ChessGame chessGame, CommandPosition commandPosition) {
        while (!commandPosition.isCommand(Command.END) && !chessGame.doesKingDead()) {
            commandPosition = CommandPosition.from(inputView.readCommand());

            if (commandPosition.isCommand(Command.MOVE)) {
                playTurn(chessGame, commandPosition);
            }
            if (commandPosition.isCommand(Command.STATUS)) {
                outputView.printScore(chessGame.getScore());
                outputView.printWinnner(chessGame.getWinners());
            }
        }
        if (chessGame.doesKingDead()) {
            chessGameDao.deleteGame();
            return;
        }
        chessGameDao.saveGame(chessGame);
    }

    private void playTurn(final ChessGame chessGame, final CommandPosition commandPosition) {
        chessGame.play(commandPosition.getSource(), commandPosition.getTarget());
        outputView.printChessBoard(chessGame.getChessBoard());
    }
}
