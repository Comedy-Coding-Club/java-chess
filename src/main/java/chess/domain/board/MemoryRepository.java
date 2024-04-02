package chess.domain.board;

import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;
import java.util.Objects;

public class MemoryRepository implements BoardRepository{

    private final Map<Position, Piece> board;

    public MemoryRepository(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public void placePiece(Position position, Piece piece) {
        board.put(position, piece);
    }

    @Override
    public void removePiece(Position position) {
        board.remove(position);
    }

    @Override
    public void clearBoard() {
        board.clear();
    }

    @Override
    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    @Override
    public Piece findPieceByPosition(Position position) {
        if (hasPiece(position)) {
            return board.get(position);
        }
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }

    @Override
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
        MemoryRepository memoryRepository1 = (MemoryRepository) o;
        return Objects.equals(board, memoryRepository1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }
}
