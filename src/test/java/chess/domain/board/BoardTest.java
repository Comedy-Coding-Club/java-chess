package chess.domain.board;

import static chess.domain.location.LocationFixture.A1;
import static chess.domain.location.LocationFixture.A2;
import static chess.domain.location.LocationFixture.A3;
import static chess.domain.location.LocationFixture.A4;
import static chess.domain.location.LocationFixture.A5;
import static chess.domain.location.LocationFixture.A6;
import static chess.domain.location.LocationFixture.A7;
import static chess.domain.location.LocationFixture.A8;
import static chess.domain.location.LocationFixture.B2;
import static chess.domain.location.LocationFixture.B3;
import static chess.domain.location.LocationFixture.B6;
import static chess.domain.location.LocationFixture.B7;
import static chess.domain.location.LocationFixture.C2;
import static chess.domain.location.LocationFixture.E8;
import static chess.domain.location.LocationFixture.G2;
import static chess.domain.location.LocationFixture.G3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.Color;
import chess.domain.piece.Score;
import chess.domain.piece.implement.Bishop;
import chess.domain.piece.implement.BlackPawn;
import chess.domain.piece.implement.King;
import chess.domain.piece.implement.Knight;
import chess.domain.piece.implement.Queen;
import chess.domain.piece.implement.Rook;
import chess.domain.piece.implement.WhitePawn;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    private static final Board BOARD = new Board();

    @DisplayName("흑은 백의 기물을 움직일 수 없다.")
    @Test
    void opponentBlackPieceMoveTest() {
        assertThatThrownBy(() -> BOARD.move(B2, B3, Color.BLACK))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 기물만 움직일 수 있습니다.");
    }

    @DisplayName("백은 흑의 기물을 움직일 수 없다.")
    @Test
    void opponentWhitePieceMoveTest() {
        assertThatThrownBy(() -> BOARD.move(B7, B6, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 기물만 움직일 수 있습니다.");
    }

    @DisplayName("보드에서 점수를 계산할 수 있다.")
    @Nested
    class CalculateScoreTest {

        @DisplayName("일반 점수 계산 테스트")
        @Nested
        class NormalTest {
            /*
            R...K...
            P.......
            ........
            ........
            ........
            ........
            ........
            rnbqk...
             */
            Board board = new Board(Map.of(
                    A8, new Rook(Color.BLACK),
                    E8, new King(Color.BLACK),
                    A7, new BlackPawn(),
                    A1, new Rook(Color.WHITE),
                    A2, new Knight(Color.WHITE),
                    A3, new Bishop(Color.WHITE),
                    A4, new Queen(Color.WHITE),
                    A5, new King(Color.WHITE)
            ));

            @DisplayName("흑의 점수는 6점이다.")
            @Test
            void calculateBlackTest() {
                assertThat(board.calculateScore(Color.BLACK)).isEqualTo(new Score(6));
            }

            @DisplayName("백의 점수는 19.5점이다.")
            @Test
            void calculateWhiteTest() {
                assertThat(board.calculateScore(Color.WHITE)).isEqualTo(new Score(19.5));
            }
        }

        @DisplayName("폰 특수 점수 계산 테스트")
        @Nested
        class PawnTest {
            /*
            R...K...
            P.......
            P.......
            ........
            ........
            ......p.
            ..p...p.
            rn..k...
             */
            Board board = new Board(Map.of(
                    A8, new Rook(Color.BLACK),
                    E8, new King(Color.BLACK),
                    A7, new BlackPawn(),
                    A6, new BlackPawn(),
                    G3, new WhitePawn(),
                    C2, new WhitePawn(),
                    G2, new WhitePawn(),
                    A1, new Rook(Color.WHITE),
                    A2, new Knight(Color.WHITE),
                    A5, new King(Color.WHITE)
            ));

            @DisplayName("흑의 점수는 6점이다.")
            @Test
            void calculateBlackTest() {
                assertThat(board.calculateScore(Color.BLACK)).isEqualTo(new Score(6));
            }

            @DisplayName("백의 점수는 9.5점이다.")
            @Test
            void calculateWhiteTest() {
                assertThat(board.calculateScore(Color.WHITE)).isEqualTo(new Score(9.5));
            }
        }
    }
}
