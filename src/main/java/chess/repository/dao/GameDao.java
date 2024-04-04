package chess.repository.dao;

import chess.domain.chessGame.ChessGame;
import chess.domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class GameDao {

    public void saveGame(Connection connection, ChessGame game) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO GAMES (id, turn) VALUES (?, ?)");
        preparedStatement.setInt(1, game.getGameId());
        preparedStatement.setString(2, game.getTurn().name());
        preparedStatement.execute();
    }

    public int findLastGameId(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM GAMES");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

    public Optional<Color> findGameById(Connection connection, int gameId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM GAMES WHERE id = ?");
        preparedStatement.setInt(1, gameId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getTurnFromResultSet(resultSet);
        }
        return Optional.empty();
    }

    private Optional<Color> getTurnFromResultSet(ResultSet resultSet) throws SQLException {
        String turnName = resultSet.getString("turn");
        return Arrays.stream(Color.values())
                .filter(color -> color.name().equalsIgnoreCase(turnName))
                .findFirst();
    }

    public void updateGame(Connection connection, ChessGame game) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE GAMES SET turn = ? WHERE id = ? ");
        preparedStatement.setString(1, game.getTurn().name());
        preparedStatement.setInt(2, game.getGameId());
        preparedStatement.execute();
    }

    public void deleteGameById(Connection connection, int gameId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM GAMES WHERE id = ?");
        preparedStatement.setInt(1, gameId);
        preparedStatement.execute();
    }
}
