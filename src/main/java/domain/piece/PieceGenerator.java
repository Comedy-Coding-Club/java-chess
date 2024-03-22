package domain.piece;

import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.Pawn;
import domain.piece.piecerole.PieceRole;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PieceGenerator {
    private static final int CHESS_BOARD_SIZE = 8;
    private static final List<PieceRole> SPECIAL_PIECES = List.of(
            new Rook(), new Knight(), new Bishop(), new Queen(), new King(), new Bishop(), new Knight(), new Rook());
    private static final List<PieceRole> BLACK_PAWN_PIECES = IntStream.range(1, CHESS_BOARD_SIZE + 1)
            .mapToObj(number -> (PieceRole) new Pawn(Color.BLACK))
            .toList();

    private static final List<PieceRole> WHITE_PAWN_PIECES = IntStream.range(1, CHESS_BOARD_SIZE + 1)
            .mapToObj(number -> (PieceRole) new Pawn(Color.WHITE))
            .toList();

    private static final Map<Integer, List<Piece>> rankPieces = new HashMap<>();

    static {
        rankPieces.put(8, generateListPiece(SPECIAL_PIECES, Color.BLACK));
        rankPieces.put(7, generateListPiece(BLACK_PAWN_PIECES, Color.BLACK));
        rankPieces.put(2, generateListPiece(WHITE_PAWN_PIECES, Color.WHITE));
        rankPieces.put(1, generateListPiece(SPECIAL_PIECES, Color.WHITE));
    }

    private PieceGenerator() {
    }

    public static Map<Position, Piece> generate() {
        Map<Position, Piece> initChessBoard = new HashMap<>();
        for (int row = 1; row <= 8 ; row++) {
            List<Piece> pieces = rankPieces.getOrDefault(row, new ArrayList<>());
            initializePositions(initChessBoard, pieces, row);
        }
        return initChessBoard;
    }

    private static void initializePositions(Map<Position, Piece> initChessBoard, List<Piece> pieces, int row) {
        for (int column = 0; column < pieces.size(); column++) {
            Position square = new Position(new Position(new File((char) ('a' + column)), new Rank(row)));
            Piece piece = pieces.get(column);
            initChessBoard.put(square, piece);
        }
    }

    private static List<Piece> generateListPiece(final List<PieceRole> pieceRoles, final Color color) {
        return pieceRoles.stream()
                .map(pieceRole -> new Piece(pieceRole, color))
                .toList();
    }
}
