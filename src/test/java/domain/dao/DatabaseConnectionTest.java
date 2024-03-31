package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.DatabaseConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {


    @Test
    void connection() {
        try (final var connection = DatabaseConnection.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
