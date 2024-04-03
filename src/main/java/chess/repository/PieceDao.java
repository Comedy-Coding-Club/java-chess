package chess.repository;

import chess.domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDao {

    public Piece findPieceById(Connection connection, int pieceId) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM PIECES WHERE id = ?");
        preparedStatement.setInt(1, pieceId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        String pieceType = resultSet.getString("piece_type");
        String pieceColor = resultSet.getString("color");
        return PieceFactory.create(pieceType, pieceColor);
    }

    public int getPieceId(Connection connection, Piece piece) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM PIECES WHERE piece_type = ? AND color = ?");
        preparedStatement.setString(1, piece.getPieceType().name());
        preparedStatement.setString(2, piece.getColor().name());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }
}
