package chess.repository;

import chess.domain.board.Board;
import chess.domain.chessGame.ChessGame;
import chess.domain.chessGame.PlayingGame;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameDao {

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

    public void saveGame(ChessGame playingGame) {
        try (Connection connection = getConnection()) {
            initializeGameTable(connection);
            initializeBoardTable(connection);
            saveGameData(playingGame, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGameData(ChessGame playingGame, Connection connection) throws SQLException {
        initializeGameTable(connection);
        initializeBoardTable(connection);
        if (playingGame.isEnd()) {
            return;
        }
        saveTurn(connection, playingGame.getTurn());
        saveBoardToDB(connection, playingGame.getBoard());
    }

    private void initializeBoardTable(Connection connection) throws SQLException {
        //TODO 여러 게임을 관리하게 되면 꼭 WHERE 절이 필요해진다.
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM BOARDS");
        preparedStatement.execute();
    }

    private void initializeGameTable(Connection connection) throws SQLException {
        //TODO 여러 게임을 관리하게 되면 꼭 WHERE 절이 필요해진다.
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM GAMES");
        preparedStatement.execute();
    }

    private void saveTurn(Connection connection, Color turn) throws SQLException {
        //TODO 여러 게임을 관리하게 되면 꼭 WHERE 절이 필요해진다.
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO GAMES VALUES (?)");
        preparedStatement.setString(1, turn.name());
        preparedStatement.execute();
    }

    private void saveBoardToDB(Connection connection, Map<Location, Piece> board) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BOARDS VALUES (?, ?, ?)");
        for (Location location : board.keySet()) {
            Piece piece = board.get(location);

            String locationData = location.getFile().getSymbol() + location.getRank().getSymbol();
            preparedStatement.setString(1, locationData);
            preparedStatement.setString(2, piece.getPieceType().name());
            preparedStatement.setString(3, piece.getColor().name());

            preparedStatement.addBatch();
            preparedStatement.clearParameters();
        }
        preparedStatement.executeBatch();
    }

    public Optional<ChessGame> loadGame() {
        try (Connection connection = getConnection()) {
            return loadGame(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<ChessGame> loadGame(Connection connection) throws SQLException {
        Map<Location, Piece> pieceMap = loadBoard();
        if (pieceMap.isEmpty()) {
            return Optional.empty();
        }
        Color turn = loadTurn(connection);
        return Optional.of(new PlayingGame(new Board(pieceMap), turn));
    }

    private Color loadTurn(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM GAMES");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String turnName = resultSet.getString("turn");
        return Arrays.stream(Color.values())
                .filter(color -> color.name().equalsIgnoreCase(turnName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("DB에 저장되어 있는 턴이 없습니다."));
    }

    public Map<Location, Piece> loadBoard() {
        Map<Location, Piece> board = new HashMap<>();
        try (Connection connection = getConnection()) {
            loadBoard(connection, board);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return board;
    }

    private void loadBoard(Connection connection, Map<Location, Piece> board) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BOARDS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String locationString = resultSet.getString("location");
            String pieceType = resultSet.getString("piece_type");
            String color = resultSet.getString("color");

            Location location = Location.of(locationString);
            Piece piece = PieceDBMapper.createPiece(pieceType, color);
            board.put(location, piece);
        }
    }

    public void initialDB() {
        try (Connection connection = getConnection()) {
            initializeGameTable(connection);
            initializeBoardTable(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
