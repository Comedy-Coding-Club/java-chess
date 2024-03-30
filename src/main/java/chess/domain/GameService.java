package chess.domain;

import chess.domain.board.Board;
import chess.domain.chessGame.ChessGame;
import chess.domain.chessGame.PlayingGame;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.repository.BoardDao;
import chess.repository.GameDao;
import java.util.Optional;

public class GameService {

    private final GameDao gameDao;
    private final BoardDao boardDao;

    public GameService(GameDao gameDao, BoardDao boardDao) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
    }

    public Optional<ChessGame> loadGame() {
        Optional<Board> board = boardDao.findBoard();
        if (board.isEmpty()) {
            return Optional.empty();
        }

        Optional<Color> turn = gameDao.findTurn();
        if (turn.isEmpty()) {
            throw new IllegalStateException("DB에 저장된 턴이 없습니다.");
        }
        return Optional.of(new PlayingGame(board.get(), turn.get()));
    }

    public ChessGame move(ChessGame chessGame, Location source, Location target) {
        chessGame = chessGame.move(source, target);
        if (chessGame.isEnd()) {
            boardDao.deleteBoard();
            gameDao.deleteTurn();
        }
        return chessGame;
    }

    public ChessGame end(ChessGame chessGame) {
        boardDao.saveBoard(chessGame.getBoard());
        gameDao.saveTurn(chessGame.getTurn());
        return chessGame.endGame();
    }
}
