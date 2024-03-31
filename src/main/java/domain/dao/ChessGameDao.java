package domain.dao;

import domain.game.GameState;
import domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChessGameDao {

    public void save(int chessGameId, Color color, GameState gameState) {
        final String query = "INSERT INTO chess_game(id, color, game_status) VALUES(?, ?, ?)";

        try (final Connection connection = DatabaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessGameId);
            preparedStatement.setString(2, color.name());
            preparedStatement.setString(3, gameState.name());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public GameState getGameStatusById(int chessGameId) {
        final String query = "SELECT game_status FROM chess_game WHERE id = ?";
        try (final Connection connection = DatabaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessGameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String gameStatus = resultSet.getString("game_status");
                return GameState.of(gameStatus);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Color getColorById(int chessGameId) {
        final String query = "SELECT color FROM chess_game WHERE id = ?";
        try (final Connection connection = DatabaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessGameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String color = resultSet.getString("color");
                return Color.of(color);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateGameStatus(int chessGameId, GameState gameState) {
        final String query = "UPDATE chess_game SET game_status = ? WHERE id = ?";
        try (final Connection connection = DatabaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameState.name());
            preparedStatement.setInt(2, chessGameId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateColor(int chessGameId, Color color) {
        final String query = "UPDATE chess_game SET color = ? WHERE id = ?";
        try (final Connection connection = DatabaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, color.name());
            preparedStatement.setInt(2, chessGameId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int chessGameId) {
        final String query = "DELETE FROM chess_game WHERE id = ?";
        try (final Connection connection = DatabaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessGameId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
