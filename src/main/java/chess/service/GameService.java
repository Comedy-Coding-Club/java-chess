package chess.service;

import chess.domain.board.Board;
import chess.domain.chessGame.ChessGame;
import chess.domain.chessGame.InitialGame;
import chess.domain.chessGame.PlayingGame;
import chess.domain.location.Location;
import chess.domain.piece.Piece;
import chess.repository.dao.BoardDao;
import chess.repository.dao.GameDao;
import chess.repository.dao.PieceDao;
import chess.repository.TransactionManager;
import chess.repository.entity.Game;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;

public class GameService {

    private final GameDao gameDao;
    private final BoardDao boardDao;
    private final PieceDao pieceDao;
    private final TransactionManager transactionManager;

    public GameService(GameDao gameDao, BoardDao boardDao, PieceDao pieceDao, TransactionManager transactionManager) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
        this.transactionManager = transactionManager;
    }

    public Optional<ChessGame> loadGame() {
        int lastGameId = transactionManager.getData(gameDao::findLastGameId);
        Optional<Board> board = transactionManager.getData(
                connection -> createBoard(connection, lastGameId)
        );
        if (board.isEmpty()) {
            return Optional.empty();
        }

        Optional<Game> gameEntity = transactionManager.getData(
                connection -> gameDao.findGameById(connection, lastGameId)
        );
        if (gameEntity.isEmpty()) {
            throw new IllegalStateException("DB에 저장된 턴이 없습니다.");
        }
        Game game = gameEntity.get();
        return Optional.of(new PlayingGame(game.getGameId(), board.get(), game.getTurn()));
    }

    private Optional<Board> createBoard(Connection connection, int lastGameId) throws SQLException {
        Map<Location, Integer> pieceIdsWithLocation = boardDao.findBoardById(connection, lastGameId);
        if (pieceIdsWithLocation.isEmpty()) {
            return Optional.empty();
        }
        Board board = generateBoard(connection, pieceIdsWithLocation);
        return Optional.of(board);
    }

    private Board generateBoard(Connection connection, Map<Location, Integer> pieceLocations)
            throws SQLException {
        Map<Location, Piece> piecesWithLocation = new HashMap<>();
        for (Entry<Location, Integer> locationIntegerEntry : pieceLocations.entrySet()) {
            Location location = locationIntegerEntry.getKey();
            Integer pieceId = locationIntegerEntry.getValue();
            Piece piece = pieceDao.findPieceById(connection, pieceId);
            piecesWithLocation.put(location, piece);
        }
        return new Board(piecesWithLocation);
    }

    public ChessGame createNewGame() {
        int lastGameId = transactionManager.getData(gameDao::findLastGameId);
        //TODO 방을 여러개 관리하게 되면 이전 데이터를 남겨두고 새로 만들어야 함
        transactionManager.executeTransaction(
                connection -> deleteGame(connection, lastGameId)
        );

        int newGameId = lastGameId;
        if (newGameId == 0) {
            newGameId = 1;
        }
        return new InitialGame(newGameId);
    }

    public ChessGame startGame(ChessGame chessGame, Supplier<Boolean> checkRestart) {
        ChessGame startedGame = chessGame.startGame(checkRestart);
        transactionManager.executeTransaction(
                connection -> saveGame(connection, startedGame));
        return startedGame;
    }

    private void saveGame(Connection connection, ChessGame game) throws SQLException {
        gameDao.saveGame(connection, game);
        Board board = game.getBoard();
        for (Entry<Location, Piece> locationPieceEntry : board.getBoard().entrySet()) {
            Piece piece = locationPieceEntry.getValue();
            Location location = locationPieceEntry.getKey();

            int pieceId = pieceDao.getPieceId(connection, piece);
            boardDao.savePieceLocation(connection, game.getGameId(), location, pieceId);
        }
    }

    public ChessGame move(ChessGame chessGame, Location source, Location target) {
        final var movedChessGame = chessGame.move(source, target);
        transactionManager.executeTransaction(
                connection -> updateMovementData(connection, source, target, movedChessGame));
        if (movedChessGame.isEnd()) {
            transactionManager.executeTransaction(
                    connection -> deleteGame(connection, movedChessGame.getGameId()));
        }
        return movedChessGame;
    }

    private void updateMovementData(Connection connection, Location source, Location target, ChessGame movedChessGame)
            throws SQLException {
        boardDao.deletePieceLocation(connection, movedChessGame.getGameId(), target);
        boardDao.updatePieceLocation(connection, movedChessGame.getGameId(), source, target);
        gameDao.updateGame(connection, movedChessGame);
    }

    private void deleteGame(Connection connection, int gameId) throws SQLException {
        boardDao.deleteAllPiecesById(connection, gameId);
        gameDao.deleteGameById(connection, gameId);
    }

    public ChessGame end(ChessGame chessGame) {
        return chessGame.endGame();
    }
}
