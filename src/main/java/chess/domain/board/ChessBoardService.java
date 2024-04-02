package chess.domain.board;

import chess.domain.ChessGameService;
import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.position.Direction;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * 체스 보드에 관련된 책임
 */
public class ChessBoardService {

    private static final int DEFAULT_KING_COUNT = 2;

    private final BoardRepository boardRepository;

    public ChessBoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void initNewBoard(Map<Position, Piece> board) {
        clearBoard();
        board.forEach(boardRepository::placePiece);
    }

    public void clearBoard() {
        boardRepository.clearBoard();
    }

    public void movePiece(Position from, Position to) {
        Piece piece = boardRepository.findPieceByPosition(from);
        boardRepository.removePiece(from);
        boardRepository.placePiece(to, piece);
    }

    public List<Position> generateMovablePositions(Position fromPosition, Color currentTurn) {
        Piece fromPiece = findPieceByPosition(fromPosition);
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
                && isEmptySpace(currentPosition);
    }

    private boolean isEnemySpace(Direction direction, Piece piece, Position currentPosition) {
        return currentPosition != null
                && piece.isAttack(direction)
                && hasPiece(currentPosition)
                && !findPieceByPosition(currentPosition).isSameTeam(piece);
    }

    public Piece findPieceByPosition(Position position) {
        return boardRepository.findPieceByPosition(position);
    }

    private boolean hasPiece(Position position) {
        return boardRepository.hasPiece(position);
    }

    public boolean isEmptySpace(Position position) {
        return !hasPiece(position);
    }

    public boolean hasTwoKing() {
        Map<Position, Piece> board = boardRepository.getBoard();
        int kingCount = (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
        return kingCount == DEFAULT_KING_COUNT;
    }

    public boolean isFirstGame() {
        return getBoard().isEmpty();
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(boardRepository.getBoard());
    }
}
