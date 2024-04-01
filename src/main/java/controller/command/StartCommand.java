package controller.command;

import domain.dao.ChessBoardDao;
import domain.dao.ChessGameDao;
import domain.game.ChessBoard;
import domain.game.ChessGame;
import domain.game.GameState;
import java.util.List;
import view.OutputView;

public class StartCommand implements Command {
    public StartCommand(List<String> arguments) {
        validate(arguments);
    }

    private void validate(List<String> arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException("잘못된 start 명령어 입니다.");
        }
    }

    @Override
    public void execute(ChessGame chessGame, OutputView outputView) {
        chessGame.start();

        loadChessGame(chessGame);

        outputView.printStartGame();
        outputView.printChessBoard(chessGame.getChessBoard());
    }

    private void loadChessGame(ChessGame chessGame) {
        ChessGameDao chessGameDao = new ChessGameDao();
        ChessBoardDao chessBoardDao = new ChessBoardDao();

        GameState gameState = chessGameDao.getGameStatusById();
        ChessBoard chessBoard = chessBoardDao.findByChessGameId();
        if (gameState == null) {
            chessGameDao.save(chessGame.getColor(), chessGame.getGameState());
            chessBoardDao.save(chessGame.getBoard());
        }

        if (gameState != null) {
            chessGame.update(chessBoard, gameState);
        }
    }
}
