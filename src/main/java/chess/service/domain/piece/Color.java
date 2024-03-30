package chess.service.domain.piece;

import chess.service.domain.board.Path;

public enum Color {
    BLACK, WHITE;

    public Color getOpponent() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }

    public boolean isForward(Path path) {
        if (this == BLACK) {
            return path.isDownside();
        }
        return path.isUpside();
    }
}
