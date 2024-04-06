package chess.dao;

import chess.DBConnection;
import chess.dto.BoardDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDao {

    public int insertBoard(final BoardDto boardDto) {
        final String query = "INSERT INTO boards VALUES(?, ?)";

        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, boardDto.id());
            preparedStatement.setString(2, boardDto.turn());

            preparedStatement.executeUpdate();

            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            return fetchGeneratedKey(generatedKeys);
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 체스 보드 생성 중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    public void updateTurn(final BoardDto boardDto) {
        final String query = "UPDATE boards SET current_turn = ? WHERE board_id = ?";

        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, boardDto.turn());
            preparedStatement.setInt(2, boardDto.id());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 체스 보드 업데이트 중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    public BoardDto findRecentBoard() {
        final String query = "SELECT * FROM boards ORDER BY board_id DESC LIMIT 1";

        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            final ResultSet resultSet = preparedStatement.executeQuery();

            return mapToChessBoardDto(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 보드를 조회하는 도중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    private int fetchGeneratedKey(final ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        throw new SQLException("[ERROR] Generated key를 찾을 수 없습니다.");
    }

    private BoardDto mapToChessBoardDto(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            final int id = resultSet.getInt("board_id");
            final String turn = resultSet.getString("current_turn");

            return new BoardDto(id, turn);
        }
        throw new IllegalStateException("[ERROR] 결과값이 존재하지 않습니다.");
    }
}
