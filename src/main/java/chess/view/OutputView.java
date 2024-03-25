package chess.view;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final Map<PieceType, String> PIECE_SYMBOL = Map.of(
            PieceType.KING, "k",
            PieceType.QUEEN, "q",
            PieceType.ROOK, "r",
            PieceType.KNIGHT, "n",
            PieceType.BISHOP, "b",
            PieceType.PAWN, "p"
    );
    public void printGameStart() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printBoard(Map<Location, Piece> board) {
        Arrays.stream(Row.values()).sorted(Comparator.reverseOrder())
                .forEach(row -> {
                    printBoardRow(row, board);
                    System.out.println();
                });
    }

    private void printBoardRow(Row row, Map<Location, Piece> board) {
        for (Column column : Column.values()) {
            Location location = new Location(column, row);
            Piece piece = board.get(location);
            System.out.print(convertPieceToString(piece));
        }
    }

    private String convertPieceToString(Piece piece) {
        if (piece == null) {
            return ".";
        }
        String s = getPieceString(piece.getPieceType());
        if (piece.isColor(Color.BLACK)) {
            return s.toUpperCase();
        }
        return s.toLowerCase();
    }

    private String getPieceString(PieceType type) {
        return Optional.ofNullable(PIECE_SYMBOL.get(type))
                .orElseThrow(() -> new IllegalArgumentException("누락된 기물 타입이 존재합니다."));
    }

    public void printExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }
}
