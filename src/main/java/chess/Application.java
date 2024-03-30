package chess;

import chess.controller.GameController;
import chess.domain.GameService;
import chess.repository.BoardDao;
import chess.repository.DatabaseConnectionGenerator;
import chess.repository.GameDao;

public class Application {
    public static void main(String[] args) {
        DatabaseConnectionGenerator connectionGenerator = new DatabaseConnectionGenerator();
        GameDao gameDao = new GameDao(connectionGenerator);
        BoardDao boardDao = new BoardDao(connectionGenerator);

        GameService gameService = new GameService(gameDao, boardDao);
        GameController gameController = new GameController(gameService);

        gameController.run();
    }
}
