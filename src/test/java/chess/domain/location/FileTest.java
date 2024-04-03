package chess.domain.location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.location.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileTest {
    @DisplayName("A~H 사이의 알파벳을 이용해 객체를 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "A", "h", "H"})
    void constructTest(String input) {
        assertThatCode(() -> File.createByName(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("범위 이외의 알파벳을 이용해 객체를 생성하면 예외가 발생한다.")
    @Test
    void outOfBoundConstructTest() {
        assertThatThrownBy(() -> File.createByName("I"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 Column 입력입니다.");
    }

    @DisplayName("알파벳이 아닌 값을 이용해 객체를 생성하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "각", "!"})
    void NotAlphabetExceptionTest(String input) {
        assertThatThrownBy(() -> File.createByName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 Column 입력입니다.");
    }

    @DisplayName("Column 거리를 계산한다.")
    @Test
    void calculateDistanceTest() {

        File source = File.C;
        File target = File.G;
        assertThat(source.calculateDistance(target)).isEqualTo(4);
    }
}
