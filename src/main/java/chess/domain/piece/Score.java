package chess.domain.piece;

import java.util.Objects;

public class Score {
    public static final Score ZERO = new Score(0);
    private final double score;

    public Score(double score) {
        this.score = score;
    }

    public Score add(Score other) {
        return new Score(score + other.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score other = (Score) o;
        return Double.compare(score, other.score) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    public Score subtract(Score other) {
        return new Score(score - other.score);
    }

    public Score multiply(int amount) {
        return new Score(score * amount);
    }
}
