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
import java.util.Map.Entry;
import java.util.Optional;

public class PieceDao {
    private final DatabaseConnectionGenerator connectionGenerator;

    public PieceDao(DatabaseConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    public void saveAllPieces(int gameId, Board board) {
        try (Connection connection = connectionGenerator.getConnection()) {
            saveAllPieces(connection, gameId, board.getBoard());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAllPieces(Connection connection, int gameId, Map<Location, Piece> pieces) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO PIECES (game_id, location, piece_type, color) VALUES (?, ?, ?, ?)"
        );
        for (Entry<Location, Piece> locationPieceEntry : pieces.entrySet()) {
            Piece piece = locationPieceEntry.getValue();
            Location location = locationPieceEntry.getKey();

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

    public Optional<Board> findAllPiecesById(int gameId) {
        try (Connection connection = connectionGenerator.getConnection()) {
            Map<Location, Piece> board = findAllPiecesById(connection, gameId);
            if (board.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new Board(board));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Location, Piece> findAllPiecesById(Connection connection, int gameId) throws SQLException {
        Map<Location, Piece> board = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PIECES WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String locationString = resultSet.getString("location");
            String pieceType = resultSet.getString("piece_type");
            String color = resultSet.getString("color");

            Location location = Location.of(locationString);
            Piece piece = PieceFactory.create(pieceType, color);
            board.put(location, piece);
        }
        return board;
    }

    public void deleteAllPiecesById(int gameId) {
        try (Connection connection = connectionGenerator.getConnection()) {
            deleteAllPiecesById(connection, gameId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAllPiecesById(Connection connection, int gameId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PIECES WHERE game_id = ?");
        preparedStatement.setInt(1, gameId);
        preparedStatement.execute();
    }
}
