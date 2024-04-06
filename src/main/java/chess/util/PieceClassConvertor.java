package chess.util;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.BlackPawn;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import chess.domain.piece.type.WhitePawn;
import java.util.HashMap;
import java.util.Map;

public class PieceClassConvertor {

    private PieceClassConvertor() {
    }

    private static final Map<String, Piece> pieces = new HashMap<>();

    static {
        pieces.put("BISHOP_WHITE", new Bishop(Color.WHITE));
        pieces.put("BISHOP_BLACK", new Bishop(Color.BLACK));
        pieces.put("PAWN_WHITE", new WhitePawn());
        pieces.put("PAWN_BLACK", new BlackPawn());
        pieces.put("KING_WHITE", new King(Color.WHITE));
        pieces.put("KING_BLACK", new King(Color.BLACK));
        pieces.put("KNIGHT_WHITE", new Knight(Color.WHITE));
        pieces.put("KNIGHT_BLACK", new Knight(Color.BLACK));
        pieces.put("QUEEN_WHITE", new Queen(Color.WHITE));
        pieces.put("QUEEN_BLACK", new Queen(Color.BLACK));
        pieces.put("ROOK_WHITE", new Rook(Color.WHITE));
        pieces.put("ROOK_BLACK", new Rook(Color.BLACK));
    }

    public static Piece from(final String piece) {
        return pieces.get(piece);
    }
}
