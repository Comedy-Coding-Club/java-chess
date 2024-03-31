package chess.domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.HashMap;
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

    /**
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     *
     * abcdefgh
     */
    @Test
    @DisplayName("보드판의 남아있는 기물들의 점수를 계산한다.")
    void calculateScore2Test() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        Map<Position, Piece> board = new HashMap<>();

        board.put(new Position(Row.RANK8, Column.B), new Piece(PieceType.KING, Color.BLACK));
        board.put(new Position(Row.RANK8, Column.C), new Piece(PieceType.ROOK, Color.BLACK));
        board.put(new Position(Row.RANK7, Column.A), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        board.put(new Position(Row.RANK7, Column.C), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        board.put(new Position(Row.RANK7, Column.D), new Piece(PieceType.BISHOP, Color.BLACK));
        board.put(new Position(Row.RANK6, Column.B), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        board.put(new Position(Row.RANK6, Column.E), new Piece(PieceType.QUEEN, Color.BLACK));

        board.put(new Position(Row.RANK4, Column.F), new Piece(PieceType.KNIGHT, Color.WHITE));
        board.put(new Position(Row.RANK4, Column.G), new Piece(PieceType.QUEEN, Color.WHITE));
        board.put(new Position(Row.RANK3, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        board.put(new Position(Row.RANK3, Column.H), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        board.put(new Position(Row.RANK2, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        board.put(new Position(Row.RANK2, Column.G), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        board.put(new Position(Row.RANK1, Column.E), new Piece(PieceType.ROOK, Color.WHITE));
        board.put(new Position(Row.RANK1, Column.F), new Piece(PieceType.KING, Color.WHITE));

        Map<Color, Double> colorDoubleMap = scoreCalculator.calculateScore(board);

        assertAll(
                () -> Assertions.assertThat(colorDoubleMap.get(Color.WHITE)).isEqualTo(19.5),
                () -> Assertions.assertThat(colorDoubleMap.get(Color.BLACK)).isEqualTo(20)
        );
    }
}
