package chess.domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreCalculatorTest {

    @Test
    @DisplayName("보드판의 남아있는 기물들의 점수를 계산한다.")
    void calculateScoreTest() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        Map<Position, Piece> board = Map.of(
                new Position(Row.RANK2, Column.H), new Piece(PieceType.ROOK, Color.WHITE),
                new Position(Row.RANK2, Column.A), new Piece(PieceType.WHITE_PAWN, Color.WHITE),
                new Position(Row.RANK3, Column.A), new Piece(PieceType.WHITE_PAWN, Color.WHITE),
                new Position(Row.RANK7, Column.B), new Piece(PieceType.BLACK_PAWN, Color.BLACK),
                new Position(Row.RANK7, Column.A), new Piece(PieceType.KNIGHT, Color.BLACK),
                new Position(Row.RANK8, Column.C), new Piece(PieceType.BLACK_PAWN, Color.BLACK)
        );

        Map<Color, Double> colorDoubleMap = scoreCalculator.calculateScore(board);

        assertAll(
                () -> Assertions.assertThat(colorDoubleMap.get(Color.WHITE)).isEqualTo(6),
                () -> Assertions.assertThat(colorDoubleMap.get(Color.BLACK)).isEqualTo(4.5)
        );
    }
}
