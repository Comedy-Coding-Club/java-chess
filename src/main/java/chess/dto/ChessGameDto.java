package chess.dto;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import java.util.List;
import java.util.Map;

public record ChessGameDto(Map<Position, Piece> chessBoard, Color currentColor) {

    public static ChessGameDto of(final List<SquareDto> squareDtos, final Color currentColor) {
        return new ChessGameDto(SquareDto.toChessBoard(squareDtos), currentColor);
    }
}
