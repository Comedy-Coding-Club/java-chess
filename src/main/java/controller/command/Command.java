package controller.command;

import domain.game.ChessGame;
import view.OutputView;

public interface Command {
    void execute(ChessGame chessGame, OutputView outputView);
}
