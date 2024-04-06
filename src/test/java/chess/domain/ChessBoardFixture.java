package chess.domain;

import chess.domain.board.ChessBoard;
import chess.domain.board.Turn;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.BlackPawn;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import chess.domain.piece.type.WhitePawn;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardFixture {

    public static ChessBoard chessBoardForScore1 = createChessBoardForScore();
    public static ChessBoard chessBoardForScore2 = createChessBoardForScore2();

    /**
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * abcdefgh
     */
    private static ChessBoard createChessBoardForScore() {
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(new Position(File.B, Rank.EIGHT), new King(Color.BLACK));
        pieces.put(new Position(File.C, Rank.EIGHT), new Rook(Color.BLACK));
        pieces.put(new Position(File.A, Rank.SEVEN), new BlackPawn());
        pieces.put(new Position(File.C, Rank.SEVEN), new BlackPawn());
        pieces.put(new Position(File.D, Rank.SEVEN), new Bishop(Color.BLACK));
        pieces.put(new Position(File.B, Rank.SIX), new BlackPawn());
        pieces.put(new Position(File.E, Rank.SIX), new Queen(Color.BLACK));
        pieces.put(new Position(File.F, Rank.FOUR), new Knight(Color.WHITE));
        pieces.put(new Position(File.G, Rank.FOUR), new Queen(Color.WHITE));
        pieces.put(new Position(File.F, Rank.THREE), new WhitePawn());
        pieces.put(new Position(File.H, Rank.THREE), new WhitePawn());
        pieces.put(new Position(File.F, Rank.TWO), new WhitePawn());
        pieces.put(new Position(File.G, Rank.TWO), new WhitePawn());
        pieces.put(new Position(File.E, Rank.ONE), new Rook(Color.WHITE));
        pieces.put(new Position(File.H, Rank.ONE), new King(Color.WHITE));

        return new ChessBoard(0, new Turn(Color.WHITE), pieces);
    }

    /**
     * .KR.....  8
     * P.PB....  7
     * PP..Q...  6
     * P.......  5
     * .....nq.  4
     * .......p  3
     * .....pp.  2
     * ....rk..  1
     * abcdefgh
     */
    private static ChessBoard createChessBoardForScore2() {
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(new Position(File.B, Rank.EIGHT), new King(Color.BLACK));
        pieces.put(new Position(File.C, Rank.EIGHT), new Rook(Color.BLACK));
        pieces.put(new Position(File.A, Rank.SEVEN), new BlackPawn());
        pieces.put(new Position(File.C, Rank.SEVEN), new BlackPawn());
        pieces.put(new Position(File.A, Rank.SIX), new BlackPawn());
        pieces.put(new Position(File.B, Rank.SIX), new BlackPawn());
        pieces.put(new Position(File.A, Rank.FIVE), new BlackPawn());
        pieces.put(new Position(File.D, Rank.SEVEN), new Bishop(Color.BLACK));
        pieces.put(new Position(File.E, Rank.SIX), new Queen(Color.BLACK));
        pieces.put(new Position(File.F, Rank.FOUR), new Knight(Color.WHITE));
        pieces.put(new Position(File.G, Rank.FOUR), new Queen(Color.WHITE));
        pieces.put(new Position(File.H, Rank.THREE), new WhitePawn());
        pieces.put(new Position(File.F, Rank.TWO), new WhitePawn());
        pieces.put(new Position(File.G, Rank.TWO), new WhitePawn());
        pieces.put(new Position(File.E, Rank.ONE), new Rook(Color.WHITE));
        pieces.put(new Position(File.H, Rank.ONE), new King(Color.WHITE));

        return new ChessBoard(0, new Turn(Color.WHITE), pieces);
    }
}
