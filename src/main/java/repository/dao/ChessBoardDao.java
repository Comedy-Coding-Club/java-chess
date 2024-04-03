package repository.dao;

import domain.game.ChessBoard;
import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import domain.position.Position;
import exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import repository.DatabaseConnection;

public class ChessBoardDao {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void save(ChessBoard chessBoard) {
        final String query = "INSERT INTO chess_board(chess_game_id, board_file, board_rank, piece_type, piece_color) VALUES(?, ?, ?, ?, ?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Map<Position, Piece> piecesPosition = chessBoard.getPiecesPosition();
            for (Entry<Position, Piece> entry : piecesPosition.entrySet()) {
                Position position = entry.getKey();
                String file = position.getFileName();
                String rank = position.getRankName();

                Piece piece = entry.getValue();
                String pieceType = piece.getPieceType();
                String color = piece.getColor();

                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, file);
                preparedStatement.setString(3, rank);
                preparedStatement.setString(4, pieceType);
                preparedStatement.setString(5, color);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage() + "chessboard를 저장할 수 없습니다.");
        }
    }

    public ChessBoard findByChessGameId() {
        final String query = "SELECT * FROM chess_board WHERE chess_game_id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);

            ResultSet resultSet = preparedStatement.executeQuery();

            final Map<Position, Piece> piecesPosition = new HashMap<>();
            parseResultSet(resultSet, piecesPosition);
            ChessGameDao chessGameDao = new ChessGameDao();
            Color color = chessGameDao.findColorById();
            return new ChessBoard(piecesPosition, color);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage() + "cheesboard를 찾을 수 없습니다. ");
        }
    }

    private void parseResultSet(ResultSet resultSet, Map<Position, Piece> piecesPosition) throws SQLException {
        while (resultSet.next()) {
            String file = resultSet.getString("board_file");
            String rank = resultSet.getString("board_rank");
            Position position = new Position(file, rank);

            String pieceType = resultSet.getString("piece_type");
            String pieceColor = resultSet.getString("piece_color");
            Piece piece = switch (pieceType) {
                case "KING" -> new Piece(new King(), Color.of(pieceColor));
                case "QUEEN" -> new Piece(new Queen(), Color.of(pieceColor));
                case "ROOK" -> new Piece(new Rook(), Color.of(pieceColor));
                case "KNIGHT" -> new Piece(new Knight(), Color.of(pieceColor));
                case "BISHOP" -> new Piece(new Bishop(), Color.of(pieceColor));
                case "WHITE_PAWN" -> new Piece(new WhitePawn(), Color.of(pieceColor));
                case "BLACK_PAWN" -> new Piece(new BlackPawn(), Color.of(pieceColor));
                default -> null;
            };
            piecesPosition.put(position, piece);
        }
    }

    public int delete() {
        final String query = "DELETE FROM chess_board WHERE chess_game_id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage() + "chessboard를 삭제할 수 없습니다.");
        }
    }
}
