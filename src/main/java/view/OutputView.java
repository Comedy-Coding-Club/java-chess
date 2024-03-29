package view;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.Score;
import domain.position.Position;
import java.util.Map;

public class OutputView {
    private static final OutputFormat FORMAT = new OutputFormat();
    private static final String COMMAND_MESSAGE = "> 체스 게임을 시작합니다.%n"
            + "> 게임 시작 : start%n"
            + "> 게임 종료 : end%n"
            + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3%n";

    public void printCommandMessage() {
        System.out.printf(COMMAND_MESSAGE);
    }

    public void printStartGame() {
        System.out.println("체스 게임이 시작되었습니다.");
    }

    public void printChessBoard(final Map<Position, Piece> chessBoard) {
        System.out.println(FORMAT.parseChessBoard(chessBoard));
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printStatus(Map<Color, Score> scoreBoard) {
        System.out.println(FORMAT.parseScoreBoard(scoreBoard));
    }

    public void printWinner(Color color) {
        System.out.println(String.format("%s팀이 승리했습니다!", color));
    }

    public void printEndGame() {
        System.out.println("체스 게임이 종료되었습니다.");
    }
}
