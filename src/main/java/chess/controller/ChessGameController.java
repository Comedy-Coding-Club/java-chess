package chess.controller;

import chess.domain.BoardInitializer;
import chess.domain.ChessGame;
import chess.domain.Color;
import chess.view.Commend;
import chess.view.CommendDto;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    public void run() {
        OutputView.printStartMessage();
        process();
    }

    private void process() {
        boolean isRunning = true;
        ChessGame chessGame = new ChessGame(BoardInitializer.initialize());
        while (isRunning) {
            isRunning = processGame(chessGame);
        }
    }

    private boolean processGame(ChessGame chessGame) {
        try {
            CommendDto commendDto = InputView.readCommend();
            Commend commend = commendDto.commend();
            if (commend == Commend.START) {
                handleStartCommend(chessGame);
            }
            if (commend == Commend.MOVE) {
                handleMoveCommend(chessGame, commendDto);
            }
            if (commend == Commend.END) {
                return false;
            }
            return true;
        } catch (IllegalArgumentException error) {
            OutputView.printError(error);
            return processGame(chessGame);
        }
    }

    private void handleStartCommend(ChessGame chessGame) {
        OutputView.printBoard(chessGame.getBoard());
    }

    private void handleMoveCommend(ChessGame chessGame, CommendDto commendDto) {
        chessGame.handleMove(commendDto);
        OutputView.printBoard(chessGame.getBoard());
    }
}
