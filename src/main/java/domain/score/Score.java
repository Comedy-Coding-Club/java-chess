package domain.score;

import java.util.Objects;

public class Score implements Comparable<Score> {
    private static final int MIN_SCORE = 0;

    private final double value;

    public Score(double value) {
        validateScoreRange(value);
        this.value = value;
    }

    private void validateScoreRange(double score) {
        if (score < MIN_SCORE) {
            throw new IllegalArgumentException(String.format("점수는 %d 이상입니다.", MIN_SCORE));
        }
    }

    public Score plus(Score target) {
        return new Score(value + target.value);
    }

    public Score subtract(Score target) {
        return new Score(value - target.value);
    }

    public Score multiply(double value) {
        return new Score(this.value * value);
    }

    public double getValue() {
        return value;
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

    @Override
    public int compareTo(Score o) {
        return (int) (value - o.value);
    }
}
