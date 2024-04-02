package chess.domain.board;

import chess.domain.Piece;
import chess.domain.position.Position;

public record BoardDto(Position position, Piece piece) {
}
