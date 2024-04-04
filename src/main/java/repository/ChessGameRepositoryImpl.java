package repository;

import domain.game.GameState;
import domain.piece.Color;
import exception.DatabaseException;
import java.util.Optional;
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
        Optional<GameState> gameState = chessGameDao.findGameStatusById();
        if (gameState.isEmpty()) {
            throw new DatabaseException("GameState를 찾을 수 없습니다.");
        }
        return gameState.get();
    }

    @Override
    public Color findColorById() {
        Optional<Color> colorById = chessGameDao.findColorById();
        if (colorById.isEmpty()) {
            throw new DatabaseException("Color를 찾을 수 없습니다.");
        }
        return colorById.get();
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
