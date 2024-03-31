package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Turn;
import domain.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnRepositoryTest {
    private final TurnRepository turnRepository = new TurnRepository();

    @DisplayName("현재 차례의 정보를 저장한다.")
    @Test
    void saveTurn() {
        Turn turn = new Turn(Color.BLACK);

        int rows = turnRepository.save(turn);

        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("현재 차례의 정보를 업데이트한다.")
    @Test
    void updateTurn() {
        Turn first = new Turn(Color.WHITE);
        Turn second = new Turn(Color.BLACK);
        turnRepository.save(first);

        turnRepository.save(second);

        assertThat(turnRepository.find()).isEqualTo(second);
    }

    @DisplayName("현재 차례의 정보를 조회한다.")
    @Test
    void findTurn() {
        Turn turn = new Turn(Color.BLACK);
        turnRepository.save(turn);

        assertThat(turnRepository.find()).isEqualTo(turn);
    }
}
