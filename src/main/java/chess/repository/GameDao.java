package chess.repository;

import chess.repository.entity.Game;
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

    //== GameDao 로 변경 ==//

    public void saveGame(Game game) {
        try (Connection connection = connectionGenerator.getConnection()) {
            saveGame(connection, game);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGame(Connection connection, Game game) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO GAMES (game_id, turn) VALUES (?, ?)");
        preparedStatement.setInt(1, game.getGameId());
        preparedStatement.setString(2, game.getTurn().name());
        preparedStatement.execute();
    }

    public int findLastGameId() {
        try (Connection connection = connectionGenerator.getConnection()) {
            return findLastGameId(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findLastGameId(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(game_id) FROM GAMES");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("game_id");
    }

    public Optional<Game> findGame(int gameId) {
        try (Connection connection = connectionGenerator.getConnection()) {
            return findGame(connection, gameId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Game> findGame(Connection connection, int gameId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM GAMES WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Game game = createGameEntity(gameId, resultSet);
            return Optional.of(game);
        }
        return Optional.empty();
    }

    private Game createGameEntity(int gameId, ResultSet resultSet) throws SQLException {
        String turnName = resultSet.getString("turn");
        Color turn = Arrays.stream(Color.values())
                .filter(color -> color.name().equalsIgnoreCase(turnName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("DB에 잘못된 값이 저장되어 있습니다."));
        return new Game(gameId, turn);
    }
}
