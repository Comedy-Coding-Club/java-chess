package chess.domain.piece;

import chess.domain.board.Path;

public abstract class Piece {
    private final Color color;
    private final PieceType pieceType;

    protected Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public abstract boolean canMove(Path path);

    public abstract void move();

    public boolean isAlly(Piece other) {
        return this.color == other.color;
    }

    public boolean isColor(Color color) {
        return this.color == color;
    }

    public boolean isTypeOf(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
