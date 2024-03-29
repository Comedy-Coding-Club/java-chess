package chess.domain.board;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.dbUtils.DBConnectionUtils;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
    public MemoryChessBoard initialize() {
        MemoryChessBoard memoryChessBoard = new MemoryChessBoard(new HashMap<>());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM board");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Column column = Column.findByIndex(resultSet.getInt("colum_index"));
                Row row = Row.findByIndex(resultSet.getInt("row_index"));
                Position position = new Position(row, column);
                PieceType pieceType = PieceType.findByName(resultSet.getString("piece_type"));
                Color color = Color.findByName(resultSet.getString("color"));
                memoryChessBoard.putPiece(position, new Piece(pieceType, color));
            }
        } catch (SQLException e) {
            throw new NoSuchElementException("DB 에 저장된 보드 정보가 없습니다.");
        }
        return memoryChessBoard;
    }
}
