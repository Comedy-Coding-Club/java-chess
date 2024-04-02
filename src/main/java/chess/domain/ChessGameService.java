package chess.domain;

import chess.db.DBConnectionUtils;
import chess.db.GameDao;
import chess.domain.board.ChessBoardService;
import chess.domain.board.DefaultBoardInitializer;
import chess.domain.position.Direction;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ChessGameService {

    private static final Color START_COLOR = Color.WHITE;

    private final ScoreCalculator scoreCalculator;
    private final ChessBoardService chessBoardService;
    private final GameDao gameDao;

    public ChessGameService(ChessBoardService chessBoardService, ScoreCalculator scoreCalculator, GameDao gameDao) {
        this.scoreCalculator = scoreCalculator;
        this.chessBoardService = chessBoardService;
        this.gameDao = gameDao;
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
