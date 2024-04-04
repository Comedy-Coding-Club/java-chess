package repository.dao;

import domain.game.ChessBoard;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeChessBoardDao extends ChessBoardDao {
    public static final int DEFAULT_KEY = 1;
    Map<Integer, ChessBoard> chessBoars = new HashMap<>();


    @Override
    public void save(ChessBoard chessBoard) {
        chessBoars.put(DEFAULT_KEY, chessBoard);
    }

    @Override
    public Optional<ChessBoard> findByChessGameId() {
        if (chessBoars.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(chessBoars.get(DEFAULT_KEY));
    }

    @Override
    public void delete() {
        chessBoars.remove(DEFAULT_KEY);
    }
}
