package chess.view;

import chess.dto.ScoresDto;
import chess.domain.piece.Color;
import chess.dto.ChessBoardDto;
import java.util.List;

public class OutputView {

    public void printCommandMenu() {
        System.out.println("""
                > 체스 게임을 시작합니다.
                > 게임 이어하기 : reload
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3""");
    }

    public void printChessBoard(final ChessBoardDto chessBoardDto) {
        chessBoardDto.chessBoard().forEach(this::printChessRow);
    }

    public void printScores(final ScoresDto scoresDto) {
        final double blackScore = scoresDto.blackScore();
        final double whiteScore = scoresDto.whiteScore();

        System.out.println(String.format("BLACK 점수 : %.2f, WHITE 점수 : %.2f", blackScore, whiteScore));
    }

    public void printWinner(final Color winner) {
        System.out.println(String.format("축하합니다🎉 우승자는 %s입니다.", winner));
    }

    private void printChessRow(final List<Character> row) {
        row.forEach(System.out::print);
        System.out.println();
    }
}
