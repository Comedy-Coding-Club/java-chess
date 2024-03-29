package controller.command;

import controller.*;
import view.*;
import view.command.*;

public class EndCommandExecutor implements CommandExecutor {
    public EndCommandExecutor(final CommandType commandType) {
        if (commandType.hasSupplements()) {
            throw new IllegalArgumentException("[ERROR] 게임 종료 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(final OutputView outputView, final ChessGame chessGame) {
        chessGame.end();
    }
}
