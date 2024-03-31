package repository;

import static org.assertj.core.api.Assertions.assertThat;

import controller.constants.GameState;
import domain.game.Turn;
import domain.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameSettingsRepositoryTest {
    private final GameSettingRepository gameSettingRepository = new GameSettingRepository();

    @DisplayName("현재 차례의 정보를 저장한다.")
    @Test
    void saveTurn() {
        Turn turn = new Turn(Color.BLACK);

        int rows = gameSettingRepository.saveTurn(turn);

        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("현재 차례의 정보를 업데이트한다.")
    @Test
    void updateTurn() {
        Turn first = new Turn(Color.WHITE);
        Turn second = new Turn(Color.BLACK);
        gameSettingRepository.saveTurn(first);

        gameSettingRepository.updateTurn(second);

        assertThat(gameSettingRepository.findTurn()).isEqualTo(second);
    }

    @DisplayName("게임 상태를 저장한다.")
    @Test
    void saveGameState() {
        GameState gameState = GameState.STOPPED;

        int rows = gameSettingRepository.saveGameState(gameState);

        assertThat(rows).isEqualTo(1);
    }
}
