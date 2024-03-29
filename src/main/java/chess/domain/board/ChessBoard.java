package chess.domain.board;

import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;

public interface ChessBoard {
    void putPiece(Position position, Piece piece);
    void movePiece(Position from, Position to);
    boolean hasPiece(Position position);
    Piece findPieceByPosition(Position position);
    boolean isEmptySpace(Position position);
    boolean hasKing(int kingCount);
    Map<Position, Piece> getBoard();
}
