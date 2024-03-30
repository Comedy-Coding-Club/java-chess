package chess.domain.board;

import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;

public interface ChessBoard {
    int DEFAULT_KING_COUNT = 2; // TODO : 이렇게 해도 괜찮나?? ChessBoard 를 구현하는 곳에 정의해주기에는 관리하기가 어려울 것 같음

    void initBoard();
    void clearBoard();
    void putPiece(Position position, Piece piece);
    void movePiece(Position from, Position to);
    boolean hasPiece(Position position);
    Piece findPieceByPosition(Position position);
    boolean isEmptySpace(Position position);
    boolean hasTwoKing();
    boolean isFirstGame();

    Map<Position, Piece> getBoard();
}
