package chess.domain.piece;

import chess.domain.Direction;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Position {

    private static final Set<Position> ALL_POSITIONS = cachePositions();
    public static final int FILE_INDEX = 0;
    public static final int RANK_INDEX = 1;

    private final File file;
    private final Rank rank;

    public Position(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Position from(final String input) {
        validatePosition(input);
        String file = input.substring(FILE_INDEX, RANK_INDEX);
        String rank = input.substring(RANK_INDEX);
        return findPosition(File.fromSymbol(file), Rank.fromInput(rank));
    }

    private static void validatePosition(final String input) {
        validateLength(input);
        validateRankNumeric(input.substring(RANK_INDEX));
    }

    private static void validateRankNumeric(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }

    private static void validateLength(final String input) {
        if (input.length() != 2) {
            throw new IllegalArgumentException("[ERROR] 유효한 위치 입력이 아닙니다.");
        }
    }

    private static Set<Position> cachePositions() {
        return Arrays.stream(File.values())
                .flatMap(file -> Arrays.stream(Rank.values()).map(rank -> new Position(file, rank)))
                .collect(Collectors.toSet());
    }

    private static Position findPosition(final File file, final Rank rank) {
        return ALL_POSITIONS.stream()
                .filter(position -> position.equals(new Position(file, rank)))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 위치입니다."));
    }

    public Position move(final Direction direction) {
        return new Position(file.move(direction.x()), rank.move(direction.y()));
    }

    public boolean canMove(final Direction direction) {
        return file.canMove(direction.x()) && rank.canMove(direction.y());
    }

    public boolean isSameRank(final Rank other) {
        return rank == other;
    }

    public boolean isSameFile(final Position otherPosition) {
        return file == otherPosition.file;
    }

    public String getFile() {
        return file.getSymbol();
    }

    public int getRank() {
        return rank.getIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return rank == position.rank && file == position.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }

    @Override
    public String toString() {
        return "" + file + rank;
    }
}
