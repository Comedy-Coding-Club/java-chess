package chess;

import chess.domain.board.ChessBoard;
import chess.domain.ChessBoardFixture;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.util.ScoreCalculator;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @DisplayName("기물들의 점수를 계산한다. 블랙폰은 1로 계산된다.")
    @Test
    void calculateScore1() {
        // given
        final ChessBoard chessBoard = ChessBoardFixture.chessBoardForScore1;

        // when
        final Map<Position, Piece> pieces = chessBoard.getPiecesWithPositionBy(Color.BLACK);
        final double score = ScoreCalculator.calculate(pieces);

        // then
        Assertions.assertThat(score).isEqualTo(20);
    }

    @DisplayName("기물들의 점수를 계산한다. 블랙폰의 일부가 0.5로 계산된다.")
    @Test
    void calculateScore2() {
        // given
        final ChessBoard chessBoard = ChessBoardFixture.chessBoardForScore2;

        // when
        final Map<Position, Piece> pieces = chessBoard.getPiecesWithPositionBy(Color.BLACK);
        final double score = ScoreCalculator.calculate(pieces);

        // then
        Assertions.assertThat(score).isEqualTo(20.5);
    }

    @DisplayName("기물들의 점수를 계산한다. 화이트폰은 1로 계산된다.")
    @Test
    void calculateScore3() {
        // given
        final ChessBoard chessBoard = ChessBoardFixture.chessBoardForScore1;

        // when
        final Map<Position, Piece> pieces = chessBoard.getPiecesWithPositionBy(Color.WHITE);
        final double score = ScoreCalculator.calculate(pieces);

        // then
        Assertions.assertThat(score).isEqualTo(19.5);
    }

    @DisplayName("기물들의 점수를 계산한다. 화이트폰의 일부는 0.5로 계산된다.")
    @Test
    void calculateScore4() {
        // given
        final ChessBoard chessBoard = ChessBoardFixture.chessBoardForScore2;

        // when
        final Map<Position, Piece> pieces = chessBoard.getPiecesWithPositionBy(Color.WHITE);
        final double score = ScoreCalculator.calculate(pieces);

        // then
        Assertions.assertThat(score).isEqualTo(19.5);
    }
}
