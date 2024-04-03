package repository;

import domain.game.GameState;
import domain.piece.Color;
import repository.dao.ChessGameDao;

public class ChessGameRepositoryImpl implements ChessGameRepository {
    private final ChessGameDao chessGameDao;

    public ChessGameRepositoryImpl(ChessGameDao chessGameDao) {
        this.chessGameDao = chessGameDao;
    }

    @Override
    public void save(Color color, GameState gameState) {
        chessGameDao.save(color, gameState);
    }

    @Override
    public GameState findGameStatusById() {
        return chessGameDao.findGameStatusById();
    }

    @Override
    public Color findColorById() {
        return chessGameDao.findColorById();
    }

    @Override
    public void updateGameStatus(GameState gameState) {
        chessGameDao.updateGameStatus(gameState);
    }

    @Override
    public void updateColor(Color color) {
        chessGameDao.updateColor(color);
    }

    @Override
    public boolean delete() {
        return chessGameDao.delete();
    }
}
