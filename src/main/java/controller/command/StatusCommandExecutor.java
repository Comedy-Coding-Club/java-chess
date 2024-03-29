package controller.command;

import controller.ChessGame;
import view.OutputView;
import view.command.CommandType;

public class StatusCommandExecutor implements CommandExecutor {
    public StatusCommandExecutor(final CommandType commandType) {
        if (commandType.hasSupplements()) {
            throw new IllegalArgumentException("[ERROR] 결과 조회 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(final OutputView outputView, final ChessGame chessGame) {
        chessGame.status(outputView);
    }
}
