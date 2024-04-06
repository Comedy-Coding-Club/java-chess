package chess.dao;

import chess.DBConnection;
import chess.dto.BoardDto;
import chess.dto.PieceDto;
import chess.dto.PositionDto;
import chess.dto.SquareDto;
import chess.dto.SquaresDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SquareDao {

    public void insertSquare(final int boardId, final PositionDto positionDto, final PieceDto pieceDto) {
        final String query = "INSERT INTO squares VALUES(?, ?, ?, ?, ?)";

        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            preparedStatement.setInt(2, positionDto.fileIndex());
            preparedStatement.setInt(3, positionDto.rankIndex());
            preparedStatement.setString(4, pieceDto.color());
            preparedStatement.setString(5, pieceDto.pieceType());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물과 위치를 저장하는 도중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    public void deleteAllSquares(final BoardDto boardDto) {
        final String query = "DELETE FROM Squares WHERE board_id = (?)";

        try (final Connection connection = DBConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardDto.id());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물과 위치 정보를 삭제하는 도중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    public SquaresDto findById(final int boardId) {
        final String query = "SELECT * FROM Squares WHERE board_id = (?)";

        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);

            final ResultSet resultSet = preparedStatement.executeQuery();

            return mapToSquaresDto(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물과 위치 정보를 조회하는 도중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    private SquaresDto mapToSquaresDto(final ResultSet resultSet) throws SQLException {
        final List<SquareDto> squares = new ArrayList<>();

        while (resultSet.next()) {
            final int file = resultSet.getInt("square_file");
            final int rank = resultSet.getInt("square_rank");
            final String color = resultSet.getString("color");
            final String piece = resultSet.getString("piece_type");

            squares.add(new SquareDto(file, rank, color, piece));
        }

        return new SquaresDto(squares);
    }
}
