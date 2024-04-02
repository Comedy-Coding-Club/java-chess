package chess;

import chess.controller.GameController;
import chess.repository.PieceDao;
import chess.repository.DatabaseConnectionGenerator;
import chess.repository.GameDao;
import chess.repository.PropertiesFile;
import chess.service.GameService;

public class Application {
    public static void main(String[] args) {
        PropertiesFile config = PropertiesFile.of("database.properties");
        DatabaseConnectionGenerator connectionGenerator = new DatabaseConnectionGenerator(config);
        GameDao gameDao = new GameDao(connectionGenerator);
        PieceDao pieceDao = new PieceDao(connectionGenerator);

        GameService gameService = new GameService(gameDao, pieceDao);
        GameController gameController = new GameController(gameService);

        gameController.run();
    }
}
