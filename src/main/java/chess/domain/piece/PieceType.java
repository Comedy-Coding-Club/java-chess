package chess.domain.piece;

import java.util.Arrays;

public enum PieceType {

    BISHOP, PAWN, KING, KNIGHT, QUEEN, ROOK;

    public static PieceType from(final String pieceType) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 기물의 종류입니다."));
    }
}
