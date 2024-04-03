package repository;

import domain.game.ChessBoard;
import repository.dao.ChessBoardDao;

public class ChessBoardRepositoryImpl implements ChessBoardRepository {
    private final ChessBoardDao chessBoardDao;

    public ChessBoardRepositoryImpl(ChessBoardDao chessBoardDao) {
        this.chessBoardDao = chessBoardDao;
    }

    @Override
    public void save(ChessBoard chessBoard) {
        chessBoardDao.save(chessBoard);
    }

    @Override
    public ChessBoard findByChessGameId() {
        return chessBoardDao.findByChessGameId();
    }

    @Override
    public void delete() {
        chessBoardDao.delete();
    }
}
