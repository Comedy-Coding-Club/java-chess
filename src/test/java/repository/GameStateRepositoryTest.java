package repository;

import static org.assertj.core.api.Assertions.assertThat;

import controller.constants.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStateRepositoryTest {
    private final GameStateRepository gameStateRepository = new GameStateRepository();

    @DisplayName("게임 상태를 저장한다.")
    @Test
    void saveGameState() {
        GameState gameState = GameState.STOPPED;

        int rows = gameStateRepository.save(gameState);

        assertThat(rows).isEqualTo(1);
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
}
