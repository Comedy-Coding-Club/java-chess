package chess.service;

import chess.repository.BoardDao;
import chess.repository.GameDao;
import chess.repository.entity.Game;
import chess.service.domain.board.Board;
import chess.service.domain.chessGame.ChessGame;
import chess.service.domain.chessGame.InitialGame;
import chess.service.domain.chessGame.PlayingGame;
import chess.service.domain.location.Location;
import java.util.Optional;

public class GameService {

    private final GameDao gameDao;
    private final BoardDao boardDao;

    public GameService(GameDao gameDao, BoardDao boardDao) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
    }

    public Optional<ChessGame> loadGame() {
        int lastGameId = gameDao.findLastGameId();
        Optional<Board> board = boardDao.findBoardById(lastGameId);
        if (board.isEmpty()) {
            return Optional.empty();
        }

        Optional<Game> gameEntity = gameDao.findGameById(lastGameId);
        if (gameEntity.isEmpty()) {
            throw new IllegalStateException("DB에 저장된 턴이 없습니다.");
        }
        Game game = gameEntity.get();
        return Optional.of(new PlayingGame(game.getGameId(), board.get(), game.getTurn()));
    }

    public ChessGame createNewGame() {
        int lastGameId = gameDao.findLastGameId();
        //TODO 방을 여러개 관리하게 되면 이전 데이터를 남겨두고 새로 만들어야 함
        boardDao.deleteBoardById(lastGameId);
        gameDao.deleteGameById(lastGameId);
        if (lastGameId == 0) {
            lastGameId = 1;
        }
        return new InitialGame(lastGameId);
    }

    public ChessGame move(ChessGame chessGame, Location source, Location target) {
        chessGame = chessGame.move(source, target);
        if (chessGame.isEnd()) {
            boardDao.deleteBoardById(chessGame.getGameId());
            gameDao.deleteGameById(chessGame.getGameId());
        }
        return chessGame;
    }

    public void save(ChessGame chessGame) {
        Game game = new Game(chessGame.getGameId(), chessGame.getTurn());
        boardDao.deleteBoardById(game.getGameId());
        gameDao.deleteGameById(game.getGameId());
        gameDao.saveGame(game);
        boardDao.saveBoard(game.getGameId(), chessGame.getBoard());
    }

    public ChessGame end(ChessGame chessGame) {
        return chessGame.endGame();
    }
}
