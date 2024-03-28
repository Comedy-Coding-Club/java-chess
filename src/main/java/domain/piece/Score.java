package domain.piece;

import java.util.Objects;

public class Score {
    private static final int MIN_SCORE = 0;
    private final double value;

    public Score(double value) {
        validateScoreRange(value);
        this.value = value;
    }

    private static void validateScoreRange(double score) {
        if (score < MIN_SCORE) {
            throw new IllegalArgumentException(String.format("점수는 %d 이상입니다.", MIN_SCORE));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return Double.compare(value, score.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
