package repository;

import domain.game.ChessBoard;

public interface ChessBoardRepository {

    void save(ChessBoard chessBoard);

    ChessBoard findByChessGameId();

    void delete();
}
