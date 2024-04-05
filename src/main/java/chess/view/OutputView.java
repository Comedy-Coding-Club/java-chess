package chess.view;

import chess.domain.Command;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printStart() {
        System.out.println("> 체스 게임을 시작합니다." + System.lineSeparator()
                + "> 새로운 게임 시작 : " + Command.START.getMessage() + System.lineSeparator()
                + "> 저장된 게임 시작 : " + Command.CONTINUE.getMessage() + System.lineSeparator()
                + "> 게임 종료 : " + Command.END.getMessage() + System.lineSeparator()
                + "> 게임 이동 :  " + Command.MOVE.getMessage() + " source위치 target위치 - 예. " + Command.MOVE + " b2 b3"
                + "> 우세 진영 :  " + Command.STATUS.getMessage());
    }

    public void printChessBoard(final Map<Position, Piece> pieces) {
        List<List<String>> rows = sortByBoardOrder(pieces);
        rows.forEach(this::printChessRow);
    }

    private List<List<String>> sortByBoardOrder(final Map<Position, Piece> pieces) {
        final List<List<String>> board = new ArrayList<>();

        for (int index = Rank.values().length; index > 0; index--) {
            Rank rank = Rank.fromNumber(index);

            List<String> row = pieces.entrySet().stream()
                    .filter(positionPiece -> positionPiece.getKey().isSameRank(rank))
                    .map(positionPiece -> PieceFormatter.from(positionPiece.getValue()))
                    .toList();

            board.add(row);
        }
        return board;
    }

    private void printChessRow(final List<String> row) {
        row.forEach(System.out::print);
        System.out.println();
    }

    public void printScore(final Map<Color, Double> scores) {
        Double blackScore = scores.get(Color.BLACK);
        Double whiteScore = scores.get(Color.WHITE);

        System.out.printf("블랙 기물 점수: %.1f" + System.lineSeparator(), blackScore);
        System.out.printf("화이트 기물 점수: %.1f" + System.lineSeparator(), whiteScore);
    }

    public void printWinnner(final List<Color> rawWinners) {
        List<String> winners = rawWinners.stream()
                .map(this::convertColor)
                .toList();

        System.out.printf("우세 진영은 %s입니다.", String.join(", ", winners));
    }

    private String convertColor(final Color color) {
        if (color == Color.BLACK) {
            return "블랙";
        }
        return "화이트";
    }
}
