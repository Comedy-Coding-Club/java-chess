package repository;

import static repository.mapper.GameStateMapper.getGameStateByName;

import controller.constants.GameState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.generator.ConnectionGenerator;

public class GameStateRepository {
    public int save(final GameState gameState) {
        String query = "INSERT INTO game_setting VALUES (?, ?) ON DUPLICATE KEY UPDATE _value = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "game_state");
            preparedStatement.setString(2, gameState.name());
            preparedStatement.setString(3, gameState.name());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameState find() {
        String query = "SELECT _value FROM game_setting WHERE _key = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "game_state");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getGameStateByName(resultSet.getString("_value"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return GameState.NOT_STARTED;
    }
}
