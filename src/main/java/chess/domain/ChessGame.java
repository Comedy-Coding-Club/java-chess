package chess.domain;

import chess.view.CommendDto;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ChessGame {

    public final Board board;
    public ChessGame(Board board) {
        this.board = board;
    }

    public void handleMove(CommendDto commendDto, Color currentTurn) {
        Position from = Position.from(commendDto.from());
        Position to = Position.from(commendDto.to());
        List<Position> movablePositions = generateMovablePositions(from, currentTurn);
        movePiece(movablePositions, from, to);
    }

    public List<Position> generateMovablePositions(Position fromPosition, Color currentTurn) {
        Piece piece = board.findPieceByPosition(fromPosition);
        if (piece.isSameTeam(currentTurn.opposite())) {
            throw new IllegalArgumentException("다른 팀의 기물을 움직일 수 없습니다.");
        }
        Map<Direction, Deque<Position>> expectedAllPositions = piece.calculateAllDirectionPositions(fromPosition);
        if (piece.isPawn()) {
            return generateValidPositionsWithPawn(expectedAllPositions, piece);
        }
        return generateValidPositions(expectedAllPositions, piece);
    }

    private List<Position> generateValidPositions(Map<Direction, Deque<Position>> expectedAllPositions, Piece piece) {
        return expectedAllPositions.keySet()
                .stream()
                .map(expectedAllPositions::get)
                .map(expectedPositions -> filterInvalidPositions(expectedPositions, piece))
                .flatMap(List::stream)
                .toList();
    }

    private List<Position> filterInvalidPositions(Deque<Position> expectedPositions, Piece piece) {
        List<Position> result = new ArrayList<>();
        while (isNotEmpty(expectedPositions) && board.isEmptySpace(expectedPositions.peek())) {
            Position position = expectedPositions.poll();
            result.add(position);
        }
        addObstaclePosition(result, expectedPositions, piece);
        return result;
    }

    private void addObstaclePosition(List<Position> result, Queue<Position> expectedPositions, Piece piece) {
        Position last = expectedPositions.poll();
        if (last != null && board.hasPiece(last) && !board.findPieceByPosition(last).isSameTeam(piece)) {
            result.add(last);
        }
    }

    private boolean isNotEmpty(Queue<Position> expectedPositions) {
        return !expectedPositions.isEmpty();
    }

    private List<Position> generateValidPositionsWithPawn(Map<Direction, Deque<Position>> expectedAllPositions, Piece piece) {
        return expectedAllPositions.keySet()
                .stream()
                .map(direction -> filterInvalidPositionsWithPawn(expectedAllPositions.get(direction), direction, piece))
                .flatMap(List::stream)
                .toList();
    }

    private List<Position> filterInvalidPositionsWithPawn(Deque<Position> expectedPositions, Direction direction, Piece piece) {
        if (piece.isBlack()) {
            return handleBlackPawn(expectedPositions, direction, piece);
        }
        return handleWhitePawn(expectedPositions, direction, piece);
    }

    private List<Position> handleBlackPawn(Deque<Position> expectedPositions, Direction direction, Piece piece) {
        List<Position> result = new ArrayList<>();
        while (isNotEmpty(expectedPositions) && board.isEmptySpace(expectedPositions.peek()) && direction == Direction.S) {
            Position position = expectedPositions.poll();
            result.add(position);
        }

        if (direction == Direction.SE || direction == Direction.SW) {
            addObstaclePosition(result, expectedPositions, piece);
        }
        return result;
    }

    private List<Position> handleWhitePawn(Deque<Position> expectedPositions, Direction direction, Piece piece) {
        List<Position> result = new ArrayList<>();
        while (isNotEmpty(expectedPositions) && board.isEmptySpace(expectedPositions.peek()) && direction == Direction.N) {
            Position position = expectedPositions.poll();
            result.add(position);
        }

        if (direction == Direction.NE || direction == Direction.NW) {
            addObstaclePosition(result, expectedPositions, piece);
        }
        return result;
    }

    public void movePiece(List<Position> positions, Position from, Position to) {
        if (positions.contains(to)) {
            board.movePiece(from, to);
            return;
        }
        throw new IllegalArgumentException("기물을 해당 위치로 이동시킬 수 없습니다.");
    }

    public Board getBoard() {
        return board;
    }
}
