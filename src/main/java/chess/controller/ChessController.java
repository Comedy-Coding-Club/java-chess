package chess.controller;

import static chess.exception.RetryHandler.retryOnException;

import chess.dto.ScoresDto;
import chess.dto.ChessBoardDto;
import chess.domain.command.Command;
import chess.domain.board.ChessBoard;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService chessService;

    public ChessController(final InputView inputView, final OutputView outputView, final ChessService chessService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessService = chessService;
    }

    public void run() {
        outputView.printCommandMenu();
        retryOnException(this::startGame);
    }

    private void startGame() {
        final Command command = Command.fromStart(inputView.readCommand());

        if (command.isReload()) {
            startGame(chessService.findRecentBoard());
        }

        if (command.isStart()) {
            startGame(chessService.createBoard());
        }
    }

    private void startGame(final ChessBoard chessBoard) {
        outputView.printChessBoard(ChessBoardDto.from(chessBoard.getPieces()));
        retryOnException(() -> playTurn(chessBoard));
    }

    private void playTurn(final ChessBoard chessBoard) {
        while (true) {
            final Command command = Command.fromPlay(inputView.readCommand());

            if (command.isMove()) {
                chessService.move(command, chessBoard);
            }

            printResult(command, chessBoard);

            if (command.isEnd() || chessBoard.isKingCaught()) {
                break;
            }
        }
    }

    private void printResult(final Command command, final ChessBoard chessBoard) {
        if (command.isMove()) {
            outputView.printChessBoard(ChessBoardDto.from(chessBoard.getPieces()));
        }
        if (command.isStatus()) {
            outputView.printScores(ScoresDto.fromChessBoard(chessBoard));
        }
        if (chessBoard.isKingCaught()) {
            outputView.printWinner(chessBoard.getTurn());
        }
    }
}
