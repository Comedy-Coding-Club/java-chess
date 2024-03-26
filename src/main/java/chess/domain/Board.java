package chess.domain;

import chess.domain.position.Column;
import chess.domain.position.Position;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private static final int MINUS_TARGET_SIZE = 2;
    private static final double MINUS_SCORE = 0.5;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void putPiece(Position position, Piece piece) {
        board.put(position, piece);
    }

    public void movePiece(Position from, Position to) {
        Piece piece = board.get(from);
        board.put(to, piece);
        board.remove(from);
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    public Piece findPieceByPosition(Position position) {
        if (hasPiece(position)) {
            return board.get(position);
        }
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }

    public boolean isEmptySpace(Position position) {
        return !hasPiece(position);
    }

    public Map<Color, Double> calculateScore() { // TODO : 보드의 책임이 맞을까??
        return Stream.of(
                        Map.entry(Color.WHITE, calculateScore(Color.WHITE)),
                        Map.entry(Color.BLACK, calculateScore(Color.BLACK)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private double calculateScore(Color color) {
        return calculateScoreBeforeMinus(color) - calculateMinusScore(color);
    }

    private double calculateScoreBeforeMinus(Color color) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(color))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private double calculateMinusScore(Color color) {
        Map<Column, Long> pawnBoard = board.keySet().stream()
                .filter(position -> board.get(position).isSameTeam(color))
                .filter(position -> board.get(position).getPieceType().isPawn())
                .collect(Collectors.groupingBy(Position::getColumn, Collectors.counting()));

        long sameLinePawnCount = pawnBoard.keySet()
                .stream()
                .filter(column -> pawnBoard.get(column) >= MINUS_TARGET_SIZE)
                .map(pawnBoard::get)
                .reduce(0L, Long::sum);

        return MINUS_SCORE * sameLinePawnCount;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board1 = (Board) o;
        return Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }
}
