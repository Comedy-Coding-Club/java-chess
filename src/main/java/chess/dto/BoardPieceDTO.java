package chess.dto;

import chess.domain.Piece;
import chess.domain.position.Position;

public record BoardPieceDTO(Position position, Piece piece) {
}
