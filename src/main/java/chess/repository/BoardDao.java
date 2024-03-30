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

    public void saveBoard(Board board) {
        try (Connection connection = connectionGenerator.getConnection()) {
            saveBoard(connection, board.getBoard());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveBoard(Connection connection, Map<Location, Piece> board) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BOARDS VALUES (?, ?, ?)");
        for (Location location : board.keySet()) {
            Piece piece = board.get(location);

            String locationData = location.getFile().getSymbol() + location.getRank().getSymbol();
            preparedStatement.setString(1, locationData);
            preparedStatement.setString(2, piece.getPieceType().name());
            preparedStatement.setString(3, piece.getColor().name());

            preparedStatement.addBatch();
            preparedStatement.clearParameters();
        }
        preparedStatement.executeBatch();
    }

    public Optional<Board> findBoard() {
        try (Connection connection = connectionGenerator.getConnection()) {
            Map<Location, Piece> board = findBoard(connection);
            if (board.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new Board(board));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Location, Piece> findBoard(Connection connection) throws SQLException {
        Map<Location, Piece> board = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOARDS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String locationString = resultSet.getString("location");
            String pieceType = resultSet.getString("piece_type");
            String color = resultSet.getString("color");

            Location location = Location.of(locationString);
            Piece piece = PieceDBMapper.createPiece(pieceType, color);
            board.put(location, piece);
        }
        return board;
    }

    public void deleteBoard() {
        try (Connection connection = connectionGenerator.getConnection()) {
            deleteBoard(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBoard(Connection connection) throws SQLException {
        //TODO 여러 게임을 관리하게 되면 꼭 WHERE 절이 필요해진다.
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM BOARDS");
        preparedStatement.execute();
    }
}
