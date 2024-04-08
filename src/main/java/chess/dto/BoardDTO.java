package chess.dto;

import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Map;

public record BoardDTO(Map<Position, Piece> board) {
}
