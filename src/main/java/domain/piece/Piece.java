package domain.piece;

import domain.Square;
import domain.Team;

public abstract class Piece {
    protected final Team team;

    protected Piece(final Team team) {
        this.team = team;
    }

    public boolean canNotMove(final Square source, final Square target) {
        return !canMove(source, target);
    }

    protected abstract boolean canMove(Square source, Square target);

    public boolean canNotAttack(final Square source, final Square target) {
        return !canAttack(source, target);
    }

    protected boolean canAttack(final Square source, final Square target) {
        return canMove(source, target);
    }

    public boolean isSameTeam(final Piece other) {
        return this.team == other.team;
    }

    public boolean isOppositeTeam(final Team other) {
        return team != other;
    }

    public abstract boolean equals(final Object o);

    public abstract int hashCode();
}
