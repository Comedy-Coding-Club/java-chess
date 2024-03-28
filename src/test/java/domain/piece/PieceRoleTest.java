package domain.piece;

import static fixture.PositionFixture.*;

import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.PieceRole;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceRoleTest {
    @DisplayName("이동 성공 테스트")
    @Nested
    class SuccessTest {
        @DisplayName("킹이 (b,1)에서 (b,2)로 이동한다.")
        @Test
        void canKingMove() {
            PieceRole king = new King();

            Assertions.assertThat(king.canMove(B1, B2)).isTrue();
        }

        @DisplayName("퀸이 (b,1)에서 (b,7)로 이동한다.")
        @Test
        void canQueenMove() {
            PieceRole queen = new Queen();

            Assertions.assertThat(queen.canMove(B1, B7)).isTrue();
        }

        @DisplayName("룩이 (b,1)에서 (b,7)로 이동한다.")
        @Test
        void canRookMove() {
            PieceRole rook = new Rook();

            Assertions.assertThat(rook.canMove(B1, B7)).isTrue();
        }

        @DisplayName("나이트가 (b,1)에서 (c,3)로 이동한다.")
        @Test
        void canKnightMove() {
            PieceRole knight = new Knight();

            Assertions.assertThat(knight.canMove(B1, C3)).isTrue();
        }

        @DisplayName("비숍이 (b,1)에서 (c,2)로 이동한다.")
        @Test
        void canBishopMove() {
            PieceRole bishop = new Bishop();

            Assertions.assertThat(bishop.canMove(B1, C2)).isTrue();
        }

        @DisplayName("흰색 폰이 (c,2)에서 (c,3)로 이동한다.")
        @Test
        void canPawnMove() {
            PieceRole pawn = new WhitePawn();

            Assertions.assertThat(pawn.canMove(C2, C3)).isTrue();
        }
    }

    @DisplayName("이동 실패 테스트")
    @Nested
    class FailTest {
        @DisplayName("킹이 (b,1)에서 (c,2)로 이동하지 못한다.")
        @Test
        void cannotKingMove() {
            PieceRole king = new King();

            Assertions.assertThat(king.canMove(B1, C2)).isFalse();
        }

        @DisplayName("퀸이 (b,1)에서 (c,3)로 이동하지 못한다.")
        @Test
        void cannotQueenMove() {
            PieceRole queen = new Queen();

            Assertions.assertThat(queen.canMove(B1, C3)).isFalse();
        }

        @DisplayName("룩이 (c,1)에서 (b,2)로 이동하지 못한다.")
        @Test
        void cannotRookMove() {
            PieceRole rook = new Rook();

            Assertions.assertThat(rook.canMove(C1, B2)).isFalse();
        }

        @DisplayName("나이트가 (b,1)에서 (b,2)로 이동하지 못한다.")
        @Test
        void cannotKnightMove() {
            PieceRole knight = new Knight();

            Assertions.assertThat(knight.canMove(B1, B2)).isFalse();
        }

        @DisplayName("비숍이 (b,1)에서 (b,2)로 이동하지 못한다.")
        @Test
        void cannotBishopMove() {
            PieceRole bishop = new Bishop();

            Assertions.assertThat(bishop.canMove(B1, B2)).isFalse();
        }

        @DisplayName("검은색 폰이 (c,2)에서 (c,3)로 이동하지 못한다.")
        @Test
        void canPawnMove() {
            PieceRole pawn = new BlackPawn();

            Assertions.assertThat(pawn.canMove(C2, C3)).isFalse();
        }
    }
}
