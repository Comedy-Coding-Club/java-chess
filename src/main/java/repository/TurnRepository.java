package repository;

import static repository.mapper.TurnMapper.getTurnByName;

import domain.game.Turn;
import domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.generator.ConnectionGenerator;

public class TurnRepository {
    public void save(final Turn turn) {
        String query = "INSERT INTO game_setting VALUES (?, ?) ON DUPLICATE KEY UPDATE _value = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "turn");
            preparedStatement.setString(2, turn.getName());
            preparedStatement.setString(3, turn.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turn find() {
        String query = "SELECT _value FROM game_setting WHERE category = ?";

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
        return new Turn(Color.WHITE);
    }


}
