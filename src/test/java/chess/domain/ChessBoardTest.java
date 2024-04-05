package chess.domain;

import static chess.Fixtures.GAMING_PIECES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import chess.Fixtures;
import chess.domain.piece.Color;
import chess.domain.piece.File;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    final ChessBoard chessBoard = ChessBoardMaker.init();

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._룩")
    @Test
    void existInWayRook() {
        // given
        final Position sourcePosition = new Position(File.A, Rank.ONE);
        final Position targetPosition = new Position(File.A, Rank.FOUR);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._나이트")
    @Test
    void existInWayNight() {
        // given
        final Position sourcePosition = new Position(File.B, Rank.ONE);
        final Position targetPosition = new Position(File.B, Rank.TWO);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._비숍")
    @Test
    void existInWayBishop() {
        // given
        final Position sourcePosition = new Position(File.C, Rank.ONE);
        final Position targetPosition = new Position(File.C, Rank.TWO);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._킹")
    @Test
    void existInWayKing() {
        // given
        final Position sourcePosition = new Position(File.E, Rank.ONE);
        final Position targetPosition = new Position(File.E, Rank.TWO);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로에 기물이 존재하면 예외를 발생시킨다._퀸")
    @Test
    void existInWayQueen() {
        // given
        final Position sourcePosition = new Position(File.D, Rank.ONE);
        final Position targetPosition = new Position(File.D, Rank.THREE);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("전략상 이동할 수 없는 위치이다._폰")
    @Test
    void canNotMoveTo() {
        // given
        final Position sourcePosition = new Position(File.A, Rank.TWO);
        final Position targetPosition = new Position(File.A, Rank.FIVE);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈칸이면 움직인다._폰")
    @Test
    void moveWhenEmpty() {
        // given
        final Position sourcePosition = new Position(File.A, Rank.TWO);
        final Position targetPosition = new Position(File.A, Rank.FOUR);
        final Piece sourcePiece = chessBoard.findPieceBy(sourcePosition);

        // when
        chessBoard.tryMove(sourcePosition, targetPosition);

        // then
        assertThat(chessBoard.getPieces().get(targetPosition)).isEqualTo(sourcePiece);
    }

    @DisplayName("빈칸인데 경로상에 기물이 존재하면 움직일 수 없다._룩")
    @Test
    void canNotMoveByExistingPiece() {
        // given
        final Position sourcePosition = new Position(File.A, Rank.ONE);
        final Position targetPosition = new Position(File.A, Rank.FIVE);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상대 기물을 잡으러 움직인다._나이트")
    @Test
    void moveToCatch() {
        // given
        Position sourcePosition = new Position(File.B, Rank.ONE);
        final Piece originPiece = chessBoard.findPieceBy(sourcePosition);
        Position targetPosition = new Position(File.C, Rank.THREE);
        chessBoard.tryMove(sourcePosition, targetPosition);

        sourcePosition = targetPosition;
        targetPosition = new Position(File.D, Rank.FIVE);
        chessBoard.tryMove(sourcePosition, targetPosition);

        sourcePosition = targetPosition;
        targetPosition = new Position(File.E, Rank.SEVEN);
        chessBoard.tryMove(sourcePosition, targetPosition);

        // when && then
        final Piece sourcePiece = chessBoard.findPieceBy(targetPosition);
        assertThat(sourcePiece).isEqualTo(originPiece);
    }

    @DisplayName("상대 기물을 잡으러 움직이는 도중에 기물이 존재하면 움직일 수 없다_룩")
    @Test
    void canNotMoveToCatchByExistingPiece_bishop() {
        // given
        final Position sourcePosition = new Position(File.C, Rank.ONE);
        final Position targetPosition = new Position(File.C, Rank.THREE);

        // when && then
        assertThatThrownBy(() -> chessBoard.tryMove(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /*
    RNB.KBNR -> K를 잡음
    ...P.PPP
    .P......
    P.P.q.b. -> q이
    .rpQP.p.
    p.n.kp..
    .p.pp..p
    ..b...nr
     */
    @DisplayName("킹이 잡혔는지 확인한다.")
    @Test
    void doesKingDead() {
        // given
        final ChessBoard newChessBoard = new ChessBoard(new HashMap<>(GAMING_PIECES));

        // when
        newChessBoard.tryMove("e5", "e8");
        final boolean doesKingDead = newChessBoard.doesKingDead();

        // then
        assertThat(doesKingDead).isTrue();
    }

    /*
    RNB.KBNR
    ...P.PPP
    .P......
    P.P.q.b.
    .rpQP.p.
    p.n.kp..
    .p.pp..p
    ..b...nr
     */
    @DisplayName("킹이 잡히지 않음을 확인한다.")
    @Test
    void doesNotKingDead() {
        // given
        final ChessBoard chessBoard = new ChessBoard(new HashMap<>(GAMING_PIECES));

        // when
        chessBoard.tryMove("e5", "e6");
        final boolean doesKingDead = chessBoard.doesKingDead();

        // then
        assertThat(doesKingDead).isFalse();
    }

    /*
    RNB.KBNR
    ...P.PPP
    .P......
    P.P.q.b. -> 3. q가 N을 잡는다. = black -2.5
    .rpQP.p. -> 2. Q가 p를 잡는다. = white -0.5, 0.5
    p.n.kp.. -> 1. p가 P를 잡는다. = black -1
    .p.pp..p
    ..b...nr
     */
    @DisplayName("잡힌 말들을 가져온다.")
    @Test
    void getScore() {
        // given
        final ChessBoard chessBoard = new ChessBoard(new HashMap<>(Fixtures.GAMING_PIECES));

        // when
        chessBoard.tryMove("f3", "e4");
        chessBoard.tryMove("d4", "e4");
        chessBoard.tryMove("e5", "b8");
        Map<Color, Double> scores = chessBoard.getScores();

        // then
        assertThat(scores).contains(entry(Color.WHITE, 37.0), entry(Color.BLACK, 34.5));
    }
}
