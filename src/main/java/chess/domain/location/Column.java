package chess.domain.location;

import chess.domain.board.Direction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Column {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8);

    private static final List<Column> COLUMNS = List.of(A, B, C, D, E, F, G, H);
    private final int index;

    Column(int index) {
        this.index = index;
    }

    public static Column of(String input) {
        return Arrays.stream(values())
                .filter(column -> column.isName(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 Column 입력입니다."));
    }

    public Column move(Direction direction) {
        if (direction.isLeftSide()) {
            return this.previous();
        }
        if (direction.isRightSide()) {
            return this.next();
        }
        return this;
    }

    private Column next() {
        int ordinalIndex = this.index - 1;
        try {
            return COLUMNS.get(ordinalIndex + 1);
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("잘못된 방향 입력입니다.");
        }
    }

    private Column previous() {
        int ordinalIndex = this.index - 1;
        try {
            return COLUMNS.get(ordinalIndex - 1);
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("잘못된 방향 입력입니다.");
        }
    }

    private boolean isName(String name) {
        return this.name().equalsIgnoreCase(name);
    }

    public int calculateDistance(Column other) {
        return other.index - this.index;
    }
}
