package view;

import domain.Team;
import domain.piece.*;

import java.util.Arrays;

public enum PieceTypeFormat {
    BLACK_PAWN("P", new Pawn(Team.BLACK)),
    BLACK_KNIGHT("N", new Knight(Team.BLACK)),
    BLACK_ROOK("R", new Rook(Team.BLACK)),
    BLACK_BISHOP("B", new Bishop(Team.BLACK)),
    BLACK_QUEEN("Q", new Queen(Team.BLACK)),
    BLACK_KING("K", new King(Team.BLACK)),
    WHITE_PAWN("p", new Pawn(Team.WHITE)),
    WHITE_KNIGHT("n", new Knight(Team.WHITE)),
    WHITE_ROOK("r", new Rook(Team.WHITE)),
    WHITE_BISHOP("b", new Bishop(Team.WHITE)),
    WHITE_QUEEN("q", new Queen(Team.WHITE)),
    WHITE_KING("k", new King(Team.WHITE)),
    ;

    static final String EMPTY_PIECE = ".";

    private final String format;
    private final Piece piece;

    PieceTypeFormat(final String format, final Piece piece) {
        this.format = format;
        this.piece = piece;
    }

    public static String findFormat(final Piece piece) {
        return Arrays.stream(values())
                .filter(type -> type.piece.equals(piece))
                .map(type -> type.format)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("피스 타입이 없습니다."));
    }
}
