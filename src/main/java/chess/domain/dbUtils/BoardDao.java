package chess.domain.dbUtils;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void create(Connection connection, BoardDto boardDto) {
        final var query = "INSERT INTO board VALUES(?, ?, ?, ?)";
        try {
            final var preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, boardDto.piece().getPieceType().name());
            preparedStatement.setInt(2, boardDto.position().getColumnIndex());
            preparedStatement.setInt(3, boardDto.position().getRowIndex());
            preparedStatement.setString(4, boardDto.piece().getColor().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static BoardDto findByPosition(Connection connection, Position position) {
        final var query = "SELECT * FROM board WHERE row_index = ? and column_index = ? ";
        try {
            final var preparedStatement = connection.prepareStatement(query);
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
}
