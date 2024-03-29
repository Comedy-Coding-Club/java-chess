package chess.domain.dbUtils;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
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

    public void create(BoardDto boardDto) {
        final String query = "INSERT INTO board VALUES(?, ?, ?, ?)";
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, boardDto.piece().getPieceType().name());
            preparedStatement.setInt(2, boardDto.position().getColumnIndex());
            preparedStatement.setInt(3, boardDto.position().getRowIndex());
            preparedStatement.setString(4, boardDto.piece().getColor().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Optional<BoardDto> findByPosition(Position position) {
        final String query = "SELECT * FROM board WHERE row_index = ? and column_index = ? ";
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2, position.getColumnIndex());
            preparedStatement.setInt(1, position.getRowIndex());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Piece piece = new Piece(PieceType.findByName(resultSet.getString(1)),
                        Color.findByName(resultSet.getString(4)));
                return Optional.of(new BoardDto(position, piece));
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findAllPieces() {
        final String query = "SELECT * FROM board";
        Map<Position, Piece> result = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PieceType pieceType = PieceType.findByName(resultSet.getString("piece_type"));
                Color color = Color.findByName(resultSet.getString("color"));
                Row row = Row.findByIndex(resultSet.getInt("row_index"));
                Column column = Column.findByIndex(resultSet.getInt("column_index"));
                result.put(new Position(row, column), new Piece(pieceType, color));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Position position) {
        final String query = "DELETE FROM board WHERE row_index = ? and column_index = ?";
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setInt(2, position.getColumnIndex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO 예외를 바꿔서 터트리는게 과연 맞을까??
        }
    }
}
