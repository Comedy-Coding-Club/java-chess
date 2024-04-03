package chess.repository;

import chess.domain.location.Location;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    public void savePieceLocation(Connection connection, int gameId, Location location, int pieceId)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO BOARDS (game_id ,location ,piece_id) VALUES (?, ?, ?)");
        preparedStatement.setInt(1, gameId);
        preparedStatement.setString(2, getLocationName(location));
        preparedStatement.setInt(3, pieceId);
        preparedStatement.execute();
    }

    public Map<Location, Integer> findBoardById(Connection connection, int gameId) throws SQLException {
        Map<Location, Integer> board = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOARDS WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String locationString = resultSet.getString("location");
            int pieceType = resultSet.getInt("piece_id");

            Location location = Location.of(locationString);
            board.put(location, pieceType);
        }
        return board;
    }

    public void updatePieceLocation(Connection connection, int gameId, Location source, Location target)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE BOARDS SET location = ? WHERE game_id = ? AND location = ?");
        preparedStatement.setString(1, getLocationName(target));
        preparedStatement.setInt(2, gameId);
        preparedStatement.setString(3, getLocationName(source));

        preparedStatement.execute();
    }

    public void deletePieceLocation(Connection connection, int gameId, Location target) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM BOARDS WHERE game_id = ? AND location = ?");
        preparedStatement.setInt(1, gameId);
        preparedStatement.setString(2, getLocationName(target));
        preparedStatement.execute();
    }

    public void deleteAllPiecesById(Connection connection, int gameId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM BOARDS WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        preparedStatement.execute();
    }

    private String getLocationName(Location location) {
        return location.getFile().getSymbol() + location.getRank().getSymbol();
    }
}
