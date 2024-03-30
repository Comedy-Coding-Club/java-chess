package chess.service.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    private static final Score SCORE_5 = new Score(5);
    private static final Score SCORE_3 = new Score(3);

    @DisplayName("점수는 더해질 수 있다.")
    @Test
    void scoreAddTest() {
        assertThat(SCORE_5.add(SCORE_3)).isEqualTo(new Score(8));
    }

    @DisplayName("점수는 뺄 수 있다.")
    @Test
    void scoreSubtractTest() {
        assertThat(SCORE_5.subtract(SCORE_3)).isEqualTo(new Score(2));
    }
}
