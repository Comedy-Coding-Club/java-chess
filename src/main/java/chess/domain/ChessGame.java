package chess.domain;

import chess.domain.board.ChessBoard;
import chess.domain.board.DefaultBoardInitializer;
import chess.domain.dbUtils.DBConnectionUtils;
import chess.domain.dbUtils.GameDao;
import chess.domain.position.Direction;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ChessGame {
    public static final Color START_COLOR = Color.WHITE;
    public static final int DEFAULT_KING_COUNT = 2;

    private final ScoreCalculator scoreCalculator;
    private final ChessBoard chessBoard;
    private final GameDao gameDao; // TODO ChessGame 이 DAO 를 아는게 맞을까? 이러면 DB를 사용할 것이라고 알게되는게 아닌가??
    private Color currentTurn;

    public ChessGame(ChessBoard chessBoard, ScoreCalculator scoreCalculator, GameDao gameDao) {
        this.scoreCalculator = scoreCalculator;
        this.chessBoard = chessBoard;
        this.gameDao = gameDao;
        this.currentTurn = START_COLOR;
    }

    public ChessGame(ChessBoard chessBoard, Color currentTurn) {
        this.scoreCalculator = new ScoreCalculator();
        this.chessBoard = chessBoard;
        this.gameDao = new GameDao(DBConnectionUtils.getConnection()); // TODO ㄱㅊ??
        this.currentTurn = currentTurn;
    }

    public void initNewGame() {
        chessBoard.initNewBoard(DefaultBoardInitializer.initializer());
        gameDao.setTurn(START_COLOR);
        currentTurn = START_COLOR;
    }

    // TODO 턴에 대한 책임을 누가 가질지 명확하게 정해야함
    public void loadGame() {
        currentTurn = gameDao.getCurrentTurn();
    }

    public void handleMove(Position from, Position to) {
        List<Position> movablePositions = generateMovablePositions(from);
        movePiece(movablePositions, from, to);
        this.currentTurn = gameDao.getCurrentTurn();
        handleTurn();
    }

    private void handleTurn() {
        gameDao.setTurn(this.currentTurn.opposite());
        currentTurn = currentTurn.opposite();
    }

    public Map<Color, Double> handleStatus() {
        return scoreCalculator.calculateScore(chessBoard.getBoard());
    }

    public List<Position> generateMovablePositions(Position fromPosition) {
        Piece fromPiece = chessBoard.findPieceByPosition(fromPosition);
        if (fromPiece.isSameTeam(currentTurn.opposite())) {
            throw new IllegalArgumentException("다른 팀의 기물을 움직일 수 없습니다. 현재 턴 : " + currentTurn.name());
        }
        Map<Direction, Deque<Position>> expectedAllPositions = fromPiece.calculateAllDirectionPositions(fromPosition);
        return generateValidPositions(expectedAllPositions, fromPiece);
    }

    private List<Position> generateValidPositions(Map<Direction, Deque<Position>> expectedAllPositions, Piece fromPiece) {
        return expectedAllPositions.keySet()
                .stream()
                .map(direction -> filterInvalidPositions(expectedAllPositions.get(direction), direction, fromPiece))
                .flatMap(List::stream)
                .toList();
    }

    private List<Position> filterInvalidPositions(Deque<Position> expectedPositions, Direction direction, Piece piece) {
        List<Position> result = new ArrayList<>();
        Position currentPosition = expectedPositions.poll();
        while (isEmptySpace(direction, piece, currentPosition)) {
            result.add(currentPosition);
            currentPosition = expectedPositions.poll();
        }
        if (isEnemySpace(direction, piece, currentPosition)) {
            result.add(currentPosition);
        }
        return result;
    }

    private boolean isEmptySpace(Direction direction, Piece piece, Position currentPosition) {
        return currentPosition != null
                && piece.isForward(direction)
                && chessBoard.isEmptySpace(currentPosition);
    }

    private boolean isEnemySpace(Direction direction, Piece piece, Position currentPosition) {
        return currentPosition != null
                && piece.isAttack(direction)
                && chessBoard.hasPiece(currentPosition)
                && !chessBoard.findPieceByPosition(currentPosition).isSameTeam(piece);
    }

    public void movePiece(List<Position> movablePositions, Position from, Position to) {
        if (movablePositions.contains(to)) {
            chessBoard.movePiece(from, to);
            return;

        }
        throw new IllegalArgumentException("해당 기물이 움직일 수 있는 위치가 아닙니다.");
    }

    public boolean isGameOver() {
        return !chessBoard.hasTwoKing();
    }

    public Color calculateWinner() {
        if (isGameOver()) {
            return currentTurn.opposite();
        }
        return calculateWinnerByScore();
    }

    public void handleEndGame() {
        if (isGameOver()) {
            chessBoard.clearBoard();
        }
    }

    private Color calculateWinnerByScore() {
        Map<Color, Double> scores = scoreCalculator.calculateScore(chessBoard.getBoard());
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
        return chessBoard.isFirstGame();
    }

    public Map<Position, Piece> getBoard() {
        return chessBoard.getBoard();
    }
}
