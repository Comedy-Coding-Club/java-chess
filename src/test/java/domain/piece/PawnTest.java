package domain.piece;

import domain.Camp;
import domain.File;
import domain.Rank;
import domain.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {
    @DisplayName("폰 움직임")
    @ParameterizedTest
    @MethodSource(value = "squareArgumentsMove")
    void canMove(final Camp color, final Square source, final Square target, final boolean result) {
        final Pawn pawn = new Pawn(color);

        final boolean canMove = pawn.canMove(source, target);

        assertThat(canMove).isEqualTo(result);
    }

    static Stream<Arguments> squareArgumentsMove() {
        return Stream.of(
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.THREE, File.D), true),
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.FOUR, File.D), true),
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.THREE, File.C), false),
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.THREE, File.E), false),
                Arguments.of(Camp.WHITE, new Square(Rank.THREE, File.D), new Square(Rank.FIVE, File.D), false),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.SIX, File.D), true),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.FIVE, File.D), true),
                Arguments.of(Camp.BLACK, new Square(Rank.SIX, File.D), new Square(Rank.FOUR, File.D), false),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.SIX, File.C), false),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.SIX, File.E), false)
        );
    }

    @DisplayName("폰 공격")
    @ParameterizedTest
    @MethodSource(value = "squareArgumentsAttack")
    void canAttack(final Camp color, final Square source, final Square target, final boolean result) {
        final Pawn pawn = new Pawn(color);

        final boolean canMove = pawn.canAttack(source, target);

        assertThat(canMove).isEqualTo(result);
    }

    static Stream<Arguments> squareArgumentsAttack() {
        return Stream.of(
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.THREE, File.D), false),
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.FOUR, File.D), false),
                Arguments.of(Camp.WHITE, new Square(Rank.THREE, File.D), new Square(Rank.FIVE, File.E), false),
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.THREE, File.C), true),
                Arguments.of(Camp.WHITE, new Square(Rank.TWO, File.D), new Square(Rank.THREE, File.E), true),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.SIX, File.D), false),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.FIVE, File.D), false),
                Arguments.of(Camp.BLACK, new Square(Rank.SIX, File.D), new Square(Rank.FOUR, File.D), false),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.SIX, File.C), true),
                Arguments.of(Camp.BLACK, new Square(Rank.SEVEN, File.D), new Square(Rank.SIX, File.E), true)
        );
    }
}
