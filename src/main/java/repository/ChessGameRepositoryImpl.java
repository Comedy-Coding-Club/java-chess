package repository;

import domain.dao.ChessBoardDao;
import domain.dao.ChessGameDao;
import domain.game.GameState;
import domain.piece.Color;

public class ChessGameRepositoryImpl implements ChessGameRepository{
    private final ChessBoardDao chessBoardDao;
    private final ChessGameDao chessGameDao;

    public ChessGameRepositoryImpl(ChessBoardDao chessBoardDao, ChessGameDao chessGameDao) {
        this.chessBoardDao = chessBoardDao;
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
