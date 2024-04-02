package chess.db;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.dto.BoardDto;
import chess.dto.BoardPieceDto;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BoardDao {

    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void create(BoardPieceDto boardPieceDto) {
        final String query = "INSERT INTO board VALUES(?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, boardPieceDto.piece().getPieceType().name());
            preparedStatement.setInt(2, boardPieceDto.position().getColumnIndex());
            preparedStatement.setInt(3, boardPieceDto.position().getRowIndex());
            preparedStatement.setString(4, boardPieceDto.piece().getColor().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Optional<BoardPieceDto> findByPosition(Position position) {
        final String query = "SELECT * FROM board WHERE row_index = ? and column_index = ? ";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(2, position.getColumnIndex());
            preparedStatement.setInt(1, position.getRowIndex());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Piece piece = new Piece(PieceType.findByName(resultSet.getString(1)),
                        Color.findByName(resultSet.getString(4)));
                return Optional.of(new BoardPieceDto(position, piece));
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public BoardDto findAllPieces() {
        final String query = "SELECT * FROM board";
        Map<Position, Piece> result = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PieceType pieceType = PieceType.findByName(resultSet.getString("piece_type"));
                Color color = Color.findByName(resultSet.getString("color"));
                Row row = Row.findByIndex(resultSet.getInt("row_index"));
                Column column = Column.findByIndex(resultSet.getInt("column_index"));
                result.put(new Position(row, column), new Piece(pieceType, color));
            }
            return new BoardDto(result);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Position position)  {
        final String query = "DELETE FROM board WHERE row_index = ? and column_index = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setInt(2, position.getColumnIndex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearAllPieces() {
        final String query = "DELETE FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
