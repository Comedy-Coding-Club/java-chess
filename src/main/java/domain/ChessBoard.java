package domain;

import domain.piece.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ChessBoard {

    private static final Map<File, Piece> BLACK_PIECE_TYPE_ORDERS = Map.of(
            File.A, new Rook(Camp.BLACK), File.B, new Knight(Camp.BLACK),
            File.C, new Bishop(Camp.BLACK), File.D, new Queen(Camp.BLACK),
            File.E, new King(Camp.BLACK), File.F, new Bishop(Camp.BLACK),
            File.G, new Knight(Camp.BLACK), File.H, new Rook(Camp.BLACK)
    );
    private static final Map<File, Piece> WHITE_PIECE_TYPE_ORDERS = Map.of(
            File.A, new Rook(Camp.WHITE), File.B, new Knight(Camp.WHITE),
            File.C, new Bishop(Camp.WHITE), File.D, new Queen(Camp.WHITE),
            File.E, new King(Camp.WHITE), File.F, new Bishop(Camp.WHITE),
            File.G, new Knight(Camp.WHITE), File.H, new Rook(Camp.WHITE)
    );

    private final Map<Square, Piece> pieceSquares;
    private Camp camp;

    public ChessBoard() {
        this.pieceSquares = new HashMap<>();
    }

    private ChessBoard(final Map<Square, Piece> pieceSquares) {
        this.pieceSquares = pieceSquares;
        this.camp = Camp.WHITE;
    }

    public static ChessBoard create() {
        final Map<Square, Piece> chessTable = new HashMap<>();

        for (final File file : File.values()) {
            chessTable.put(new Square(Rank.SEVEN, file), new Pawn(Camp.BLACK));
            chessTable.put(new Square(Rank.TWO, file), new Pawn(Camp.WHITE));
            chessTable.put(new Square(Rank.EIGHT, file), BLACK_PIECE_TYPE_ORDERS.get(file));
            chessTable.put(new Square(Rank.ONE, file), WHITE_PIECE_TYPE_ORDERS.get(file));
        }

        return new ChessBoard(chessTable);
    }

    public void move(final Square source, final Square target) {
        validateEmptySource(source);

        final Piece sourcePiece = pieceSquares.get(source);

        validateCamp(sourcePiece);

        if (pieceSquares.containsKey(target)) {
            validateAttack(source, target, sourcePiece);
        } else {
            validateMove(source, target, sourcePiece);
        }

        validateBlocking(source, target);

        pieceSquares.put(target, sourcePiece);
        pieceSquares.remove(source);
        camp = camp.turnAlternation();
    }

    private void validateEmptySource(final Square source) {
        if (!pieceSquares.containsKey(source)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
    }

    private void validateCamp(final Piece sourcePiece) {
        if (sourcePiece.isOppositeCamp(camp)) {
            throw new IllegalArgumentException("자기 말이 아닙니다.");
        }
    }

    private void validateAttack(final Square source, final Square target, final Piece sourcePiece) {
        final Piece targetPiece = pieceSquares.get(target);
        if (targetPiece.isSameCamp(sourcePiece)) {
            throw new IllegalArgumentException("갈 수 없는 경로입니다.");
        }
        if (sourcePiece.canNotAttack(source, target)) {
            throw new IllegalArgumentException("공격할 수 없는 경로입니다.");
        }
    }

    private void validateMove(final Square source, final Square target, final Piece sourcePiece) {
        if (sourcePiece.canNotMove(source, target)) {
            throw new IllegalArgumentException("갈 수 없는 경로입니다.");
        }
    }

    private void validateBlocking(final Square source, final Square target) {
        if (isBlocked(source, target)) {
            throw new IllegalArgumentException("갈 수 없는 경로입니다.");
        }
    }

    private boolean isBlocked(final Square source, final Square target) {
        final ChessVector chessVector = target.calculateVector(source);

        final ChessVector direction = chessVector.scaleDown();
        final long count = chessVector.divide(direction);

        return Stream.iterate(source.next(direction), square -> square.next(direction))
                .limit(count)
                .filter(square -> !square.equals(target))
                .anyMatch(pieceSquares::containsKey);
    }

    public Map<Square, Piece> getPieceSquares() {
        return Collections.unmodifiableMap(pieceSquares);
    }
}
