package controller.command;

import controller.ChessGame;
import view.OutputView;
import view.command.CommandDto;

public class ContinueCommandExecutor implements CommandExecutor {
    public ContinueCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 게임을 이어서 진행하는 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(final OutputView outputView, final ChessGame chessGame) {
        chessGame.continueGame(outputView);
    }
}
