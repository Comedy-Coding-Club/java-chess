package domain.game;

import static fixture.PiecePositionFixture.PIECE_POSITION_FOR_BLACK_WINS;
import static fixture.PiecePositionFixture.PIECE_POSITION_FOR_WHITE_WINS;
import static org.assertj.core.api.Assertions.assertThat;

import controller.constants.Winner;
import controller.dto.GameResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RefereeTest {
    @DisplayName("검은색 진영의 기물의 점수가 높은 경우 검은색 진영이 이긴다.")
    @Test
    void blackWins() {
        ChessBoard chessBoard = new ChessBoard(PIECE_POSITION_FOR_BLACK_WINS);
        Referee referee = new Referee(chessBoard);
        GameResult gameResult = referee.judge();
        assertThat(gameResult).isEqualTo(new GameResult(Winner.BLACK, 20, 19.5));
    }

    @DisplayName("하얀색 진영의 기물의 점수가 높은 경우 하얀색 진영이 이긴다.")
    @Test
    void whiteWins() {
        ChessBoard chessBoard = new ChessBoard(PIECE_POSITION_FOR_WHITE_WINS);
        Referee referee = new Referee(chessBoard);
        GameResult gameResult = referee.judge();
        assertThat(gameResult).isEqualTo(new GameResult(Winner.WHITE, 11, 19.5));
    }
}
