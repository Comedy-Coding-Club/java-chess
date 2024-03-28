package domain.game;

import static fixture.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;

import domain.piece.Color;
import domain.position.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("source에 위치한 piece를 target으로 이동한다.")
    @Test
    void movePieceToTarget() {
        ChessBoard chessBoard = new ChessBoard();

        chessBoard.move(B2, B3);

        Assertions.assertAll(
                () -> assertThat(chessBoard.isNotEmptyAt(B3)).isTrue(),
                () -> assertThat(chessBoard.isNotEmptyAt(B3)).isTrue()
        );
    }

    @DisplayName("source에 piece가 없다면 에러를 반환한다.")
    @Test
    void movePieceIfSourceHasNotPiece() {
        ChessBoard chessBoard = new ChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(B3, B4, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("옮기고자 하는 위치에 같은 진영의 Piece가 있다면 에러를 반환한다.")
    @Test
    void hasSameColorPiece() {
        ChessBoard chessBoard = new ChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(A1, A2, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 위치로의 이동이라면 에러를 반환한다.")
    @Test
    void moveToSamePosition() {
        ChessBoard chessBoard = new ChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(B1, B1, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("앞에 다른 진영의 기물이 있는 경우 폰이 이동하지 못한다.")
    @Test
    void movePawnWhenFrontPositionHasOtherPiece() {
        Position whiteSourcePosition = B2;
        Position whiteTargetPosition = B4;

        Position blackSourcePosition = B7;
        Position balckTargetPosition = B5;

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.move(whiteSourcePosition, whiteTargetPosition);
        chessBoard.move(blackSourcePosition, balckTargetPosition);

        assertThatThrownBy(() -> chessBoard.checkRoute(whiteTargetPosition, balckTargetPosition, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("대각선에 다른 진영의 기물이 있는 경우 폰이 이동할 수 있다.")
    @Test
    void movePawnWhenDiagonalPositionHasOtherPiece() {
        Position whiteSourcePosition = B2;
        Position whiteTargetPosition = B4;

        Position blackSourcePosition = C7;
        Position balckTargetPosition = C5;

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.move(whiteSourcePosition, whiteTargetPosition);
        chessBoard.move(blackSourcePosition, balckTargetPosition);

        assertThatCode(() -> chessBoard.checkRoute(whiteTargetPosition, balckTargetPosition, Color.WHITE))
                .doesNotThrowAnyException();
    }

    @DisplayName("나이트를 제외한 기물은 이동하는 경로에 기물이 있으면 이동하지 못한다.")
    @Test
    void isOverlappedPath() {
        ChessBoard chessBoard = new ChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(A1, A3, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("나이트는 이동하는 경로에 기물이 있어도 이동할 수 있다.")
    @Test
    void knightCanJump() {
        ChessBoard chessBoard = new ChessBoard();
        assertThatCode(() -> chessBoard.checkRoute(B1, C3, Color.WHITE))
                .doesNotThrowAnyException();
    }
}
