package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.position.File;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.util.ChessBoardInitializer;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._룩의 경우")
    @Test
    void existInWayRook() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.A, Rank.ONE);
        final Position nextPosition = new Position(File.A, Rank.FOUR);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._나이트의 경우")
    @Test
    void existInWayNight() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.B, Rank.ONE);
        final Position nextPosition = new Position(File.B, Rank.TWO);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._비숍의 경우")
    @Test
    void existInWayBishop() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.C, Rank.ONE);
        final Position nextPosition = new Position(File.C, Rank.TWO);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다.킹의 경우")
    @Test
    void existInWayKing() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.E, Rank.ONE);
        final Position nextPosition = new Position(File.E, Rank.TWO);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다.퀸의 경우")
    @Test
    void existInWayQueen() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.D, Rank.ONE);
        final Position nextPosition = new Position(File.D, Rank.THREE);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("전략상 이동할 수 없는 위치이다.")
    @Test
    void canNotMoveTo() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.A, Rank.TWO); // 폰
        final Position nextPosition = new Position(File.A, Rank.FIVE);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈칸이면 움직인다.")
    @Test
    void moveWhenEmpty() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.A, Rank.TWO); // 폰
        final Position nextPosition = new Position(File.A, Rank.FOUR);
        final Piece currentPiece = chessBoard.findPieceBy(currentPosition);

        // when
        chessBoard.move(currentPosition, nextPosition);

        // then
        assertThat(chessBoard.findPieceBy(nextPosition)).isEqualTo(currentPiece);
    }

    @DisplayName("빈칸인데 경로상에 기물이 존재하면 움직일 수 없다.")
    @Test
    void canNotMoveByExistingPiece() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.A, Rank.ONE);
        final Position nextPosition = new Position(File.A, Rank.FIVE);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상대 기물을 잡으러 움직인다.")
    @Test
    void moveToCatch() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position firstPosition = new Position(File.B, Rank.ONE); // 나이트
        final Position endPosition = new Position(File.D, Rank.FIVE);
        final Piece originPiece = chessBoard.findPieceBy(firstPosition);

        chessBoard.move(firstPosition, new Position(File.C, Rank.THREE)); //나이트
        chessBoard.move(new Position(File.D, Rank.SEVEN), new Position(File.D, Rank.FIVE));
        chessBoard.move(new Position(File.C, Rank.THREE), endPosition);

        // when && then
        final Piece currentPiece = chessBoard.findPieceBy(endPosition);
        assertThat(currentPiece).isEqualTo(originPiece);
    }

    @DisplayName("상대 기물을 잡으러 움직이는 도중에 기물이 존재하면 움직일 수 없다.")
    @Test
    void canNotMoveToCatchByExistingPiece() {
        // given
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final Position currentPosition = new Position(File.A, Rank.ONE); // 룩
        final Position nextPosition = new Position(File.A, Rank.EIGHT);

        // when && then
        assertThatThrownBy(() -> chessBoard.move(currentPosition, nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("블랙 기물과 위치들을 반환한다.")
    @Test
    void getBlackPiecesWithPositionBy() {
        // given
        final ChessBoard chessBoard = ChessBoardFixture.chessBoardForScore1;

        // when
        final Map<Position, Piece> piecesWithPosition = chessBoard.getPiecesWithPositionBy(Color.BLACK);

        // then
        assertThat(piecesWithPosition).containsOnlyKeys(
                new Position(File.B, Rank.EIGHT), new Position(File.C, Rank.EIGHT),
                new Position(File.A, Rank.SEVEN), new Position(File.C, Rank.SEVEN),
                new Position(File.D, Rank.SEVEN), new Position(File.B, Rank.SIX),
                new Position(File.E, Rank.SIX)
        );
    }

    @DisplayName("화이트 기물과 위치들을 반환한다.")
    @Test
    void getWhitePiecesWithPositionBy() {
        // given
        final ChessBoard chessBoard = ChessBoardFixture.chessBoardForScore1;

        // when
        final Map<Position, Piece> piecesWithPosition = chessBoard.getPiecesWithPositionBy(Color.WHITE);

        // then
        assertThat(piecesWithPosition).containsOnlyKeys(
                new Position(File.F, Rank.FOUR), new Position(File.G, Rank.FOUR),
                new Position(File.F, Rank.THREE), new Position(File.H, Rank.THREE),
                new Position(File.F, Rank.TWO), new Position(File.G, Rank.TWO),
                new Position(File.E, Rank.ONE), new Position(File.H, Rank.ONE)
        );
    }
}
