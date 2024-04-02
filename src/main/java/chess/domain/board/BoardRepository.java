package chess.domain.board;

import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;

/**
 * 저장소와 관련된 책임
 * CRUD
 */
public interface BoardRepository {
    void placePiece(Position position, Piece piece);
    void removePiece(Position position);
    void clearBoard();
    boolean hasPiece(Position position);
    Piece findPieceByPosition(Position position);
    Map<Position, Piece> getBoard();
}
