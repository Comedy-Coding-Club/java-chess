package chess.domain;

import chess.domain.position.Column;
import chess.domain.position.Position;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreCalculator {

    private static final int MINUS_TARGET_SIZE = 2;
    private static final double MINUS_PAWN_SCORE = 0.5;

    public Map<Color, Double> calculateScore(Map<Position, Piece> board) {
        return Stream.of(
                        Map.entry(Color.WHITE, calculateScore(board, Color.WHITE)),
                        Map.entry(Color.BLACK, calculateScore(board, Color.BLACK)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private double calculateScore(Map<Position, Piece> board, Color color) {
        return calculateScoreBeforeMinus(board, color) - calculateMinusScore(board, color);
    }

    private double calculateScoreBeforeMinus(Map<Position, Piece> board, Color color) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(color))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private double calculateMinusScore(Map<Position, Piece> board, Color color) {
        Map<Column, Long> pawnBoard = board.keySet().stream()
                .filter(position -> board.get(position).isSameTeam(color))
                .filter(position -> board.get(position).getPieceType().isPawn())
                .collect(Collectors.groupingBy(Position::getColumn, Collectors.counting()));

        long sameLinePawnCount = pawnBoard.keySet()
                .stream()
                .filter(column -> pawnBoard.get(column) >= MINUS_TARGET_SIZE)
                .map(pawnBoard::get)
                .reduce(0L, Long::sum);

        return MINUS_PAWN_SCORE * sameLinePawnCount;
    }
}
