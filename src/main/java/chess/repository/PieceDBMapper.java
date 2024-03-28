package chess.repository;

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
import java.util.Arrays;

public class PieceDBMapper {

    public static Piece createPiece(String pieceTypeName, String colorName) {
        PieceType pieceType = convertToPieceType(pieceTypeName);
        Color color = convertToColor(colorName);
        return createPieceOf(pieceType, color);
    }

    private static Piece createPieceOf(PieceType pieceType, Color color) {
        return switch (pieceType) {
            case KING -> new King(color);
            case QUEEN -> new Queen(color);
            case ROOK -> new Rook(color);
            case KNIGHT -> new Knight(color);
            case BISHOP -> new Bishop(color);
            case PAWN -> createColoredPawn(color);
        };
    }

    private static Piece createColoredPawn(Color color) {
        if (color == Color.BLACK) {
            return new BlackPawn();
        }
        return new WhitePawn();
    }

    private static PieceType convertToPieceType(String pieceTypeName) {
        return Arrays.stream(PieceType.values())
                .filter(type -> type.name().equalsIgnoreCase(pieceTypeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("DB에 잘못된 값이 저장되었습니다."));
    }

    private static Color convertToColor(String colorName) {
        return Arrays.stream(Color.values())
                .filter(color -> color.name().equalsIgnoreCase(colorName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("DB에 잘못된 값이 저장되었습니다."));
    }
}
