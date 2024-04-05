package chess.dao;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.dto.SquareDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SquareDao {

    public List<SquareDto> findSquares() {
        final String query = "SELECT * FROM square WHERE chessGame_id = 1 ORDER BY position";

        try (final Connection connection = ChessDbConnector.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();

            final List<SquareDto> squareEntities = new ArrayList<>();
            while (resultSet.next()) {
                String position = resultSet.getString("position");
                PieceType pieceType = PieceType.valueOf(resultSet.getString("pieceType"));
                Color color = Color.valueOf(resultSet.getString("color"));

                squareEntities.add(new SquareDto(position, pieceType, color));
            }
            return squareEntities;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSquares(final Map<Position, Piece> chessBoard) {
        final String query = "INSERT INTO square (position, pieceType, color, chessGame_id) VALUES (?, ?, ?, 1)";

        try (final Connection connection = ChessDbConnector.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {
            List<SquareDto> squareDtos = SquareDto.toSquareDtos(chessBoard);

            for (SquareDto squareDto : squareDtos) {
                statement.setString(1, squareDto.position());
                statement.setString(2, squareDto.pieceType().name());
                statement.setString(3, squareDto.color().name());

                statement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSquares() {
        final String query = "DELETE FROM square WHERE chessGame_id = 1";

        try (final Connection connection = ChessDbConnector.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
