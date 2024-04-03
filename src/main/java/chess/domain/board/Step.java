package chess.domain.board;

public class Step {
    private final Direction direction;
    private final LocationState locationState;

    public Step(Direction direction, LocationState locationState) {
        this.direction = direction;
        this.locationState = locationState;
    }

    public boolean isDiagonalDirection() {
        return direction.isDiagonal();
    }

    public boolean isOrthogonalDirection() {
        return direction.isOrthogonal();
    }

    public boolean hasPiece() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return locationState.isEmpty();
    }

    public boolean hasEnemy() {
        return locationState.isEnemy();
    }

    public boolean hasAlly() {
        return locationState.isAlly();
    }

    public boolean isUpside() {
        return direction.isUpSide();
    }

    public boolean isDownside() {
        return direction.isDownside();
    }

    public Direction getDirection() {
        return direction;
    }
}
