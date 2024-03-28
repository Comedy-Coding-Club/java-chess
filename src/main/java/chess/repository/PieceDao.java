package chess.repository;

import chess.domain.location.Location;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void saveBoard(Map<Location, Piece> board) {
        try(Connection connection = getConnection()) {
            saveBoardToDB(connection, board);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveBoardToDB(Connection connection, Map<Location, Piece> board) throws SQLException {
        for (Location location : board.keySet()) {
            Piece piece = board.get(location);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into board values (?, ?, ?)");

            String locationData = location.getFile().getSymbol() + location.getRank().getSymbol();
            preparedStatement.setString(1, locationData);
            preparedStatement.setString(2, piece.getPieceType().name());
            preparedStatement.setString(3, piece.getColor().name());

            preparedStatement.execute();
        }
    }

    public Map<Location, Piece> loadBoard() {
        Map<Location, Piece> board = new HashMap<>();
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from board");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String locationString = resultSet.getString("location");
                String pieceType = resultSet.getString("piece_type");
                String color = resultSet.getString("color");

                Location location = Location.of(locationString);
                Piece piece = PieceEntity.of(pieceType, color);
                board.put(location, piece);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return board;
    }
}
