package chess.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatabaseConnectionGeneratorTest {

    private final DatabaseConnectionGenerator connectionGenerator = new DatabaseConnectionGenerator(
            PropertiesFile.of("database.properties")
    );

    @DisplayName("데이터베이스 연결 테스트")
    @Test
    void getConnectionTest() {
        try (Connection connection = connectionGenerator.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
        }
    }
}
