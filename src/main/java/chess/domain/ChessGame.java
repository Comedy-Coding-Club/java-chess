package chess.domain;

import chess.domain.board.MemoryChessBoard;
import chess.domain.position.Direction;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ChessGame { // TODO 객체 분리 고민
    public static final int KING_COUNT = 2;
    private static final Color START_COLOR = Color.WHITE;

    private final MemoryChessBoard memoryChessBoard;
    private final ScoreCalculator scoreCalculator;
    private Color currentTurn;

    public ChessGame(MemoryChessBoard memoryChessBoard, ScoreCalculator scoreCalculator) {
        this.memoryChessBoard = memoryChessBoard;
        this.scoreCalculator = scoreCalculator;
        this.currentTurn = START_COLOR;
    }

    public ChessGame(MemoryChessBoard memoryChessBoard, Color currentTurn) {
        this.memoryChessBoard = memoryChessBoard;
        this.scoreCalculator = new ScoreCalculator();
        this.currentTurn = currentTurn;
    }

    public void handleMove(Position from, Position to) {
        List<Position> movablePositions = generateMovablePositions(from);
        movePiece(movablePositions, from, to);
        this.currentTurn = this.currentTurn.opposite();
    }

    public Map<Color, Double> handleStatus() {
        return scoreCalculator.calculateScore(memoryChessBoard.getBoard());
    }

    public List<Position> generateMovablePositions(Position fromPosition) {
        Piece fromPiece = memoryChessBoard.findPieceByPosition(fromPosition);
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
                && memoryChessBoard.isEmptySpace(currentPosition);
    }

    private boolean isEnemySpace(Direction direction, Piece piece, Position currentPosition) {
        return currentPosition != null
                && piece.isAttack(direction)
                && memoryChessBoard.hasPiece(currentPosition)
                && !memoryChessBoard.findPieceByPosition(currentPosition).isSameTeam(piece);
    }

    public void movePiece(List<Position> movablePositions, Position from, Position to) {
        if (movablePositions.contains(to)) {
            memoryChessBoard.movePiece(from, to);
            return;

        }
        throw new IllegalArgumentException("해당 기물이 움직일 수 있는 위치가 아닙니다.");
    }

    public boolean isEnd() {
        return !memoryChessBoard.hasKing(KING_COUNT);
    }

    public Color calculateWinner() {
        if (isEnd()) {
            return currentTurn.opposite();
        }
        return calculateWinnerByScore();
    }

    private Color calculateWinnerByScore() {
        Map<Color, Double> scores = scoreCalculator.calculateScore(memoryChessBoard.getBoard());
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

    public MemoryChessBoard getBoard() {
        return memoryChessBoard;
    }
}
