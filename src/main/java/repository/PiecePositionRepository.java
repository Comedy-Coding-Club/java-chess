package repository;

import static repository.ColorMapper.getColorByFieldName;
import static repository.PieceRoleMapper.getPieceRoleByFieldName;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.PieceRole;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PiecePositionRepository {
    public int save(final Position position, final Piece piece) {
        String query = "INSERT INTO piece_position VALUES (?, ?, ?, ?)";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, position.fileName());
            preparedStatement.setString(2, position.rankName());
            preparedStatement.setString(3, piece.pieceRoleName());
            preparedStatement.setString(4, piece.colorName());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Piece findPieceByPosition(final Position position) {
        String query = "SELECT * FROM piece_position WHERE _file = ? AND _rank = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, position.fileName());
            preparedStatement.setString(2, position.rankName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PieceRole pieceRole = getPieceRoleByFieldName(resultSet.getString("piece_role"));
                Color color = getColorByFieldName(resultSet.getString("color"));
                return new Piece(pieceRole, color);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("[ERROR] 기물을 조회할 수 없습니다.");
    }

    public void clear() {
        String query = "TRUNCATE piece_position";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByPosition(final Position position) {
        String query = "DELETE FROM piece_position WHERE _file = ? AND _rank = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, position.fileName());
            preparedStatement.setString(2, position.rankName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
