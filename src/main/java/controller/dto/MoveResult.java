package controller.dto;

import controller.constants.GameState;
import domain.piece.Piece;

public record MoveResult(
        GameState gameState,
        Piece movedPiece
) {
}
