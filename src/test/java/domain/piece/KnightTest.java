package domain.piece;

import domain.File;
import domain.Rank;
import domain.Square;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class KnightTest {
    @DisplayName("나이트는 2칸,1칸을 조합한 정해진 8방향으로 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "canMoveArguments")
    void canMove(final Square target, final boolean expected) {
        // given
        final Knight knight = new Knight(Team.BLACK);
        final Square source = new Square(File.D, Rank.FOUR);

        // when
        final boolean canMove = knight.canMove(source, target);

        // then
        assertThat(canMove).isEqualTo(expected);
    }

    static Stream<Arguments> canMoveArguments() {
        return Stream.of(
                Arguments.of(new Square(File.E, Rank.TWO), true),
                Arguments.of(new Square(File.C, Rank.TWO), true),
                Arguments.of(new Square(File.B, Rank.THREE), true),
                Arguments.of(new Square(File.B, Rank.FIVE), true),
                Arguments.of(new Square(File.C, Rank.SIX), true),
                Arguments.of(new Square(File.E, Rank.SIX), true),
                Arguments.of(new Square(File.F, Rank.FIVE), true),
                Arguments.of(new Square(File.F, Rank.THREE), true));
    }

    @DisplayName("나이트는 2칸,1칸을 조합한 정해진 8방향으로 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "canNotMoveArguments")
    void canNotMove(final Square target, final boolean expected) {
        // given
        final Knight knight = new Knight(Team.BLACK);
        final Square source = new Square(File.D, Rank.FOUR);

        // when
        final boolean canMove = knight.canMove(source, target);

        // then
        assertThat(canMove).isEqualTo(expected);
    }

    static Stream<Arguments> canNotMoveArguments() {
        return Stream.of(
                Arguments.of(new Square(File.C, Rank.FOUR), false),
                Arguments.of(new Square(File.G, Rank.THREE), false),
                Arguments.of(new Square(File.G, Rank.FIVE), false));
    }
}
