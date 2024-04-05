package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("file, rank를 입력받아 위치를 생성한다.")
    @Test
    void create() {
        // given
        final Position positionA_ONE = new Position(File.A, Rank.ONE);
        final Position positionB_TWO = new Position(File.B, Rank.TWO);

        // when && then
        assertThat(positionA_ONE).isEqualTo(new Position(File.A, Rank.ONE));
        assertThat(positionB_TWO).isEqualTo(new Position(File.B, Rank.TWO));
    }

    @DisplayName("문자를 입력받아 위치를 생성한다.")
    @Test
    void from() {
        assertThat(Position.from("a1")).isEqualTo(new Position(File.A, Rank.ONE));
    }

    @DisplayName("입력받은 방향대로 위치를 움직인다.")
    @Test
    void move() {
        // given
        final Position position = new Position(File.A, Rank.ONE);

        // when
        Position moved = position.move(Direction.UP);

        // then
        assertThat(moved).isEqualTo(new Position(File.A, Rank.TWO));
    }

    @DisplayName("입력받은 방향이 움직일 수 있는 위치인지 확인햔다.")
    @Test
    void canMove() {
        // given
        final Position position = new Position(File.A, Rank.ONE);

        // when
        boolean canMove = position.canMove(Direction.RIGHT_UP);

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("입력받은 방향이 움직일 수 없는 위치인지 확인햔다.")
    @Test
    void canNotMove() {
        // given
        final Position position = new Position(File.A, Rank.ONE);

        // when
        boolean canMove = position.canMove(Direction.DOWN);

        // then
        assertThat(canMove).isFalse();
    }
}
