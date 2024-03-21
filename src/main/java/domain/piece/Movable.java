package domain.piece;

import domain.game.Direction;
import domain.position.Position;
import java.util.Objects;

public class Movable {
    private int maxMovement;
    private final Direction direction;

    public Movable(int maxMovement, Direction direction) {
        this.maxMovement = maxMovement;
        this.direction = direction;
    }

    public boolean canMove(Position sourcePosition, Position targetPosition) {
        Direction findDirection = Direction.findDirection(sourcePosition, targetPosition);

        // source -> target이 direction으로 몇 칸 이동하는지?
        // TODO: 코드 개선하기
        if (this.direction == findDirection) {
            int count = 0;
            Position here = new Position(sourcePosition);
            while (!here.equals(targetPosition)) {
                here.move(direction);
                count++;
            }
            return count <= maxMovement;
        }
        return false;
    }

    public void decreaseMaxMovement() {
        maxMovement--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movable movable = (Movable) o;
        return direction == movable.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }
}
