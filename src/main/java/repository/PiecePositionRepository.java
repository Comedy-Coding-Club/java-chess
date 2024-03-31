package repository;

import domain.piece.Piece;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
