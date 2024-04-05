package chess.view;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.Arrays;

public enum PieceFormatter {
    BISHOP(PieceType.BISHOP, "B"),
    KING(PieceType.KING, "K"),
    KNIGHT(PieceType.KNIGHT, "N"),
    PAWN(PieceType.PAWN, "P"),
    QUEEN(PieceType.QUEEN, "Q"),
    ROOK(PieceType.ROOK, "R"),
    EMPTY(PieceType.EMPTY, ".");

    private final PieceType pieceType;
    private final String format;

    private PieceFormatter(final PieceType pieceType, final String format) {
        this.pieceType = pieceType;
        this.format = format;
    }

    public static String from(final Piece piece) {
        return Arrays.stream(values())
                .filter(pieceFormat -> piece.isType(pieceFormat.pieceType))
                .findFirst()
                .map(pieceFormat -> pieceFormat.getColorFormat(piece))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 기물입니다."));

    }

    private String getColorFormat(final Piece piece) {
        if (piece.isBlack()) {
            return format;
        }
        return format.toLowerCase();
    }
}
