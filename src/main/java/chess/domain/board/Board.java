package chess.domain.board;

import chess.domain.location.File;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Location, Piece> board;

    public Board(Map<Location, Piece> board) {
        this.board = board;
    }

    public static Board createInitialBoard() {
        return InitialBoardBuilder.build();
    }

    public static Board createEmptyBoard() {
        return new Board(Map.of());
    }

    public void move(Location source, Location target, Color turnPlayer) {
        Piece selectedPiece = findPieceAt(source);
        if (!selectedPiece.isColor(turnPlayer)) {
            throw new IllegalArgumentException("본인 기물만 움직일 수 있습니다.");
        }

        Path path = createPath(source, target);
        if (!selectedPiece.canMove(path)) {
            throw new IllegalArgumentException("유효하지 않은 움직임입니다.");
        }
        selectedPiece = selectedPiece.move();
        updateLocation(source, target, selectedPiece);
    }

    private void updateLocation(Location source, Location target, Piece movingPiece) {
        board.remove(source);
        board.put(target, movingPiece);
    }

    private Path createPath(Location source, Location target) {
        List<Direction> directions = Direction.createDirections(source, target);
        List<LocationState> locationStates = createPathState(source, directions);
        return Path.of(directions, locationStates);
    }

    private List<LocationState> createPathState(Location source, List<Direction> directions) {
        Piece movingPiece = findPieceAt(source);
        Location movedLocation = source.copy();
        List<LocationState> locationStates = new ArrayList<>();
        for (Direction direction : directions) {
            movedLocation = movedLocation.move(direction);
            locationStates.add(findLocationStates(movingPiece, movedLocation));
        }
        return locationStates;
    }

    private LocationState findLocationStates(Piece movingPiece, Location current) {
        Piece locatedPiece = board.get(current);
        if (locatedPiece == null) {
            return LocationState.EMPTY;
        }
        if (movingPiece.isAlly(locatedPiece)) {
            return LocationState.ALLY;
        }
        return LocationState.ENEMY;
    }

    private Piece findPieceAt(Location source) {
        Piece piece = board.get(source);
        if (piece == null) {
            throw new IllegalArgumentException("말이 존재하지 않습니다.");
        }
        return piece;
    }

    public boolean isKingDead() {
        return board.values().stream()
                .filter(piece -> piece.isTypeOf(PieceType.KING))
                .count() != 2;
    }

    public Score calculateScore(Color color) {
        Score defaultScoreSum = calculateDefaultScore(color);
        Score sameRankPawnScore = calculateSameRankPawnScore(color);
        return defaultScoreSum.subtract(sameRankPawnScore);
    }

    private Score calculateDefaultScore(Color color) {
        return board.values().stream()
                .filter(piece -> piece.isColor(color))
                .map(Piece::getPieceScore)
                .reduce(Score::add)
                .orElse(Score.ZERO);
    }

    private Score calculateSameRankPawnScore(Color color) {
        Score sameRankPawnScore = new Score(0.5);
        Map<File, Long> countPawnLocationByFile = groupingPawnLocationByRank(color);
        int sameRankPawnCount = countPawnLocationByFile.values().stream()
                .mapToInt(Long::intValue)
                .filter(count -> count != 1)
                .sum();
        return sameRankPawnScore.multiply(sameRankPawnCount);
    }

    private Map<File, Long> groupingPawnLocationByRank(Color color) {
        return board.keySet().stream()
                .filter(location -> hasPawn(location))
                .filter(location -> hasPieceColoredOf(location, color))
                .collect(Collectors.groupingBy(
                        Location::getFile, Collectors.counting()
                ));
    }

    private boolean hasPawn(Location location) {
        return board.get(location).isTypeOf(PieceType.PAWN);
    }

    private boolean hasPieceColoredOf(Location location, Color color) {
        return board.get(location).isColor(color);
    }

    public Map<Location, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Color getWinner() {
        List<Piece> aliveKings = board.values().stream()
                .filter(piece -> piece.isTypeOf(PieceType.KING))
                .toList();

        if (aliveKings.size() != 1) {
            throw new IllegalStateException("승부가 나지 않았습니다.");
        }

        return aliveKings.get(0).getColor();
    }
}
