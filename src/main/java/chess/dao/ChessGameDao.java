package chess.dao;

import chess.dto.ChessGameDto;
import chess.domain.ChessGame;
import chess.domain.piece.Color;
import chess.dto.SquareDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChessGameDao {

    public final SquareDao squareDao = new SquareDao();

    public ChessGameDto findGame() {
        try(final Connection connection = ChessDbConnector.getConnection();
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM chessGame");) {

            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Color currentColor = Color.valueOf(resultSet.getString("currentColor"));
                List<SquareDto> chessBoard = squareDao.findSquares();
                return ChessGameDto.of(chessBoard, currentColor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addGame(final ChessGame chessGame) {
        final String query = "INSERT INTO chessGame (chessGame_id, currentColor) VALUES (1 , ?)";

        try {
            squareDao.addSquares(chessGame.getChessBoard());

            final Connection connection = ChessDbConnector.getConnection();
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, chessGame.getCurrentColor().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGame() {
        final String query = "DELETE FROM chessGame WHERE chessGame_id = 1";
        try {
            final Connection connection = ChessDbConnector.getConnection();
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGame(final ChessGame chessGame) {
        final String query = "UPDATE chessGame SET currentColor = ? WHERE chessGame_id = 1";

        try {
            final Connection connection = ChessDbConnector.getConnection();
            final PreparedStatement statement = connection.prepareStatement(query);

            final String color = chessGame.getCurrentColor().name();
            statement.setString(1, color);

            squareDao.deleteSquares();
            squareDao.addSquares(chessGame.getChessBoard());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
