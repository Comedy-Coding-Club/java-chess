package domain.movement;

import static fixture.PositionFixture.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @DisplayName("(1,1) -> (2,2)로 이동하면, NE 방향이다.")
    @Test
    void moveToNorthEast() {
        Direction findDirection = Direction.findDirection(B1, C2);
        Assertions.assertThat(findDirection).isEqualTo(Direction.NE);
    }
}
