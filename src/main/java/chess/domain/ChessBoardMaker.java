package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.File;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
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

public class ChessBoardMaker {

    private ChessBoardMaker() {
    }

    public static ChessBoard init() {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        pieces.putAll(createPieceWithoutPawn(Color.BLACK, Rank.EIGHT));
        pieces.putAll(createBlackPawn(Rank.SEVEN));
        pieces.putAll(createEmpty(Rank.SIX));
        pieces.putAll(createEmpty(Rank.FIVE));
        pieces.putAll(createEmpty(Rank.FOUR));
        pieces.putAll(createEmpty(Rank.THREE));
        pieces.putAll(createWhitePawn(Rank.TWO));
        pieces.putAll(createPieceWithoutPawn(Color.WHITE, Rank.ONE));

        return new ChessBoard(pieces);
    }

    private static Map<Position, Piece> createWhitePawn(final Rank rank) {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        pieces.put(new Position(File.A, rank), new WhitePawn());
        pieces.put(new Position(File.B, rank), new WhitePawn());
        pieces.put(new Position(File.C, rank), new WhitePawn());
        pieces.put(new Position(File.D, rank), new WhitePawn());
        pieces.put(new Position(File.E, rank), new WhitePawn());
        pieces.put(new Position(File.F, rank), new WhitePawn());
        pieces.put(new Position(File.G, rank), new WhitePawn());
        pieces.put(new Position(File.H, rank), new WhitePawn());

        return pieces;
    }

    private static Map<Position, Piece> createBlackPawn(final Rank rank) {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        pieces.put(new Position(File.A, rank), new BlackPawn());
        pieces.put(new Position(File.B, rank), new BlackPawn());
        pieces.put(new Position(File.C, rank), new BlackPawn());
        pieces.put(new Position(File.D, rank), new BlackPawn());
        pieces.put(new Position(File.E, rank), new BlackPawn());
        pieces.put(new Position(File.F, rank), new BlackPawn());
        pieces.put(new Position(File.G, rank), new BlackPawn());
        pieces.put(new Position(File.H, rank), new BlackPawn());

        return pieces;
    }

    private static Map<Position, Piece> createPieceWithoutPawn(final Color color, final Rank rank) {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        pieces.put(new Position(File.A, rank), new Rook(color));
        pieces.put(new Position(File.B, rank), new Knight(color));
        pieces.put(new Position(File.C, rank), new Bishop(color));
        pieces.put(new Position(File.D, rank), new Queen(color));
        pieces.put(new Position(File.E, rank), new King(color));
        pieces.put(new Position(File.F, rank), new Bishop(color));
        pieces.put(new Position(File.G, rank), new Knight(color));
        pieces.put(new Position(File.H, rank), new Rook(color));

        return pieces;
    }

    private static Map<Position, Piece> createEmpty(final Rank rank) {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        pieces.put(new Position(File.A, rank), new Empty());
        pieces.put(new Position(File.B, rank), new Empty());
        pieces.put(new Position(File.C, rank), new Empty());
        pieces.put(new Position(File.D, rank), new Empty());
        pieces.put(new Position(File.E, rank), new Empty());
        pieces.put(new Position(File.F, rank), new Empty());
        pieces.put(new Position(File.G, rank), new Empty());
        pieces.put(new Position(File.H, rank), new Empty());

        return pieces;
    }

}
