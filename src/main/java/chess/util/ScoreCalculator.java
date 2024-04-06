package chess.util;

import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class ScoreCalculator {

    private static final double PAWN_MINUS_SCORE = 0.5;

    private ScoreCalculator() {
    }

    public static double calculate(final Map<Position, Piece> pieces) {
        double scoreSum = pieces.values().stream()
                .mapToDouble(Piece::getScore)
                .sum();

        return scoreSum - calculatePawnScore(pieces);
    }

    private static double calculatePawnScore(final Map<Position, Piece> pieces) {
        final Set<Position> positions = getPawnPositions(pieces);

        final long sameFilePawnCount = Arrays.stream(File.values())
                .mapToLong(file -> getSameFilePieceCount(positions, file))
                .sum();

        return sameFilePawnCount * PAWN_MINUS_SCORE;
    }

    private static Set<Position> getPawnPositions(final Map<Position, Piece> pieces) {
        return pieces.entrySet().stream()
                .filter(piece -> piece.getValue().isPawn())
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    private static long getSameFilePieceCount(final Set<Position> positions, final File file) {
        final long count = positions.stream().filter(position -> position.isFile(file)).count();

        if (count >= 2) {
            return count;
        }

        return 0;
    }
}
