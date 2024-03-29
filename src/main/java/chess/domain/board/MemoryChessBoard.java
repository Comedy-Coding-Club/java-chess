package chess.domain.board;

import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;
import java.util.Objects;

public class MemoryChessBoard implements ChessBoard{

    private final Map<Position, Piece> board;

    public MemoryChessBoard(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public void putPiece(Position position, Piece piece) {
        board.put(position, piece);
    }

    @Override
    public void movePiece(Position from, Position to) {
        Piece piece = board.get(from);
        board.put(to, piece);
        board.remove(from);
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
    public boolean isEmptySpace(Position position) {
        return !hasPiece(position);
    }

    @Override
    public boolean hasKing(int count) { // TODO 보드는 최대한 보드 상태에만 물어보도록 hasTowKing 이라고 명명
        int kingCount = (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
        return kingCount == count;
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
        MemoryChessBoard memoryChessBoard1 = (MemoryChessBoard) o;
        return Objects.equals(board, memoryChessBoard1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }
}
