package chess.service;

import chess.domain.board.Board;
import chess.domain.chessGame.ChessGame;
import chess.domain.chessGame.InitialGame;
import chess.domain.chessGame.PlayingGame;
import chess.domain.location.Location;
import chess.repository.GameDao;
import chess.repository.BoardDao;
import chess.repository.TransactionManager;
import chess.repository.entity.Game;
import java.util.Optional;
import java.util.function.Supplier;

public class GameService {

    private final GameDao gameDao;
    private final BoardDao boardDao;
    private final TransactionManager transactionManager;

    public GameService(GameDao gameDao, BoardDao boardDao, TransactionManager transactionManager) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
        this.transactionManager = transactionManager;
    }

    public Optional<ChessGame> loadGame() {
        int lastGameId = transactionManager.getData(gameDao::findLastGameId);
        Optional<Board> board = transactionManager.getData(
                connection -> boardDao.findBoardById(connection, lastGameId)
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

    public ChessGame createNewGame() {
        int lastGameId = transactionManager.getData(gameDao::findLastGameId);
        //TODO 방을 여러개 관리하게 되면 이전 데이터를 남겨두고 새로 만들어야 함
        transactionManager.executeTransaction(
                connection -> {
                    boardDao.deleteAllPiecesById(connection, lastGameId);
                    gameDao.deleteGameById(connection, lastGameId);
                });

        int newGameId = lastGameId;
        if (newGameId == 0) {
            newGameId = 1;
        }
        return new InitialGame(newGameId);
    }

    public ChessGame startGame(ChessGame chessGame, Supplier<Boolean> checkRestart) {
        ChessGame startedGame = chessGame.startGame(checkRestart);
        transactionManager.executeTransaction(
                connection -> {
                    gameDao.saveGame(connection, startedGame);
                    boardDao.saveAllPieces(connection, startedGame.getGameId(), startedGame.getBoard());
                });
        return startedGame;
    }

    public ChessGame move(ChessGame chessGame, Location source, Location target) {
        final var movedChessGame = chessGame.move(source, target);
        transactionManager.executeTransaction(
                connection -> {
                    boardDao.deletePieceLocation(connection, movedChessGame.getGameId(), target);
                    boardDao.updatePieceLocation(connection, movedChessGame.getGameId(), source, target);
                    gameDao.updateGame(connection, movedChessGame);
                });
        if (movedChessGame.isEnd()) {
            transactionManager.executeTransaction(
                    connection -> {
                        boardDao.deleteAllPiecesById(connection, movedChessGame.getGameId());
                        gameDao.deleteGameById(connection, movedChessGame.getGameId());
                    });
        }
        return movedChessGame;
    }

    public ChessGame end(ChessGame chessGame) {
        return chessGame.endGame();
    }
}
