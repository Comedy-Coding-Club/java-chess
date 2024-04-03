package controller.command;

import domain.ChessGame;
import domain.game.ChessBoard;
import service.ChessGameService;
import view.OutputView;
import view.command.CommandDto;

public class StartCommandExecutor implements CommandExecutor {
    public StartCommandExecutor(final CommandDto commandDto) {
        if (commandDto.isInvalidSupplementSize()) {
            throw new IllegalArgumentException("[ERROR] 게임 시작 명령어를 올바르게 입력해주세요.");
        }
    }

    @Override
    public void execute(
            final ChessGameService chessGameService,
            final OutputView outputView,
            final ChessGame chessGame
    ) {
        ChessBoard chessBoard = chessGame.start();
        outputView.printChessBoard(chessBoard);
        chessGameService.saveAllPiecePositions(chessBoard.getPiecePosition());
    }
}
