package repository;

import static org.assertj.core.api.Assertions.assertThat;

import controller.constants.GameState;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.generator.ConnectionGenerator;

class GameStateRepositoryTest {
    private final GameStateRepository gameStateRepository = new GameStateRepository();

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = ConnectionGenerator.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = ConnectionGenerator.getConnection();
        connection.rollback();
    }

    @DisplayName("게임 상태를 저장한다.")
    @Test
    void saveGameState() {
        GameState gameState = GameState.STOPPED;

        gameStateRepository.save(gameState);

        assertThat(gameStateRepository.find()).isEqualTo(GameState.STOPPED);
    }

    @DisplayName("게임 상태를 업데이트한다.")
    @Test
    void updateGameState() {
        GameState gameState = GameState.NOT_STARTED;
        gameStateRepository.save(gameState);

        gameStateRepository.save(GameState.RUNNING);

        assertThat(gameStateRepository.find()).isEqualTo(GameState.RUNNING);
    }

    @DisplayName("게임 상태를 조회한다.")
    @Test
    void findGameState() {
        GameState gameState = GameState.NOT_STARTED;
        gameStateRepository.save(gameState);

        assertThat(gameStateRepository.find()).isEqualTo(GameState.NOT_STARTED);
    }

    @Test
    void test() {
        GameState gameState = null;
        boolean equals = gameState==GameState.STOPPED);
        assertThat(equals).isFalse();
    }
}
