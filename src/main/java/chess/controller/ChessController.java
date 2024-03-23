package chess.controller;

import chess.domain.ChessBoard;
import chess.domain.Command;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void runChess() {
        Command command = Command.fromStartCommand(inputView.readStartCommand());
        if (command == Command.END) {
            return;
        }

        final ChessBoard chessBoard = ChessBoard.init();
        outputView.printChessBoard(chessBoard.getPieces());

        List<String> positions = inputView.readMoveCommand();
        while (isNotEndCommand(positions)) {
            playTurn(chessBoard, positions);
            positions = inputView.readMoveCommand();
        }
    }

    private boolean isNotEndCommand(final List<String> validInputPositions) {
        return !validInputPositions.isEmpty();
    }

    private void playTurn(final ChessBoard chessBoard, final List<String> positions) {
        chessBoard.move(positions);
        outputView.printChessBoard(chessBoard.getPieces());
    }
}
