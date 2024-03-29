package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.DefaultBoardInitializer;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("보드 생성기로 보드 생성 시 32개의 기물이 초기화된다.")
    @Test
    void printMap() {
        Board board = new DefaultBoardInitializer().initialize();

        Map<Position, Piece> boardMap = board.getBoard();
        Map<Position, Piece> defaultMap = new HashMap<>();

        defaultMap.put(new Position(Row.RANK1, Column.A), new Piece(PieceType.ROOK, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.H), new Piece(PieceType.ROOK, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.B), new Piece(PieceType.KNIGHT, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.G), new Piece(PieceType.KNIGHT, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.C), new Piece(PieceType.BISHOP, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.F), new Piece(PieceType.BISHOP, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.D), new Piece(PieceType.QUEEN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK1, Column.E), new Piece(PieceType.KING, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.A), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.H), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.G), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.C), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.F), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.D), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        defaultMap.put(new Position(Row.RANK2, Column.E), new Piece(PieceType.WHITE_PAWN, Color.WHITE));

        defaultMap.put(new Position(Row.RANK8, Column.A), new Piece(PieceType.ROOK, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.H), new Piece(PieceType.ROOK, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.B), new Piece(PieceType.KNIGHT, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.G), new Piece(PieceType.KNIGHT, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.C), new Piece(PieceType.BISHOP, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.F), new Piece(PieceType.BISHOP, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.D), new Piece(PieceType.QUEEN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK8, Column.E), new Piece(PieceType.KING, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.A), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.H), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.B), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.G), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.C), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.F), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.D), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        defaultMap.put(new Position(Row.RANK7, Column.E), new Piece(PieceType.BLACK_PAWN, Color.BLACK));

        assertThat(boardMap).isEqualTo(defaultMap);
    }

    @DisplayName("체스판에 왕이 하나만 있는 경우 false 를 리턴한다.")
    @Test
    void hasTwoKingFalseTest() {
        Map<Position, Piece> boardMap = new HashMap<>();
        Board board = new Board(boardMap);

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

        assertThat(board.hasKing(ChessGame.KING_COUNT)).isFalse();
    }

    @DisplayName("체스판에 왕이 모두 살아있는 경우 true 를 리턴한다.")
    @Test
    void hasTwoKingTrueTest() {
        Map<Position, Piece> boardMap = new HashMap<>();
        Board board = new Board(boardMap);

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

        assertThat(board.hasKing(ChessGame.KING_COUNT)).isTrue();
    }
}
