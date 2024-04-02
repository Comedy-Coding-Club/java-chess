package chess.dto;

import chess.domain.Piece;
import chess.domain.position.Position;

public record BoardPieceDto(Position position, Piece piece) {
}
