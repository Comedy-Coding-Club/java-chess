package chess.domain.board;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.HashMap;
import java.util.Map;

public class DefaultBoardInitializer {
    public static Map<Position, Piece> initializer() {
        Map<Position, Piece> board = new HashMap<>();
        initializeBlackTeam(board);
        initializeWhiteTeam(board);
        return board;
    }

    private static void initializeBlackTeam(Map<Position, Piece> board) {
        initializePawn(board, Row.RANK7, Color.BLACK);
        initializeHighValuePiece(board, Row.RANK8, Color.BLACK);
    }

    private static void initializeWhiteTeam(Map<Position, Piece> board) {
        initializePawn(board, Row.RANK2, Color.WHITE);
        initializeHighValuePiece(board, Row.RANK1, Color.WHITE);
    }

    private static void initializePawn(Map<Position, Piece> board, Row row, Color color) {
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            if (color == Color.WHITE) {
                board.put(position, new Piece(PieceType.WHITE_PAWN, color));
                continue;
            }
            board.put(position, new Piece(PieceType.BLACK_PAWN, color));
        }
    }

    private static void initializeHighValuePiece(Map<Position, Piece> board, Row row, Color color) {
        board.put(new Position(row, Column.A), new Piece(PieceType.ROOK, color));
        board.put(new Position(row, Column.H), new Piece(PieceType.ROOK, color));

        board.put(new Position(row, Column.B), new Piece(PieceType.KNIGHT, color));
        board.put(new Position(row, Column.G), new Piece(PieceType.KNIGHT, color));

        board.put(new Position(row, Column.C), new Piece(PieceType.BISHOP, color));
        board.put(new Position(row, Column.F), new Piece(PieceType.BISHOP, color));

        board.put(new Position(row, Column.D), new Piece(PieceType.QUEEN, color));
        board.put(new Position(row, Column.E), new Piece(PieceType.KING, color));
    }
}
