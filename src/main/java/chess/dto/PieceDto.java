package chess.dto;

import chess.domain.piece.Piece;

public record PieceDto(String color, String pieceType) {

    public static PieceDto fromPiece(final Piece piece) {
        return new PieceDto(piece.getColor().name(), piece.getPieceType().name());
    }
}
