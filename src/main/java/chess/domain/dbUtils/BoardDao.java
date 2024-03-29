package chess.domain.dbUtils;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {

    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Connection connection, BoardDto boardDto) {
        final String query = "INSERT INTO board VALUES(?, ?, ?, ?)";
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, boardDto.piece().getPieceType().name());
            preparedStatement.setInt(2, boardDto.position().getColumnIndex());
            preparedStatement.setInt(3, boardDto.position().getRowIndex());
            preparedStatement.setString(4, boardDto.piece().getColor().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BoardDto findByPosition(Connection connection, Position position) {
        final String query = "SELECT * FROM board WHERE row_index = ? and column_index = ? ";
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2, position.getColumnIndex());
            preparedStatement.setInt(1, position.getRowIndex());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Piece piece = new Piece(PieceType.findByName(resultSet.getString(1)),
                        Color.findByName(resultSet.getString(4)));
                return new BoardDto(position, piece);
            }
            throw new IllegalArgumentException("해당 위치에 해당하는 기물이 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Connection connection, Position position) {
        final String query = "DELETE FROM board WHERE row_index = ? and column_index = ?";
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setInt(2, position.getColumnIndex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
