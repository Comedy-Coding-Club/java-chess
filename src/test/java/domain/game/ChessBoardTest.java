package domain.game;

import static fixture.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;

import controller.constants.*;
import domain.piece.*;
import domain.piece.piecerole.*;
import domain.position.*;
import java.util.*;
import org.junit.jupiter.api.*;

class ChessBoardTest {

    @DisplayName("이동 테스트")
    @Nested
    class MoveTest {
        @DisplayName("(b,2)에 위치한 piece를 (b,3)로 이동한다.")
        @Test
        void movePieceToTarget() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            chessBoard.move(B2, B3);

            Piece piece = chessBoard.findPieceByPosition(B3);
            assertThat(piece).isEqualTo(new Piece(WhitePawn.create(), Color.WHITE));
        }

        @DisplayName("source에 piece가 없다면 에러를 반환한다.")
        @Test
        void movePieceIfSourceHasNotPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThatThrownBy(() -> chessBoard.move(C3, C4))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("옮기고자 하는 위치에 같은 진영의 Piece가 있다면 에러를 반환한다.")
        @Test
        void hasSameColorPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(D2, D3);

            assertThatThrownBy(() -> chessBoard.move(D1, D3))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("같은 위치로의 이동이라면 에러를 반환한다.")
        @Test
        void moveToSameSquare() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThatThrownBy(() -> chessBoard.move(B1, B1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("같은 진영의 기물이 있는 곳으로 이동한다면 에러를 반환한다.")
        @Test
        void moveToSamePiecePosition() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();

            assertThatThrownBy(() -> chessBoard.move(D1, D2))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("앞에 다른 진영의 기물이 있는 경우 폰이 이동하지 못한다.")
        @Test
        void movePawnWhenFrontSquareHasOtherPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B4);
            chessBoard.move(B7, B5);

            assertThatThrownBy(() -> chessBoard.move(B4, B5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("대각선에 다른 진영의 기물이 있는 경우 폰이 이동할 수 있다.")
        @Test
        void movePawnWhenDiagonalSquareHasOtherPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B4);
            chessBoard.move(C7, C5);
            chessBoard.move(B4, C5);

            Piece piece = chessBoard.findPieceByPosition(C5);
            assertThat(piece).isEqualTo(new Piece(WhitePawn.create(), Color.WHITE));
        }

        @DisplayName("대각선에 다른 진영의 기물이 없는 경우 폰이 이동할 수 없다.")
        @Test
        void cannotMovePawnWhenDiagonalSquareHasNotOtherPiece() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B4);
            chessBoard.move(B7, B6);
            assertThatThrownBy(() -> chessBoard.move(B4, C5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("나이트를 제외한 기물은 이동하는 경로에 기물이 있으면 이동하지 못한다.")
        @Test
        void isOverlappedPath() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(D2, D3);
            chessBoard.move(B7, B6);

            assertThatThrownBy(() -> chessBoard.move(D1, D5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("나이트는 이동하는 경로에 기물이 있어도 이동할 수 있다.")
        @Test
        void knightCanJump() {
            ChessBoard chessBoard = ChessBoardGenerator.generate();
            chessBoard.move(B2, B3);
            chessBoard.move(B7, B5);
            chessBoard.move(B1, C3);

            assertThat(chessBoard.findPieceByPosition(C3)).isEqualTo(new Piece(Knight.create(), Color.WHITE));
        }
    }

    @DisplayName("특정 위치에 기물이 있음을 확인한다.")
    @Test
    void hasPiece() {
        ChessBoard chessBoard = ChessBoardGenerator.generate();

        assertThat(chessBoard.hasPiece(A1)).isTrue();
    }

    @DisplayName("특정 위치에 기물이 없음을 확인한다.")
    @Test
    void hasNotPiece() {
        ChessBoard chessBoard = ChessBoardGenerator.generate();

        assertThat(chessBoard.hasPiece(A5)).isFalse();
    }

    @DisplayName("킹이 잡힌 경우 게임이 종료되는 상태를 반환한다.")
    @Test
    void gameEndsWhenKingCaptured() {
        ChessBoard chessBoard = new ChessBoard(generatePiecePositionForCapturingKing());
        GameState gameState = chessBoard.move(C8, B8);

        assertThat(gameState).isEqualTo(GameState.STOPPED);
    }

    @DisplayName("킹이 잡힌 경우 게임이 종료되는 상태를 반환한다.")
    @Test
    void gameIsRunningWhenKingIsNotCaptured() {
        ChessBoard chessBoard = new ChessBoard(generatePiecePositionForCapturingKing());
        GameState gameState = chessBoard.move(C8, D8);

        assertThat(gameState).isEqualTo(GameState.RUNNING);
    }

    /*
     * .Kr.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * abcdefgh
     */
    private Map<Position, Piece> generatePiecePositionForCapturingKing() {
        return new HashMap<>(
                Map.ofEntries(
                        Map.entry(B8, new Piece(King.create(), Color.BLACK)),
                        Map.entry(C8, new Piece(Rook.create(), Color.WHITE)),
                        Map.entry(A7, new Piece(BlackPawn.create(), Color.BLACK)),
                        Map.entry(C7, new Piece(BlackPawn.create(), Color.BLACK)),
                        Map.entry(D7, new Piece(Bishop.create(), Color.BLACK)),
                        Map.entry(B6, new Piece(BlackPawn.create(), Color.BLACK)),
                        Map.entry(E6, new Piece(Queen.create(), Color.BLACK)),
                        Map.entry(F4, new Piece(Knight.create(), Color.WHITE)),
                        Map.entry(G4, new Piece(Queen.create(), Color.WHITE)),
                        Map.entry(F3, new Piece(WhitePawn.create(), Color.WHITE)),
                        Map.entry(H3, new Piece(WhitePawn.create(), Color.WHITE)),
                        Map.entry(F2, new Piece(WhitePawn.create(), Color.WHITE)),
                        Map.entry(G2, new Piece(WhitePawn.create(), Color.WHITE)),
                        Map.entry(E1, new Piece(Rook.create(), Color.WHITE)),
                        Map.entry(F1, new Piece(King.create(), Color.WHITE))
                )
        );
    }
}
