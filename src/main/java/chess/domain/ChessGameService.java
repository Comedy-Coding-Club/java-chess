package chess.domain;

import chess.db.BoardDao;
import chess.db.DBBoardRepository;
import chess.db.DBConnectionUtils;
import chess.db.GameDao;
import chess.domain.board.ChessBoardService;
import chess.domain.board.DefaultBoardInitializer;
import chess.domain.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ChessGameService {

    private static final Color START_COLOR = Color.WHITE;
    private static final Connection connection = DBConnectionUtils.getConnection();

    private final ChessBoardService chessBoardService;
    private final ScoreCalculator scoreCalculator;
    private final GameDao gameDao;

    public ChessGameService(ScoreCalculator scoreCalculator) {
        this.chessBoardService = new ChessBoardService(new DBBoardRepository(new BoardDao(connection)));
        this.scoreCalculator = scoreCalculator;
        this.gameDao = new GameDao(connection);
    }

    public void initNewGame() {
        chessBoardService.initNewBoard(DefaultBoardInitializer.initializer());
        gameDao.setTurn(START_COLOR);
    }

    public void handleMove(Position from, Position to) {
        List<Position> movablePositions = chessBoardService.generateMovablePositions(from, gameDao.getCurrentTurn());
        movePiece(movablePositions, from, to);
        handleTurn();
    }

    public void movePiece(List<Position> movablePositions, Position from, Position to) {
        if (movablePositions.contains(to)) {
            chessBoardService.movePiece(from, to);
            return;

        }
        throw new IllegalArgumentException("해당 기물이 움직일 수 있는 위치가 아닙니다.");
    }

    private void handleTurn() {
        gameDao.setTurn(gameDao.getCurrentTurn().opposite());
    }

    public Map<Color, Double> handleStatus() {
        return scoreCalculator.calculateScore(chessBoardService.getBoard());
    }

    public Color calculateWinner() {
        if (isGameOver()) {
            return gameDao.getCurrentTurn().opposite();
        }
        return calculateWinnerByScore();
    }

    public void handleEndGame() {
        if (isGameOver()) {
            chessBoardService.clearBoard();
            handleConnectionClose();
        }
    }

    private void handleConnectionClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isGameOver() {
        return !chessBoardService.hasTwoKing();
    }

    private Color calculateWinnerByScore() {
        Map<Color, Double> scores = scoreCalculator.calculateScore(chessBoardService.getBoard());
        Double blackScore = scores.get(Color.BLACK);
        Double whiteScore = scores.get(Color.WHITE);
        if (blackScore > whiteScore) {
            return Color.BLACK;
        }
        if (blackScore < whiteScore) {
            return Color.WHITE;
        }
        return Color.NONE;
    }

    public boolean isFirstGame() {
        return chessBoardService.isFirstGame();
    }

    public Map<Position, Piece> getBoard() {
        return chessBoardService.getBoard();
    }
}
