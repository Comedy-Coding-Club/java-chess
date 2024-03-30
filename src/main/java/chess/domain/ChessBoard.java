package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.domain.piece.type.Empty;
import chess.entity.SquareEntity;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChessBoard {

    private final Map<Position, Piece> pieces;

    public ChessBoard(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static ChessBoard fromEntity(final List<SquareEntity> chessBoard) {
        Map<Position, Piece> pieces = chessBoard.stream()
                .collect(Collectors.toMap(
                        squareEntity -> Position.from(squareEntity.position()),
                        squareEntity -> Piece.of(squareEntity.pieceType(), squareEntity.color())
                ));
        return new ChessBoard(pieces);
    }

    public boolean isPieceColor(final String sourcePosition, final Color color) {
        final Piece sourcePiece = findPieceBy(Position.from(sourcePosition));
        return sourcePiece.isMyColor(color);
    }

    public void move(final String sourcePosition, final String targetPosition) {
        move(Position.from(sourcePosition), Position.from(targetPosition));
    }

    public void move(final Position sourcePosition, final Position targetPosition) {
        final Piece sourcePiece = findPieceBy(sourcePosition);

        Set<Position> positions = sourcePiece.getPositions(sourcePosition, pieces);

        if (positions.contains(targetPosition)) {
            movePiece(sourcePosition, targetPosition);
            return;
        }

        throw new IllegalArgumentException("[ERROR] 이동할 수 없는 위치입니다.");
    }

    public Piece findPieceBy(final Position input) {
        if (isPieceExist(input)) {
            return pieces.get(input);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
    }

    public boolean doesKingDead() {
        int kingCount = (int) pieces.values().stream()
                .filter(piece -> piece.isType(PieceType.KING))
                .count();
        return kingCount != 2;
    }

    private boolean isPieceExist(final Position input) {
        return !pieces.get(input).isClass(Empty.class);
    }

    private void movePiece(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = pieces.get(sourcePosition);

        pieces.put(targetPosition, sourcePiece);
        pieces.put(sourcePosition, new Empty());
    }

    public Map<Color, Double> getScores() {
        Map<Color, Double> scores = new EnumMap<>(Color.class);
        scores.put(Color.WHITE, calculateScore(Color.WHITE));
        scores.put(Color.BLACK, calculateScore(Color.BLACK));
        return scores;
    }

    private double calculateScore(final Color color) {
        return pieces.entrySet().stream()
                .filter(positionPiece -> positionPiece.getValue().isMyColor(color))
                .mapToDouble(positionPiece -> positionPiece.getValue()
                        .getScore(hasSameFilePawn(positionPiece.getKey(), positionPiece.getValue())))
                .sum();
    }

    private boolean hasSameFilePawn(final Position position, final Piece piece) {
        long count = pieces.entrySet().stream()
                .filter(positionPiece -> positionPiece.getKey().isSameFile(position)
                        && positionPiece.getValue().isType(PieceType.PAWN)
                        && positionPiece.getValue().isMyColor(piece))
                .count();
        return count > 1;
    }

    public List<Color> getWinners() {
        List<Color> winners = new ArrayList<>();

        Double blackScore = getScores().get(Color.BLACK);
        Double whiteScore = getScores().get(Color.WHITE);

        if (blackScore >= whiteScore) {
            winners.add(Color.BLACK);
        }
        if (blackScore <= whiteScore) {
            winners.add(Color.WHITE);
        }
        return winners;
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "pieces=" + pieces +
                '}';
    }
}
