package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import repository.DatabaseConnection;

class DatabaseConnectionTest {

    @Test
    void connect() {
        try (final var connection = DatabaseConnection.getInstance().getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
