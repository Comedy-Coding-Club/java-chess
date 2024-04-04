package repository;

import domain.game.ChessBoard;
import exception.DatabaseException;
import java.util.Optional;
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
        Optional<ChessBoard> chessBoard = chessBoardDao.findByChessGameId();
        if (chessBoard.isEmpty()) {
            throw new DatabaseException("chessBoard를 찾을 수 없습니다.");
        }
        return chessBoard.get();
    }

    @Override
    public void delete() {
        chessBoardDao.delete();
    }
}
