package domain.piece;

import static org.assertj.core.api.Assertions.*;

import domain.score.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("점수는 0점 이상이다.")
    @Test
    void validateScoreRange() {
        assertThatThrownBy(() -> new Score(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @DisplayName("Score 더하기를 할 수 있다.")
    @Test
    void plus() {
        Score source = new Score(1.0);
        Score target = new Score(2.0);

        assertThat(source.plus(target)).isEqualTo(new Score(3.0));
    }

    @DisplayName("Score 빼기를 할 수 있다.")
    @Test
    void subtract() {
        Score source = new Score(3.0);
        Score target = new Score(1.0);

        assertThat(source.subtract(target)).isEqualTo(new Score(2.0));
    }

    @DisplayName("Score 곱하기를 할 수 있다.")
    @Test
    void multiply() {
        Score source = new Score(2.0);

        assertThat(source.multiply(2.0)).isEqualTo(new Score(4.0));
    }
}
