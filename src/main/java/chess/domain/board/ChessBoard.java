package chess.domain.board;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;

public interface ChessBoard {

    void initNewBoard(Color startColor);
    void clearBoard();
    void putPiece(Position position, Piece piece);
    void movePiece(Position from, Position to);
    void switchTurn(Color currentTurn);
    boolean hasPiece(Position position);
    Piece findPieceByPosition(Position position);
    boolean isEmptySpace(Position position);
    boolean hasTwoKing();
    boolean isFirstGame();
    Map<Position, Piece> getBoard();

    Color getCurrentTurn();
}
