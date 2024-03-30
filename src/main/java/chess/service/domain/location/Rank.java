package chess.service.domain.location;

import chess.service.domain.board.Direction;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Rank {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private static final Map<Integer, Rank> ROWS =
            Arrays.stream(values())
                    .collect(Collectors.toMap(Rank::getRank, Function.identity()));

    private final int rank;

    Rank(int rank) {
        this.rank = rank;
    }

    public static Rank createByRank(String rank) {
        try {
            return createByRank(Integer.parseInt(rank));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 Row 입력입니다. 숫자를 입력해 주세요");
        }
    }

    public static Rank createByRank(int rank) {
        return Optional.ofNullable(ROWS.get(rank))
                .orElseThrow(() -> new IllegalArgumentException("잘못된 Row 입력입니다."));
    }

    public Rank move(Direction direction) {
        if (direction.isUpSide()) {
            return this.next();
        }
        if (direction.isDownside()) {
            return this.previous();
        }
        return this;
    }

    private Rank previous() {
        return Rank.createByRank(this.rank - 1);
    }

    private Rank next() {
        return Rank.createByRank(this.rank + 1);
    }

    public int calculateDistance(Rank other) {
        return other.rank - this.rank;
    }

    private int getRank() {
        return rank;
    }

    public String getSymbol() {
        return Integer.toString(rank);
    }
}
