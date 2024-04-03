package controller.command;

import domain.ChessGame;
import domain.game.ChessBoard;
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
        if (chessGame.hasNotGameInProgress()) {
            outputView.printErrorMessage("[ERROR] 진행 중인 게임이 없습니다. 게임을 새로 시작합니다.");
            chessGame.start();
            return;
        }
        ChessBoard chessBoard = chessGame.continueGame();
        outputView.printContinuingMessage(chessBoard);
    }
}
