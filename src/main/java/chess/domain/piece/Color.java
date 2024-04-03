package chess.domain.piece;

import chess.domain.board.Path;

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
