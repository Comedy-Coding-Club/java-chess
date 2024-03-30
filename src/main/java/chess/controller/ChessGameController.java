package chess.controller;

import chess.domain.ChessGame;
import chess.domain.Color;
import chess.domain.ScoreCalculator;
import chess.domain.board.DBChessBoard;
import chess.domain.dbUtils.BoardDao;
import chess.domain.dbUtils.DBConnectionUtils;
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

        ChessGame chessGame = new ChessGame(new DBChessBoard(new BoardDao(DBConnectionUtils.getConnection())), new ScoreCalculator()); // TODO 이게 맞을까..?
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
        if (command == Command.MOVE) { // TODO start 없이 명령어 입력 받을 수 있는 문제 수정
            handleMoveCommend(chessGame, commandDto);
        }
        if (command == Command.STATUS) {
            handleStatusCommend(chessGame);
        }
        if (command == Command.END || chessGame.isGameOver()) {
            handleEndCommand(chessGame);
            return false;
        }
        return true;
    }

    private void handleStartCommend(ChessGame chessGame) { // TODO 리팩터링 가능할까?
        try {
            if (chessGame.isFirstGame() || inputView.readStartNewGame()) {
                chessGame.initBoard();
            }
            outputView.printBoard(chessGame.getBoard());
        } catch (IllegalArgumentException error) {
            outputView.printError(error);
            handleStartCommend(chessGame);
        }
    }

    private void handleMoveCommend(ChessGame chessGame, CommandDto commandDto) {
        Position fromPosition = PositionParser.parse(commandDto.from());
        Position toPosition = PositionParser.parse(commandDto.to());
        chessGame.handleMove(fromPosition, toPosition);
        outputView.printBoard(chessGame.getBoard());
    }

    private void handleEndCommand(ChessGame chessGame) {
        Color color = chessGame.calculateWinner();
        chessGame.handleClearGame();
        outputView.printWinner(color);
    }

    private void handleStatusCommend(ChessGame chessGame) {
        Map<Color, Double> score = chessGame.handleStatus();
        outputView.printScore(score);
    }
}
