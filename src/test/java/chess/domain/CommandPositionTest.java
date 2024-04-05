package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.CommandPosition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CommandPositionTest {

    @DisplayName("공백을 입력하면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void BlankInputThrowException(final String value) {
        Assertions.assertThatThrownBy(() -> CommandPosition.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("null을 입력하면 예외를 발생시킨다.")
    @ParameterizedTest
    @NullSource
    void nullInputThrowException(final String value) {
        Assertions.assertThatThrownBy(() -> CommandPosition.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("지정된 명령어가 아니면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"st", "START", "go"})
    void test(final String value) {
        Assertions.assertThatThrownBy(() -> CommandPosition.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바른 입력에 대해 명령어를 생성한다.")
    @Test
    void createSTART() {
        // given
        CommandPosition commandPosition = CommandPosition.from("start");

        // when && then
        assertThat(commandPosition).isEqualTo(new CommandPosition(Command.START, "", ""));
    }
    @DisplayName("올바른 입력에 대해 명령어를 생성한다.")
    @Test
    void createEND() {
        // given
        CommandPosition commandPosition = CommandPosition.from("end");

        // when && then
        assertThat(commandPosition).isEqualTo(new CommandPosition(Command.END, "", ""));
    }
    @DisplayName("올바른 입력에 대해 명령어를 생성한다.")
    @Test
    void createMOVE() {
        // given
        CommandPosition commandPosition = CommandPosition.from("move a2 a4");

        // when && then
        assertThat(commandPosition).isEqualTo(new CommandPosition(Command.MOVE, "a2", "a4"));
    }
}
