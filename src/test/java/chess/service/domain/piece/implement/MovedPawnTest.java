package chess.service.domain.piece.implement;

import static org.assertj.core.api.Assertions.assertThat;

import chess.service.domain.board.Direction;
import chess.service.domain.board.LocationState;
import chess.service.domain.board.Path;
import chess.service.domain.board.Step;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Piece;
import chess.service.domain.piece.implement.pawn.MovedPawn;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MovedPawnTest {
    private static final Piece MOVED_BLACK_PAWN = new MovedPawn(Color.BLACK);
    private static final Piece MOVED_WHITE_PAWN = new MovedPawn(Color.WHITE);

    @DisplayName("폰은 뒤로 이동할 수 없다.")
    @Nested
    class PawnBackwardTest {
        @DisplayName("블랙 폰은 위로 이동할 수 없다")
        @Test
        void blackPawnOppositeTest() {
            Path path = new Path(List.of(
                    new Step(Direction.UP, LocationState.EMPTY)
            ));

            assertThat(MOVED_BLACK_PAWN.canMove(path)).isFalse();
        }

        @DisplayName("화이트 폰은 아래로 이동할 수 없다")
        @Test
        void whitePawnOppositeTest() {
            Path path = new Path(List.of(
                    new Step(Direction.DOWN, LocationState.EMPTY)
            ));

            assertThat(MOVED_WHITE_PAWN.canMove(path)).isFalse();
        }

        @DisplayName("블랙 폰은 대각선 위로 이동할 수 없다.")
        @ParameterizedTest
        @EnumSource(value = Direction.class, names = {"UP_LEFT", "UP_RIGHT"})
        void blackPawnOppositeDiagonalTest(Direction direction) {
            Path path = new Path(List.of(
                    new Step(direction, LocationState.ENEMY)
            ));
            assertThat(MOVED_BLACK_PAWN.canMove(path)).isFalse();
        }

        @DisplayName("화이트 폰은 대각선 아래로 이동할 수 없다.")
        @ParameterizedTest
        @EnumSource(value = Direction.class, names = {"DOWN_LEFT", "DOWN_RIGHT"})
        void whitePawnOppositeDiagonalTest(Direction direction) {
            Path path = new Path(List.of(
                    new Step(direction, LocationState.ENEMY)
            ));
            assertThat(MOVED_WHITE_PAWN.canMove(path)).isFalse();
        }
    }

    @DisplayName("블랙 폰은 아래로 이동할 수 있다.")
    @Test
    void blackPawnForwardTest() {
        Path path = new Path(List.of(
                new Step(Direction.DOWN, LocationState.EMPTY)
        ));
        assertThat(MOVED_BLACK_PAWN.canMove(path)).isTrue();
    }

    @DisplayName("화이트 폰은 위로 이동할 수 있다.")
    @Test
    void whitePawnForwardTest() {
        Path path = new Path(List.of(
                new Step(Direction.UP, LocationState.EMPTY)
        ));
        assertThat(MOVED_WHITE_PAWN.canMove(path)).isTrue();
    }

    @DisplayName("움직인 적 있는 블랙 폰은 아래로 두 번 이동할 수 없다.")
    @Test
    void neverMovedBlackPawn_D_D_Test() {
        Path path = new Path(List.of(
                new Step(Direction.DOWN, LocationState.EMPTY),
                new Step(Direction.DOWN, LocationState.EMPTY)
        ));
        assertThat(MOVED_BLACK_PAWN.canMove(path)).isFalse();
    }

    @DisplayName("움직인 적 있는 화이트 폰은 위로 두 번 이동할 수 없다.")
    @Test
    void neverMovedWhitePawn_U_U_Test() {
        Path path = new Path(List.of(
                new Step(Direction.UP, LocationState.EMPTY),
                new Step(Direction.UP, LocationState.EMPTY)
        ));
        assertThat(MOVED_WHITE_PAWN.canMove(path)).isFalse();
    }


    @DisplayName("폰은 한 방향으로만 이동할 수 있다.")
    @Test
    void tooManyDirectionTest() {
        Path manyDirectionPath = new Path(List.of(
                new Step(Direction.DOWN, LocationState.EMPTY),
                new Step(Direction.LEFT, LocationState.EMPTY)
        ));
        assertThat(MOVED_WHITE_PAWN.canMove(manyDirectionPath))
                .isFalse();
    }

    @DisplayName("경로 중간에 기물이 위치한다면 움직일 수 없다.")
    @Test
    void pathHasPieceTest() {
        Path notEmptyPath = new Path(List.of(
                new Step(Direction.DOWN, LocationState.ALLY),
                new Step(Direction.DOWN, LocationState.ENEMY)
        ));

        assertThat(MOVED_WHITE_PAWN.canMove(notEmptyPath))
                .isFalse();
    }

    @DisplayName("목적지에 아군이 존재한다면 움직일 수 없다.")
    @ParameterizedTest
    @EnumSource(Direction.class)
    void allyLocatedAtTargetTest(Direction direction) {
        Path manyDirectionPath = new Path(List.of(
                new Step(direction, LocationState.ALLY)
        ));

        assertThat(MOVED_WHITE_PAWN.canMove(manyDirectionPath))
                .isFalse();
    }
}
