package chess;

import chess.controller.ChessController;
import chess.dao.BoardDao;
import chess.dao.SquareDao;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final ChessService chessService = new ChessService(new SquareDao(), new BoardDao());
        final ChessController chessController = new ChessController(inputView, outputView, chessService);
        chessController.run();
    }
}
