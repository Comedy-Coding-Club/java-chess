package controller.command;

import domain.game.ChessGame;
import domain.game.ScoreBoard;
import java.util.List;
import view.OutputView;

public class StautsCommand implements Command {

    public StautsCommand(List<String> arguments) {
        validate(arguments);
    }

    private void validate(List<String> arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException("잘못된 status 명령어 입니다.");
        }
    }

    @Override
    public void execute(ChessGame chessGame, OutputView outputView) {
        ScoreBoard scoreBoard = chessGame.status();
        outputView.printStatus(scoreBoard.getBoard());

        if (scoreBoard.isEqualScores()) {
            outputView.printEqualScores();
            return;
        }
        outputView.printHigherScoreColor(scoreBoard.winnerColor());
    }
}
