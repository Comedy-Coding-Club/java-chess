package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.util.PieceClassConvertor;

public record SquareDto(int file, int rank, String color, String piece_type) {

    public Position toPosition() {
        return new Position(File.fromIndex(file), Rank.fromIndex(rank));
    }

    public Piece toPiece() {
        return PieceClassConvertor.from(piece_type + "_" + color);
    }
}
