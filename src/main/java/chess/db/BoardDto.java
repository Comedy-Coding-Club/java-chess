package chess.db;

import chess.domain.Piece;
import chess.domain.position.Position;

public record BoardDto(Position position, Piece piece) {
}
