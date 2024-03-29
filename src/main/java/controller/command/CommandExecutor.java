package controller.command;

import controller.*;
import view.*;

public interface CommandExecutor {
    void execute(OutputView outputView, ChessGame chessGame);
}
