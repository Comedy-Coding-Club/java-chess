package controller.command;

import domain.dao.ChessBoardDao;
import domain.dao.ChessGameDao;
import domain.game.ChessGame;
import java.util.List;
import repository.ChessBoardRepositoryImpl;
import repository.ChessGameRepositoryImpl;
import service.ChessGameService;
import view.OutputView;

public class StartCommand implements Command {
    private final ChessGameService chessGameService = new ChessGameService(new ChessBoardRepositoryImpl(new ChessBoardDao()), new ChessGameRepositoryImpl(new ChessBoardDao(), new ChessGameDao()));

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

        chessGameService.loadChessGame(chessGame);

        outputView.printStartGame();
        outputView.printChessBoard(chessGame.getChessBoard());
    }
}
