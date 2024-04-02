package chess.db;

import chess.domain.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {
    private final Connection connection;

    public GameDao(Connection connection) {
        this.connection = connection;
    }

    public Color getCurrentTurn() {
        final String query = "SELECT current_turn FROM game";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){
            if (resultSet.next()) {
                return Color.findByName(resultSet.getString("current_turn"));
            }
            throw new RuntimeException("등록된 현재 턴 기록이 없습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setTurn(Color nextTurn) {
        final String query = "UPDATE game SET current_turn = '" + nextTurn.name() + "' WHERE id = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
