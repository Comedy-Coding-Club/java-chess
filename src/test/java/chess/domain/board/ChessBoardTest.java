package chess.domain.board;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChessBoardTest {

    ChessBoard chessBoard;

    @BeforeEach
    void beforeEach() {
        chessBoard = new ChessBoard(new MemoryRepository(new HashMap<>()));
    }

    @DisplayName("보드를 초기화 한다.")
    @Test
    void initNewBoardTest() {
        //when
        chessBoard.initNewBoard(DefaultBoardInitializer.initializer());
        Map<Position, Piece> board = chessBoard.getBoard();

        //then
        assertThat(board).isEqualTo(DefaultBoardInitializer.initializer());
    }

    @DisplayName("기물을 이동시킨다.")
    @Test
    void movePieceTest() {
        //given
        Position from = new Position(Row.RANK2, Column.A);
        Position to = new Position(Row.RANK4, Column.A);
        Piece piece = new Piece(PieceType.WHITE_PAWN, Color.WHITE);

        //when
        chessBoard.initNewBoard(DefaultBoardInitializer.initializer());
        chessBoard.movePiece(from, to);

        //then
        assertThatThrownBy(() -> chessBoard.findPieceByPosition(from))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(chessBoard.findPieceByPosition(to)).isEqualTo(piece);
    }

    @DisplayName("체스판에 왕이 하나만 있는 경우 false 를 리턴한다.")
    @Test
    void hasTwoKingFalseTest() {

        Map<Position, Piece> boardMap = new HashMap<>();
        MemoryRepository memoryRepository = new MemoryRepository(boardMap);
        chessBoard = new ChessBoard(memoryRepository);

        boardMap.put(new Position(Row.RANK8, Column.B), new Piece(PieceType.KING, Color.BLACK));
        boardMap.put(new Position(Row.RANK8, Column.C), new Piece(PieceType.ROOK, Color.BLACK));
        boardMap.put(new Position(Row.RANK7, Column.A), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        boardMap.put(new Position(Row.RANK7, Column.C), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        boardMap.put(new Position(Row.RANK7, Column.D), new Piece(PieceType.BISHOP, Color.BLACK));
        boardMap.put(new Position(Row.RANK6, Column.B), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        boardMap.put(new Position(Row.RANK6, Column.E), new Piece(PieceType.QUEEN, Color.BLACK));

        boardMap.put(new Position(Row.RANK4, Column.F), new Piece(PieceType.KNIGHT, Color.WHITE));
        boardMap.put(new Position(Row.RANK4, Column.G), new Piece(PieceType.QUEEN, Color.WHITE));
        boardMap.put(new Position(Row.RANK3, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK3, Column.H), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK2, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK2, Column.G), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK1, Column.E), new Piece(PieceType.ROOK, Color.WHITE));

        assertThat(chessBoard.hasTwoKing()).isFalse();
    }

    @DisplayName("체스판에 왕이 모두 살아있는 경우 true 를 리턴한다.")
    @Test
    void hasTwoKingTrueTest() {
        Map<Position, Piece> boardMap = new HashMap<>();
        MemoryRepository memoryRepository = new MemoryRepository(boardMap);
        chessBoard = new ChessBoard(memoryRepository);

        boardMap.put(new Position(Row.RANK8, Column.B), new Piece(PieceType.KING, Color.BLACK));
        boardMap.put(new Position(Row.RANK8, Column.C), new Piece(PieceType.ROOK, Color.BLACK));
        boardMap.put(new Position(Row.RANK7, Column.A), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        boardMap.put(new Position(Row.RANK7, Column.C), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        boardMap.put(new Position(Row.RANK7, Column.D), new Piece(PieceType.BISHOP, Color.BLACK));
        boardMap.put(new Position(Row.RANK6, Column.B), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        boardMap.put(new Position(Row.RANK6, Column.E), new Piece(PieceType.QUEEN, Color.BLACK));

        boardMap.put(new Position(Row.RANK4, Column.F), new Piece(PieceType.KNIGHT, Color.WHITE));
        boardMap.put(new Position(Row.RANK4, Column.G), new Piece(PieceType.QUEEN, Color.WHITE));
        boardMap.put(new Position(Row.RANK3, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK3, Column.H), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK2, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK2, Column.G), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        boardMap.put(new Position(Row.RANK1, Column.E), new Piece(PieceType.KING, Color.WHITE));

        assertThat(chessBoard.hasTwoKing()).isTrue();
    }
}
