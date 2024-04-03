package controller.command;

import domain.ChessGame;
import domain.GameResult;
import view.OutputView;
import view.command.CommandDto;

public class StatusCommandExecutor implements CommandExecutor {
    public StatusCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 결과 조회 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(final OutputView outputView, final ChessGame chessGame) {
        GameResult gameResult = chessGame.status();
        outputView.printGameResult(gameResult);
    }
}
