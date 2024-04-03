package repository.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.game.ChessGame;
import domain.game.GameState;
import domain.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ChessGameDaoTest {

    private final ChessGameDao chessGameDao = new ChessGameDao();

    @DisplayName("1. DB에 chessGame을 저장한다.")
    @Test
    void saveChessGame() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(() -> chessGameDao.save(Color.WHITE, GameState.READY));
    }

    @DisplayName("2. DB에서 chessGame의 Status를 찾는다.")
    @Test
    void findGameStatusById() {
        GameState gameState = chessGameDao.findGameStatusById();
        assertThat(gameState).isEqualTo(GameState.READY);
    }

    @DisplayName("3. DB에서 chessGame의 Color(턴)를 찾는다.")
    @Test
    void findColorById() {
        Color color = chessGameDao.findColorById();
        assertThat(color).isEqualTo(Color.WHITE);
    }

    @DisplayName("4. DB에서 chessGame의 Status를 업데이트한다.")
    @Test
    void updateGameStatus() {
        chessGameDao.updateGameStatus(GameState.RUNNING);

        GameState gameState = chessGameDao.findGameStatusById();
        assertThat(gameState).isEqualTo(GameState.RUNNING);
    }

    @DisplayName("5. DB에서 chessGame의 Color를 업데이트한다.")
    @Test
    void updateColor() {
        chessGameDao.updateColor(Color.BLACK);

        Color color = chessGameDao.findColorById();
        assertThat(color).isEqualTo(Color.BLACK);
    }


    @DisplayName("6. DB에서 chessGame을 삭제한다.")
    @Test
    void delete() {
        assertThat(chessGameDao.delete()).isTrue();
    }
}
