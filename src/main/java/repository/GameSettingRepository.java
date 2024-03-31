package repository;

import static repository.mapper.TurnMapper.getTurnByName;

import controller.constants.GameState;
import domain.game.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.generator.ConnectionGenerator;

public class GameSettingRepository {

    public int saveTurn(final Turn turn) {
        String query = "INSERT INTO game_setting VALUES (?, ?)";

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

    public void updateTurn(final Turn turn) {
        String query = "UPDATE game_setting SET _value = ? WHERE _key = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, turn.getName());
            preparedStatement.setString(2, "turn");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turn findTurn() {
        String query = "SELECT _value FROM game_setting WHERE _key = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "turn");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getTurnByName(resultSet.getString("_value"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("[ERROR] Turn을 조회할 수 없습니다.");
    }

    public int saveGameState(final GameState gameState) {
        String query = "INSERT INTO game_setting VALUES (?, ?)";

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
