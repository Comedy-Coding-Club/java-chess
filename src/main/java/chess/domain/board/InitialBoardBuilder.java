package chess.domain.board;

import chess.domain.location.File;
import chess.domain.location.Location;
import chess.domain.location.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.implement.Bishop;
import chess.domain.piece.implement.BlackPawn;
import chess.domain.piece.implement.King;
import chess.domain.piece.implement.Knight;
import chess.domain.piece.implement.Queen;
import chess.domain.piece.implement.Rook;
import chess.domain.piece.implement.WhitePawn;
import java.util.HashMap;
import java.util.Map;

public class InitialBoardBuilder {

    public static Board build() {
        Map<Location, Piece> initialBoard = new HashMap<>();
        initialPawnSetting(initialBoard);
        initialRookSetting(initialBoard);
        initialKnightSetting(initialBoard);
        initialBishopSetting(initialBoard);
        initialQueenSetting(initialBoard);
        initialKingSetting(initialBoard);
        return new Board(initialBoard);
    }

    private static void initialPawnSetting(Map<Location, Piece> board) {
        for (File value : File.values()) {
            board.put(new Location(value, Rank.TWO), new WhitePawn());
            board.put(new Location(value, Rank.SEVEN), new BlackPawn());
        }
    }

    private static void initialRookSetting(Map<Location, Piece> board) {
        board.put(new Location(File.A, Rank.ONE), new Rook(Color.WHITE));
        board.put(new Location(File.A, Rank.EIGHT), new Rook(Color.BLACK));
        board.put(new Location(File.H, Rank.ONE), new Rook(Color.WHITE));
        board.put(new Location(File.H, Rank.EIGHT), new Rook(Color.BLACK));
    }

    private static void initialKnightSetting(Map<Location, Piece> board) {
        board.put(new Location(File.B, Rank.ONE), new Knight(Color.WHITE));
        board.put(new Location(File.B, Rank.EIGHT), new Knight(Color.BLACK));
        board.put(new Location(File.G, Rank.ONE), new Knight(Color.WHITE));
        board.put(new Location(File.G, Rank.EIGHT), new Knight(Color.BLACK));
    }

    private static void initialBishopSetting(Map<Location, Piece> board) {
        board.put(new Location(File.C, Rank.ONE), new Bishop(Color.WHITE));
        board.put(new Location(File.C, Rank.EIGHT), new Bishop(Color.BLACK));
        board.put(new Location(File.F, Rank.ONE), new Bishop(Color.WHITE));
        board.put(new Location(File.F, Rank.EIGHT), new Bishop(Color.BLACK));
    }

    private static void initialQueenSetting(Map<Location, Piece> board) {
        board.put(new Location(File.D, Rank.ONE), new Queen(Color.WHITE));
        board.put(new Location(File.D, Rank.EIGHT), new Queen(Color.BLACK));
    }

    private static void initialKingSetting(Map<Location, Piece> board) {
        board.put(new Location(File.E, Rank.ONE), new King(Color.WHITE));
        board.put(new Location(File.E, Rank.EIGHT), new King(Color.BLACK));
    }
}
