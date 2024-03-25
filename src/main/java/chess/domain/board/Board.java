package chess.domain.board;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.implement.Bishop;
import chess.domain.piece.implement.BlackPawn;
import chess.domain.piece.implement.King;
import chess.domain.piece.implement.Knight;
import chess.domain.piece.implement.Queen;
import chess.domain.piece.implement.Rook;
import chess.domain.piece.implement.WhitePawn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Location, Piece> board;

    public Board() {
        this(initialBoard());
    }

    public Board(Map<Location, Piece> board) {
        this.board = board;
    }

    private static Map<Location, Piece> initialBoard() {
        Map<Location, Piece> initialBoard = new HashMap<>();
        initialPawnSetting(initialBoard);
        initialRookSetting(initialBoard);
        initialKnightSetting(initialBoard);
        initialBishopSetting(initialBoard);
        initialQueenSetting(initialBoard);
        initialKingSetting(initialBoard);
        return initialBoard;
    }

    private static void initialPawnSetting(Map<Location, Piece> board) {
        for (Column value : Column.values()) {
            board.put(new Location(value, Row.TWO), new WhitePawn());
            board.put(new Location(value, Row.SEVEN), new BlackPawn());
        }
    }

    private static void initialRookSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.A, Row.ONE), new Rook(Color.WHITE));
        board.put(new Location(Column.A, Row.EIGHT), new Rook(Color.BLACK));
        board.put(new Location(Column.H, Row.ONE), new Rook(Color.WHITE));
        board.put(new Location(Column.H, Row.EIGHT), new Rook(Color.BLACK));
    }

    private static void initialKnightSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.B, Row.ONE), new Knight(Color.WHITE));
        board.put(new Location(Column.B, Row.EIGHT), new Knight(Color.BLACK));
        board.put(new Location(Column.G, Row.ONE), new Knight(Color.WHITE));
        board.put(new Location(Column.G, Row.EIGHT), new Knight(Color.BLACK));
    }

    private static void initialBishopSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.C, Row.ONE), new Bishop(Color.WHITE));
        board.put(new Location(Column.C, Row.EIGHT), new Bishop(Color.BLACK));
        board.put(new Location(Column.F, Row.ONE), new Bishop(Color.WHITE));
        board.put(new Location(Column.F, Row.EIGHT), new Bishop(Color.BLACK));
    }

    private static void initialQueenSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.D, Row.ONE), new Queen(Color.WHITE));
        board.put(new Location(Column.D, Row.EIGHT), new Queen(Color.BLACK));
    }

    private static void initialKingSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.E, Row.ONE), new King(Color.WHITE));
        board.put(new Location(Column.E, Row.EIGHT), new King(Color.BLACK));
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
        selectedPiece.move();
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

    public Map<Location, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
