package repository;

import static repository.ColorMapper.getColorByFieldName;
import static repository.PieceRoleMapper.getPieceRoleByFieldName;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.PieceRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceRepository {
    public int save(final Piece piece) {
        String query = "INSERT INTO piece (piece_role, color) VALUES (?, ?)";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, piece.pieceRoleName());
            preparedStatement.setString(2, piece.colorName());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Piece findByPieceId(final String pieceId) {
        String query = "SELECT * FROM piece WHERE piece_id = ?";

        Connection connection = ConnectionGenerator.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pieceId);

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
}
