package controller.command;

import domain.ChessGame;
import view.OutputView;

public interface CommandExecutor {
    void execute(OutputView outputView, ChessGame chessGame);
}
