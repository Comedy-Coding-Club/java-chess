package chess;

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

@SuppressWarnings("unused")
public final class Fixtures {

    public static final Position A1 = new Position(File.A, Rank.ONE);
    public static final Position A2 = new Position(File.A, Rank.TWO);
    public static final Position A3 = new Position(File.A, Rank.THREE);
    public static final Position A4 = new Position(File.A, Rank.FOUR);
    public static final Position A5 = new Position(File.A, Rank.FIVE);
    public static final Position A6 = new Position(File.A, Rank.SIX);
    public static final Position A7 = new Position(File.A, Rank.SEVEN);
    public static final Position A8 = new Position(File.A, Rank.EIGHT);
    public static final Position B1 = new Position(File.B, Rank.ONE);

    public static final Position B2 = new Position(File.B, Rank.TWO);
    public static final Position B3 = new Position(File.B, Rank.THREE);
    public static final Position B4 = new Position(File.B, Rank.FOUR);
    public static final Position B5 = new Position(File.B, Rank.FIVE);
    public static final Position B6 = new Position(File.B, Rank.SIX);
    public static final Position B7 = new Position(File.B, Rank.SEVEN);
    public static final Position B8 = new Position(File.B, Rank.EIGHT);
    public static final Position C1 = new Position(File.C, Rank.ONE);

    public static final Position C2 = new Position(File.C, Rank.TWO);
    public static final Position C3 = new Position(File.C, Rank.THREE);
    public static final Position C4 = new Position(File.C, Rank.FOUR);
    public static final Position C5 = new Position(File.C, Rank.FIVE);
    public static final Position C6 = new Position(File.C, Rank.SIX);
    public static final Position C7 = new Position(File.C, Rank.SEVEN);
    public static final Position C8 = new Position(File.C, Rank.EIGHT);
    public static final Position D1 = new Position(File.D, Rank.ONE);

    public static final Position D2 = new Position(File.D, Rank.TWO);
    public static final Position D3 = new Position(File.D, Rank.THREE);
    public static final Position D4 = new Position(File.D, Rank.FOUR);
    public static final Position D5 = new Position(File.D, Rank.FIVE);
    public static final Position D6 = new Position(File.D, Rank.SIX);
    public static final Position D7 = new Position(File.D, Rank.SEVEN);
    public static final Position D8 = new Position(File.D, Rank.EIGHT);
    public static final Position E1 = new Position(File.E, Rank.ONE);

    public static final Position E2 = new Position(File.E, Rank.TWO);
    public static final Position E3 = new Position(File.E, Rank.THREE);
    public static final Position E4 = new Position(File.E, Rank.FOUR);
    public static final Position E5 = new Position(File.E, Rank.FIVE);
    public static final Position E6 = new Position(File.E, Rank.SIX);
    public static final Position E7 = new Position(File.E, Rank.SEVEN);
    public static final Position E8 = new Position(File.E, Rank.EIGHT);
    public static final Position F1 = new Position(File.F, Rank.ONE);

    public static final Position F2 = new Position(File.F, Rank.TWO);
    public static final Position F3 = new Position(File.F, Rank.THREE);
    public static final Position F4 = new Position(File.F, Rank.FOUR);
    public static final Position F5 = new Position(File.F, Rank.FIVE);
    public static final Position F6 = new Position(File.F, Rank.SIX);
    public static final Position F7 = new Position(File.F, Rank.SEVEN);
    public static final Position F8 = new Position(File.F, Rank.EIGHT);
    public static final Position G1 = new Position(File.G, Rank.ONE);

    public static final Position G2 = new Position(File.G, Rank.TWO);
    public static final Position G3 = new Position(File.G, Rank.THREE);
    public static final Position G4 = new Position(File.G, Rank.FOUR);
    public static final Position G5 = new Position(File.G, Rank.FIVE);
    public static final Position G6 = new Position(File.G, Rank.SIX);
    public static final Position G7 = new Position(File.G, Rank.SEVEN);
    public static final Position G8 = new Position(File.G, Rank.EIGHT);
    public static final Position H1 = new Position(File.H, Rank.ONE);

    public static final Position H2 = new Position(File.H, Rank.TWO);
    public static final Position H3 = new Position(File.H, Rank.THREE);
    public static final Position H4 = new Position(File.H, Rank.FOUR);
    public static final Position H5 = new Position(File.H, Rank.FIVE);
    public static final Position H6 = new Position(File.H, Rank.SIX);
    public static final Position H7 = new Position(File.H, Rank.SEVEN);
    public static final Position H8 = new Position(File.H, Rank.EIGHT);
    private Fixtures() {
    }

    public static final Map<Position, Piece> EMPTY_PIECES = createPieces();
    public static final Map<Position, Piece> GAMING_PIECES = createGamingPieces();

    private static Map<Position, Piece> createGamingPieces() {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        final Map<Position, Piece> pieces_a = Map.of(
                A1, new Empty(),
                A2, new Empty(),
                A3, new WhitePawn(),
                A4, new Empty(),
                A5, new BlackPawn(),
                A6, new Empty(),
                A7, new Empty(),
                A8, new Rook(Color.BLACK)
        );

        final Map<Position, Piece> pieces_b = Map.of(
                B1, new Empty(),
                B2, new WhitePawn(),
                B3, new Empty(),
                B4, new Rook(Color.WHITE),
                B5, new Empty(),
                B6, new BlackPawn(),
                B7, new Empty(),
                B8, new Knight(Color.BLACK)
        );

        final Map<Position, Piece> pieces_c = Map.of(
                C1, new Bishop(Color.WHITE),
                C2, new Empty(),
                C3, new Knight(Color.WHITE),
                C4, new WhitePawn(),
                C5, new BlackPawn(),
                C6, new Empty(),
                C7, new Empty(),
                C8, new Bishop(Color.BLACK)
        );

        final Map<Position, Piece> pieces_d = Map.of(
                D1, new Empty(),
                D2, new WhitePawn(),
                D3, new Empty(),
                D4, new Queen(Color.BLACK),
                D5, new Empty(),
                D6, new Empty(),
                D7, new BlackPawn(),
                D8, new Empty()
        );

        final Map<Position, Piece> pieces_e = Map.of(
                E1, new Empty(),
                E2, new WhitePawn(),
                E3, new King(Color.WHITE),
                E4, new BlackPawn(),
                E5, new Queen(Color.WHITE),
                E6, new Empty(),
                E7, new Empty(),
                E8, new King(Color.BLACK)
        );

        final Map<Position, Piece> pieces_f = Map.of(
                F1, new Empty(),
                F2, new Empty(),
                F3, new WhitePawn(),
                F4, new Empty(),
                F5, new Empty(),
                F6, new Empty(),
                F7, new BlackPawn(),
                F8, new Bishop(Color.BLACK)
        );

        final Map<Position, Piece> pieces_g = Map.of(
                G1, new Knight(Color.WHITE),
                G2, new Empty(),
                G3, new Empty(),
                G4, new WhitePawn(),
                G5, new Bishop(Color.WHITE),
                G6, new Empty(),
                G7, new BlackPawn(),
                G8, new Knight(Color.BLACK)
        );

        final Map<Position, Piece> pieces_h = Map.of(
                H1, new Rook(Color.WHITE),
                H2, new WhitePawn(),
                H3, new Empty(),
                H4, new Empty(),
                H5, new Empty(),
                H6, new Empty(),
                H7, new BlackPawn(),
                H8, new Rook(Color.BLACK)
        );

        pieces.putAll(pieces_a);
        pieces.putAll(pieces_b);
        pieces.putAll(pieces_c);
        pieces.putAll(pieces_d);
        pieces.putAll(pieces_e);
        pieces.putAll(pieces_f);
        pieces.putAll(pieces_g);
        pieces.putAll(pieces_h);

        return pieces;    }

    private static Map<Position, Piece> createPieces() {
        final Map<Position, Piece> pieces = new LinkedHashMap<>();

        final Map<Position, Piece> pieces_a = Map.of(
                A1, new Empty(),
            A2, new Empty(),
                A3, new Empty(),
                A4, new Empty(),
                A5, new Empty(),
                A6, new Empty(),
                A7, new Empty(),
                A8, new Empty()
        );

        final Map<Position, Piece> pieces_b = Map.of(
                B1, new Empty(),
                B2, new Empty(),
                B3, new Empty(),
                B4, new Empty(),
                B5, new Empty(),
                B6, new Empty(),
                B7, new Empty(),
                B8, new Empty()
        );

        final Map<Position, Piece> pieces_c = Map.of(
                C1, new Empty(),
                C2, new Empty(),
                C3, new Empty(),
                C4, new Empty(),
                C5, new Empty(),
                C6, new Empty(),
                C7, new Empty(),
                C8, new Empty()
        );

        final Map<Position, Piece> pieces_d = Map.of(
                D1, new Empty(),
                D2, new Empty(),
                D3, new Empty(),
                D4, new Empty(),
                D5, new Empty(),
                D6, new Empty(),
                D7, new Empty(),
                D8, new Empty()
        );

        final Map<Position, Piece> pieces_e = Map.of(
                E1, new Empty(),
                E2, new Empty(),
                E3, new Empty(),
                E4, new Empty(),
                E5, new Empty(),
                E6, new Empty(),
                E7, new Empty(),
                E8, new Empty()
        );

        final Map<Position, Piece> pieces_f = Map.of(
                F1, new Empty(),
                F2, new Empty(),
                F3, new Empty(),
                F4, new Empty(),
                F5, new Empty(),
                F6, new Empty(),
                F7, new Empty(),
                F8, new Empty()
        );

        final Map<Position, Piece> pieces_g = Map.of(
                G1, new Empty(),
                G2, new Empty(),
                G3, new Empty(),
                G4, new Empty(),
                G5, new Empty(),
                G6, new Empty(),
                G7, new Empty(),
                G8, new Empty()
        );

        final Map<Position, Piece> pieces_h = Map.of(
                H1, new Empty(),
                H2, new Empty(),
                H3, new Empty(),
                H4, new Empty(),
                H5, new Empty(),
                H6, new Empty(),
                H7, new Empty(),
                H8, new Empty()
        );

        pieces.putAll(pieces_a);
        pieces.putAll(pieces_b);
        pieces.putAll(pieces_c);
        pieces.putAll(pieces_d);
        pieces.putAll(pieces_e);
        pieces.putAll(pieces_f);
        pieces.putAll(pieces_g);
        pieces.putAll(pieces_h);

        return pieces;
    }
}
