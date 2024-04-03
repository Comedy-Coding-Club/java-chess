package chess;

import chess.controller.GameController;
import chess.repository.DatabaseConnectionGenerator;
import chess.repository.GameDao;
import chess.repository.BoardDao;
import chess.repository.PropertiesFile;
import chess.repository.TransactionManager;
import chess.service.GameService;

public class Application {
    public static void main(String[] args) {
        PropertiesFile config = PropertiesFile.of("database.properties");
        DatabaseConnectionGenerator connectionGenerator = new DatabaseConnectionGenerator(config);
        GameDao gameDao = new GameDao();
        BoardDao boardDao = new BoardDao();

        TransactionManager transactionManager = new TransactionManager(connectionGenerator);
        GameService gameService = new GameService(gameDao, boardDao, transactionManager);
        GameController gameController = new GameController(gameService);

        gameController.run();
    }
}
