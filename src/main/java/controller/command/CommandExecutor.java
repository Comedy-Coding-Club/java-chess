package controller.command;

import domain.ChessGame;
import service.ChessGameService;
import view.OutputView;

public interface CommandExecutor {
    void execute(ChessGameService chessGameService, OutputView outputView, ChessGame chessGame);
}
