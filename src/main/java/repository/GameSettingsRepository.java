package repository;

import controller.constants.GameState;
import domain.game.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import repository.generator.ConnectionGenerator;

public class GameSettingsRepository {

    public int saveTurn(final Turn turn) {
        String query = "INSERT INTO game_settings VALUES (?, ?)";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "turn");
            preparedStatement.setString(2, turn.getName());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveGameState(final GameState gameState) {
        String query = "INSERT INTO game_settings VALUES (?, ?)";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "game_state");
            preparedStatement.setString(2, gameState.name());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
