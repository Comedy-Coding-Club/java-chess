package repository.dao;

import domain.game.GameState;
import domain.piece.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeChessGameDao extends ChessGameDao {
    public static final int DEFAULT_KEY = 1;
    Map<Integer, Color> colors = new HashMap<>();
    Map<Integer, GameState> gameStates = new HashMap<>();

    @Override
    public void save(Color color, GameState gameState) {
        colors.put(DEFAULT_KEY, color);
        gameStates.put(DEFAULT_KEY, gameState);
    }

    @Override
    public Optional<GameState> findGameStatusById() {
        if (gameStates.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(gameStates.get(DEFAULT_KEY));
    }

    @Override
    public Optional<Color> findColorById() {
        if (colors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(colors.get(DEFAULT_KEY));
    }

    @Override
    public void updateGameStatus(GameState gameState) {
        gameStates.put(DEFAULT_KEY, gameState);
    }

    @Override
    public void updateColor(Color color) {
        colors.put(DEFAULT_KEY, color);
    }

    @Override
    public void delete() {
        gameStates.remove(DEFAULT_KEY);
        colors.remove(DEFAULT_KEY);
    }
}
