package domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("점수는 0점 이상이다.")
    @Test
    void validateScoreRange() {
        Assertions.assertThatThrownBy(() -> new Score(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
