package chess.dto;

import chess.domain.position.Position;

public record PositionDto(int fileIndex, int rankIndex) {

    public static PositionDto fromPosition(final Position position) {
        return new PositionDto(position.getFileIndex(), position.getRankIndex());
    }
}
