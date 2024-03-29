package chess.domain.board;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.dbUtils.BoardDao;
import chess.domain.dbUtils.DBConnectionUtils;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MysqlBoardInitializer implements BoardInitializer{

    private final Connection connection;

    public MysqlBoardInitializer() {
        this.connection = DBConnectionUtils.getConnection();
    }

    public MysqlBoardInitializer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ChessBoard initialize() {
        ChessBoard chessBoard = new DBChessBoard(new BoardDao(connection));
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM board");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Column column = Column.findByIndex(resultSet.getInt("colum_index"));
                Row row = Row.findByIndex(resultSet.getInt("row_index"));
                Position position = new Position(row, column);
                PieceType pieceType = PieceType.findByName(resultSet.getString("piece_type"));
                Color color = Color.findByName(resultSet.getString("color"));
                chessBoard.putPiece(position, new Piece(pieceType, color));
            }
            return chessBoard;
        } catch (SQLException e) {
            throw new NoSuchElementException("DB 에 저장된 보드 정보가 없습니다.");
        }
    }

//    private ChessBoard initializePreviousGame(ResultSet resultSet) throws SQLException {
//        ChessBoard memoryChessBoard = new ChessBoard(new HashMap<>());
//
//        return memoryChessBoard
//
//    }
}
