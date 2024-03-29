package chess.domain.board;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.HashMap;

public class DefaultBoardInitializer implements BoardInitializer{

    @Override
    public MemoryChessBoard initialize() {
        MemoryChessBoard memoryChessBoard = new MemoryChessBoard(new HashMap<>());
        initializeBlackTeam(memoryChessBoard);
        initializeWhiteTeam(memoryChessBoard);
        return memoryChessBoard;
    }

    private void initializeBlackTeam(MemoryChessBoard memoryChessBoard) {
        initializePawn(memoryChessBoard, Row.RANK7, Color.BLACK);
        initializeHighValuePiece(memoryChessBoard, Row.RANK8, Color.BLACK);
    }

    private void initializeWhiteTeam(MemoryChessBoard memoryChessBoard) {
        initializePawn(memoryChessBoard, Row.RANK2, Color.WHITE);
        initializeHighValuePiece(memoryChessBoard, Row.RANK1, Color.WHITE);
    }

    private void initializePawn(MemoryChessBoard memoryChessBoard, Row row, Color color) {
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            if (color == Color.WHITE) {
                memoryChessBoard.putPiece(position, new Piece(PieceType.WHITE_PAWN, color));
                continue;
            }
            memoryChessBoard.putPiece(position, new Piece(PieceType.BLACK_PAWN, color));
        }
    }

    private void initializeHighValuePiece(MemoryChessBoard memoryChessBoard, Row row, Color color) {
        memoryChessBoard.putPiece(new Position(row, Column.A), new Piece(PieceType.ROOK, color));
        memoryChessBoard.putPiece(new Position(row, Column.H), new Piece(PieceType.ROOK, color));

        memoryChessBoard.putPiece(new Position(row, Column.B), new Piece(PieceType.KNIGHT, color));
        memoryChessBoard.putPiece(new Position(row, Column.G), new Piece(PieceType.KNIGHT, color));

        memoryChessBoard.putPiece(new Position(row, Column.C), new Piece(PieceType.BISHOP, color));
        memoryChessBoard.putPiece(new Position(row, Column.F), new Piece(PieceType.BISHOP, color));

        memoryChessBoard.putPiece(new Position(row, Column.D), new Piece(PieceType.QUEEN, color));
        memoryChessBoard.putPiece(new Position(row, Column.E), new Piece(PieceType.KING, color));
    }
}
