package chess;

import chess.controller.GameController;
import chess.repository.dao.BoardDao;
import chess.repository.DatabaseConnectionGenerator;
import chess.repository.dao.GameDao;
import chess.repository.dao.PieceDao;
import chess.repository.PropertiesFile;
import chess.repository.TransactionManager;
import chess.service.GameService;

public class Application {
    public static void main(String[] args) {
        PropertiesFile config = PropertiesFile.of("database.properties");
        DatabaseConnectionGenerator connectionGenerator = new DatabaseConnectionGenerator(config);
        GameDao gameDao = new GameDao();
        BoardDao boardDao = new BoardDao();
        PieceDao pieceDao = new PieceDao();

        TransactionManager transactionManager = new TransactionManager(connectionGenerator);
        GameService gameService = new GameService(gameDao, boardDao, pieceDao, transactionManager);
        GameController gameController = new GameController(gameService);

        gameController.run();
    }
}
