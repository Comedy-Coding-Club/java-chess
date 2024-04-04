package repository;

import domain.game.GameState;
import domain.piece.Color;

public interface ChessGameRepository {

    void save(Color color, GameState gameState);

    GameState findGameStatusById();

    Color findColorById();

    void updateGameStatus(GameState gameState);

    void updateColor(Color color);

    void delete();
}
