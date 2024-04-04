package repository.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.game.ChessGame;
import domain.game.GameState;
import domain.piece.Color;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ChessGameDaoTest {
    private final FakeChessGameDao chessGameDao = new FakeChessGameDao();

    @BeforeEach
    void save() {
        ChessGame chessGame = new ChessGame();
        chessGameDao.save(chessGame.getColor(), chessGame.getGameState());
    }

    @AfterEach
    void delete() {
        chessGameDao.delete();
    }

    @DisplayName("1. DB에 chessGame을 저장한다.")
    @Test
    void saveChessGame() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(() -> chessGameDao.save(Color.WHITE, GameState.READY));
    }

    @DisplayName("2. DB에서 chessGame의 Status를 찾는다.")
    @Test
    void findGameStatusById() {
        Optional<GameState> gameState = chessGameDao.findGameStatusById();
        assertThat(gameState).contains(GameState.READY);
    }

    @DisplayName("3. DB에서 chessGame의 Color(턴)를 찾는다.")
    @Test
    void findColorById() {
        Optional<Color> color = chessGameDao.findColorById();
        assertThat(color).contains(Color.WHITE);
    }

    @DisplayName("4. DB에서 chessGame의 Status를 업데이트한다.")
    @Test
    void updateGameStatus() {
        chessGameDao.updateGameStatus(GameState.RUNNING);

        Optional<GameState> gameState = chessGameDao.findGameStatusById();
        assertThat(gameState).contains(GameState.RUNNING);
    }

    @DisplayName("5. DB에서 chessGame의 Color를 업데이트한다.")
    @Test
    void updateColor() {
        chessGameDao.updateColor(Color.BLACK);

        Optional<Color> color = chessGameDao.findColorById();
        assertThat(color).contains(Color.BLACK);
    }


    @DisplayName("6. DB에서 chessGame을 삭제한다.")
    @Test
    void deleteChessGame() {
        chessGameDao.delete();
        assertAll(
                () -> assertThat(chessGameDao.findColorById()).isEmpty(),
                () -> assertThat(chessGameDao.findGameStatusById()).isEmpty()
        );

    }
}
