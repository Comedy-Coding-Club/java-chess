package view;

import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import domain.score.Score;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final OutputFormat FORMAT = new OutputFormat();
    private static final String COMMAND_MESSAGE = "> 체스 게임을 시작합니다.%n"
            + "> 게임 시작 : start%n"
            + "> 게임 종료 : end%n"
            + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3%n"
            + "> 점수 계산 : status%n";

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
        System.out.println();
        System.out.println(FORMAT.parseScoreBoard(scoreBoard));
    }

    public void printHigherScoreColor(List<Color> colors) {
        colors.forEach(color -> System.out.println(String.format(">> %s 색깔의 팀이 이기고 있습니다.", color)));
    }

    public void printEqualScores() {
        System.out.println(">> 두 플레이어의 점수가 동일합니다.");
    }

    public void printWinner(Color color) {
        System.out.println(String.format("###%s팀이 승리했습니다!###", color));
    }

    public void printEndGame() {
        System.out.println("체스 게임이 종료되었습니다.");
    }
}
