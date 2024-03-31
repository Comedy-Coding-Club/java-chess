package chess.repository;

import chess.service.domain.board.Board;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BoardDao {
    private final DatabaseConnectionGenerator connectionGenerator;

    public BoardDao(DatabaseConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    public void saveBoard(int gameId, Board board) {
        try (Connection connection = connectionGenerator.getConnection()) {
            saveBoard(connection, gameId, board.getBoard());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveBoard(Connection connection, int gameId, Map<Location, Piece> board) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO BOARDS (game_id, location, piece_type, color) VALUES (?, ?, ?, ?)"
        );
        for (Location location : board.keySet()) {
            Piece piece = board.get(location);

            String locationData = location.getFile().getSymbol() + location.getRank().getSymbol();
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, locationData);
            preparedStatement.setString(3, piece.getPieceType().name());
            preparedStatement.setString(4, piece.getColor().name());

            preparedStatement.addBatch();
            preparedStatement.clearParameters();
        }
        preparedStatement.executeBatch();
    }

    public Optional<Board> findBoardById(int gameId) {
        try (Connection connection = connectionGenerator.getConnection()) {
            Map<Location, Piece> board = findBoardById(connection, gameId);
            if (board.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new Board(board));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Location, Piece> findBoardById(Connection connection, int gameId) throws SQLException {
        Map<Location, Piece> board = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOARDS WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String locationString = resultSet.getString("location");
            String pieceType = resultSet.getString("piece_type");
            String color = resultSet.getString("color");

            Location location = Location.of(locationString);
            Piece piece = PieceGenerator.createPiece(pieceType, color);
            board.put(location, piece);
        }
        return board;
    }

    public void updateBoard(int gameId, Board board) {
        deleteBoardById(gameId);
        saveBoard(gameId, board);
    }

    public void deleteBoardById(int gameId) {
        try (Connection connection = connectionGenerator.getConnection()) {
            deleteBoardById(connection, gameId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBoardById(Connection connection, int gameId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM BOARDS WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        preparedStatement.execute();
    }
}
