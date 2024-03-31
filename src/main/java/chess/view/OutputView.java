package chess.view;

import chess.domain.board.ChessBoard;
import chess.domain.board.MemoryChessBoard;
import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class OutputView {

    public void printStartMessage() {
        System.out.println("""
                체스 게임을 시작합니다.
                게임 시작 : start
                게임 종료 : end
                게임 이동 : move source위치 target위치 - 예. move b2 b3
                """);
    }

    public void printBoard(ChessBoard chessBoard) {
        List<StringBuilder> result = new ArrayList<>();
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));
        result.add(new StringBuilder("........"));

        chessBoard.getBoard().keySet()
                .forEach(position -> placePiece(chessBoard, result, position));
        result.forEach(System.out::println);
        System.out.println();
    }

    private void placePiece(ChessBoard chessBoard, List<StringBuilder> result, Position position) {
        Piece piece = chessBoard.getBoard().get(position);
        int rowIndex = position.getRowIndex();
        int columnIndex = position.getColumnIndex();
        result.get(rowIndex).replace(columnIndex, columnIndex + 1, PieceMapper.findByPieceType(piece));
    }

    public void printScore(Map<Color, Double> scores) {
        scores.forEach((color, score) -> System.out.printf("%s 팀 점수 : %.1f\n", color.name(), score));
    }

    public void printWinner(Color color) {
        System.out.println("승리 팀 : " + color.name());
    }

    public void printError(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
