package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.BlackPawn;
import chess.domain.piece.type.Empty;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import chess.domain.piece.type.WhitePawn;
import java.util.LinkedHashMap;
import java.util.Map;

public class PieceFactory {

    private static Map<PieceType, Piece> whitePieces = createPiecesByType(Color.WHITE);
    private static Map<PieceType, Piece> blakcPieces = createPiecesByType(Color.BLACK);

    private PieceFactory() {
    }

    public static Piece of(final PieceType pieceType, final Color color) {
        if (color == Color.WHITE) {
            return whitePieces.get(pieceType);
        }
        return blakcPieces.get(pieceType);
    }

    private static Map<PieceType, Piece> createPiecesByType(final Color color) {
        Map<PieceType, Piece> pieceTypePiece = new LinkedHashMap<>();

        pieceTypePiece.put(PieceType.KING, new King(color));
        pieceTypePiece.put(PieceType.QUEEN, new Queen(color));
        pieceTypePiece.put(PieceType.ROOK, new Rook(color));
        pieceTypePiece.put(PieceType.BISHOP, new Bishop(color));
        pieceTypePiece.put(PieceType.KNIGHT, new Knight(color));
        pieceTypePiece.put(PieceType.EMPTY, new Empty());

        if (color == Color.WHITE) {
            pieceTypePiece.put(PieceType.PAWN, new WhitePawn());
        }
        pieceTypePiece.put(PieceType.PAWN, new BlackPawn());

        return pieceTypePiece;
    }
}
