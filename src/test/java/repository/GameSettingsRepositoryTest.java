package repository;

import static org.assertj.core.api.Assertions.assertThat;

import controller.constants.GameState;
import domain.game.Turn;
import domain.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameSettingsRepositoryTest {
    private final GameSettingsRepository gameSettingsRepository = new GameSettingsRepository();

    @DisplayName("현재 차례의 정보를 저장한다.")
    @Test
    void saveTurn() {
        Turn turn = new Turn(Color.BLACK);

        int rows = gameSettingsRepository.saveTurn(turn);

        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("게임 상태를 저장한다.")
    @Test
    void saveGameState() {
        GameState gameState = GameState.STOPPED;

        int rows = gameSettingsRepository.saveGameState(gameState);

        assertThat(rows).isEqualTo(1);
    }
}
