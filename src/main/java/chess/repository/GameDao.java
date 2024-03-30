package chess.repository;

import chess.service.domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class GameDao {
    private final DatabaseConnectionGenerator connectionGenerator;

    public GameDao(DatabaseConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    public void saveTurn(Color turn) {
        try (Connection connection = connectionGenerator.getConnection()) {
            saveTurn(connection, turn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveTurn(Connection connection, Color turn) throws SQLException {
        //TODO 여러 게임을 관리하게 되면 꼭 WHERE 절이 필요해진다.
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO GAMES VALUES (?)");
        preparedStatement.setString(1, turn.name());
        preparedStatement.execute();
    }

    public Optional<Color> findTurn() {
        try (Connection connection = connectionGenerator.getConnection()) {
            return findTurn(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Color> findTurn(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM GAMES");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String turnName = resultSet.getString("turn");
        return Arrays.stream(Color.values())
                .filter(color -> color.name().equalsIgnoreCase(turnName))
                .findFirst();
    }

    public void deleteTurn() {
        try (Connection connection = connectionGenerator.getConnection()) {
            deleteTurn(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteTurn(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM GAMES");
        preparedStatement.execute();
    }
}
