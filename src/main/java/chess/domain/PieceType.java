package chess.domain;

import chess.domain.position.Direction;
import chess.domain.position.Position;
import chess.domain.strategy.speical.BishopMoveStrategy;
import chess.domain.strategy.pawn.BlackPawnMoveStrategy;
import chess.domain.strategy.speical.KingMoveStrategy;
import chess.domain.strategy.speical.KnightMoveStrategy;
import chess.domain.strategy.MoveStrategy;
import chess.domain.strategy.speical.QueenMoveStrategy;
import chess.domain.strategy.speical.RookMoveStrategy;
import chess.domain.strategy.pawn.WhitePawnMoveStrategy;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

public enum PieceType {

    BLACK_PAWN(new BlackPawnMoveStrategy(), 1),
    WHITE_PAWN(new WhitePawnMoveStrategy(), 1),
    ROOK(new RookMoveStrategy(), 5),
    KNIGHT(new KnightMoveStrategy(), 2.5),
    BISHOP(new BishopMoveStrategy(), 3),
    QUEEN(new QueenMoveStrategy(), 9),
    KING(new KingMoveStrategy(), 0),
    ;

    private final MoveStrategy moveStrategy;
    private final double score;

    PieceType(MoveStrategy moveStrategy, double score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public static PieceType findByName(String name) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException());
    }

    public Map<Direction, Deque<Position>> calculateAllDirectionPositions(Position currentPosition) {
        return this.moveStrategy.generateMovablePositions(currentPosition);
    }

    public boolean isBlackPawn() {
        return this == BLACK_PAWN;
    }

    public boolean isWhitePawn() {
        return this == WHITE_PAWN;
    }

    public boolean isPawn() {
        return isBlackPawn() || isWhitePawn();
    }

    public boolean isKing() { // TODO 이런 메서드가 계속 생기는게 맞을까??
        return this == KING;
    }

    public double getScore() {
        return score;
    }
}
