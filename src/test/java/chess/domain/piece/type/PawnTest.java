package chess.domain.piece.type;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Color;
import chess.domain.piece.File;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PawnTest {

//    @DisplayName("화이트폰은 처음에는 앞으로 두칸 이동할 수 있다.")
//    @Test
//    void canWhiteMoveTwoAtFirst() {
//        // given
//        final Pawn pawn = new Pawn(Color.WHITE, new Position(File.D, Rank.TWO));
//
//        // when
//        final boolean canMove = pawn.canMoveTo(new Position(File.D, Rank.FOUR));
//
//        // then
//        assertThat(canMove).isTrue();
//    }
//
//    @DisplayName("블랙폰은 처음에 앞으로 두칸 이동할 수 있다.")
//    @Test
//    void canBlackMoveTwoAtFirst() {
//        // given
//        final Pawn pawn = new Pawn(Color.BLACK, new Position(File.D, Rank.SEVEN));
//
//        // when
//        final boolean canMove = pawn.canMoveTo(new Position(File.D, Rank.FIVE));
//
//        // then
//        assertThat(canMove).isTrue();
//    }
//
//    @DisplayName("폰은 처음이 아니면 두칸을 이동할 수 없다.")
//    @Test
//    void canNotMoveTwo() {
//        // given
//        final Pawn pawn = new Pawn(Color.WHITE, new Position(File.D, Rank.FOUR));
//
//        // when
//        final boolean canMove = pawn.canMoveTo(new Position(File.D, Rank.SIX));
//
//        // then
//        assertThat(canMove).isFalse();
//    }
//
//    @DisplayName("폰은 앞으로 한칸 이동할 수 있다.")
//    @Test
//    void canMoveForwardOneStep() {
//        // given
//        final Pawn pawn = new Pawn(Color.WHITE, new Position(File.D, Rank.FIVE));
//
//        // when
//        final boolean canMove = pawn.canMoveTo(new Position(File.D, Rank.SIX));
//
//        // then
//        assertThat(canMove).isTrue();
//    }
//
//    @DisplayName("폰은 앞으로 한칸 이외에는 이동할 수 없다.")
//    @Test
//    void canNotMoveForwardOverOneStep() { // TODO: 한칸인데 뒤로가는 경우 추가
//        // given
//        final Pawn pawn = new Pawn(Color.WHITE, new Position(File.D, Rank.FIVE));
//
//        // when
//        final boolean canMove = pawn.canMoveTo(new Position(File.D, Rank.EIGHT));
//
//        // then
//        assertThat(canMove).isFalse();
//    }
//
//    @DisplayName("도착 지점이 두칸 앞일 때 위치들을 반환한다.")
//    @Test
//    void getRouteForward() {
//        // given
//        final Pawn pawn = new Pawn(Color.WHITE, new Position(File.A, Rank.TWO));
//
//        // when
//        final Set<Position> positions = pawn.getRoute(new Position(File.A, Rank.FOUR));
//
//        // then
//        assertThat(positions).containsExactlyInAnyOrder(
//                new Position(File.A, Rank.THREE)
//        );
//
//    }
}
