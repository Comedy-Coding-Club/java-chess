package chess.util;

import chess.domain.board.ChessBoard;
import chess.domain.board.Turn;
import chess.domain.piece.Color;
import chess.domain.piece.type.BlackPawn;
import chess.domain.piece.type.WhitePawn;
import chess.domain.position.File;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Pawn;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardInitializer {

    private ChessBoardInitializer() {
    }

    public static ChessBoard init() {
        final Map<Position, Piece> pieces = new HashMap<>();

        createPieceWithoutPawn(pieces, Color.BLACK, Rank.EIGHT);
        createPawn(pieces, new BlackPawn(), Rank.SEVEN);
        createPawn(pieces, new WhitePawn(), Rank.TWO);
        createPieceWithoutPawn(pieces, Color.WHITE, Rank.ONE);

        return new ChessBoard(0, new Turn(Color.WHITE), pieces);
    }

    private static void createPawn(final Map<Position, Piece> pieces, final Pawn pawn, final Rank rank) {
        pieces.put(new Position(File.A, rank), pawn);
        pieces.put(new Position(File.B, rank), pawn);
        pieces.put(new Position(File.C, rank), pawn);
        pieces.put(new Position(File.D, rank), pawn);
        pieces.put(new Position(File.E, rank), pawn);
        pieces.put(new Position(File.F, rank), pawn);
        pieces.put(new Position(File.G, rank), pawn);
        pieces.put(new Position(File.H, rank), pawn);
    }

    private static void createPieceWithoutPawn(final Map<Position, Piece> pieces, final Color color, final Rank rank) {
        pieces.put(new Position(File.A, rank), new Rook(color));
        pieces.put(new Position(File.B, rank), new Knight(color));
        pieces.put(new Position(File.C, rank), new Bishop(color));
        pieces.put(new Position(File.D, rank), new Queen(color));
        pieces.put(new Position(File.E, rank), new King(color));
        pieces.put(new Position(File.F, rank), new Bishop(color));
        pieces.put(new Position(File.G, rank), new Knight(color));
        pieces.put(new Position(File.H, rank), new Rook(color));
    }
}
